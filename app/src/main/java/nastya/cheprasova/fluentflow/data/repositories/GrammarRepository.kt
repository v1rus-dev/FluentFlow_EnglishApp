package nastya.cheprasova.fluentflow.data.repositories

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import nastya.cheprasova.fluentflow.data.entity.GrammarNetworkEntity
import nastya.cheprasova.fluentflow.data.firestore.AppFirebaseStorage
import nastya.cheprasova.fluentflow.data.firestore.AppFirestore
import nastya.cheprasova.fluentflow.data.entity.GrammarDetailNetworkEntity
import nastya.cheprasova.fluentflow.data.entity.GrammarExercise
import nastya.cheprasova.fluentflow.data.entity.GrammarExerciseNetworkEntity
import nastya.cheprasova.fluentflow.data.entity.GrammarExercises
import nastya.cheprasova.fluentflow.data.mappers.GrammarNetworkMapper
import nastya.cheprasova.fluentflow.data.room.dao.GrammarDao
import nastya.cheprasova.fluentflow.data.room.dao.GrammarExerciseDao
import nastya.cheprasova.fluentflow.data.room.entities.GrammarEntity
import nastya.cheprasova.fluentflow.data.room.entities.GrammarExerciseEntity
import java.io.File
import java.io.FileReader

class GrammarRepository(
    private val firestore: AppFirestore,
    private val firebaseStorage: AppFirebaseStorage,
    private val grammarDao: GrammarDao,
    private val grammarExerciseDao: GrammarExerciseDao,
    private val grammarNetworkMapper: GrammarNetworkMapper
) {

    suspend fun loadGrammars() {
        val snapshot = firestore.grammars.get().await()
        val grammarEntitiesNetwork =
            snapshot.documents.mapNotNull { it.toObject(GrammarNetworkEntity::class.java) }
                .map { grammarNetworkMapper.map(it) }
        insertToDatabase(grammarEntitiesNetwork)
    }

    fun observeGrammars(): Flow<List<GrammarEntity>> = grammarDao.observeAll()

    suspend fun getExercisesByGrammarId(grammarId: Int) = grammarExerciseDao.getByExerciseId(grammarId)

    suspend fun setExerciseLikeEnded(id: Int, grammarId: Int) {
        val grammar = grammarDao.getByGrammarId(grammarId)
        val currentEnded = grammar.endedExercises
        grammarDao.updateEndedSize(grammarId, currentEnded + 1)
        grammarExerciseDao.setExerciseLikeEnded(id)
    }

    fun loadGrammarFromFile(fileName: String): Flow<GrammarDetailNetworkEntity> = flow {
        val file = File.createTempFile(fileName.substringBefore("."), fileName.substringAfter("."))
        firebaseStorage.grammarReference.child(fileName).getFile(file).await()
        val gson = Gson()
        val fileReader = FileReader(file)
        val entity = gson.fromJson(fileReader, GrammarDetailNetworkEntity::class.java)
        file.delete()
        fileReader.close()
        emit(entity)
    }.flowOn(Dispatchers.IO)

    private suspend fun insertToDatabase(grammarEntitiesNetwork: List<GrammarEntity>) {
        val currentGrammarsInDb = grammarDao.getAll()
        val newGrammars = grammarEntitiesNetwork.filter { !currentGrammarsInDb.map { it.title }.contains(it.title) }
        grammarDao.insertAll(newGrammars)
        loadExercises(newGrammars)
    }

    private suspend fun loadExercises(newGrammars: List<GrammarEntity>) = withContext(Dispatchers.IO) {
        newGrammars.forEach {
            val exercise = loadExercise(fileName = it.exerciseFile).list.mapIndexed { index, grammarExercise ->
                GrammarExerciseEntity(
                    translate = grammarExercise.translate,
                    imagePath = grammarExercise.imagePath.toString(),
                    text = grammarExercise.text,
                    words = grammarExercise.words,
                    correctWords = grammarExercise.correctWords,
                    correctPhrase = grammarExercise.correctPhrase,
                    grammarId = it.grammarId,
                    isEnded = false
                )
            }
            grammarExerciseDao.insert(exercise)
            grammarDao.updateSizeOfExercises(it.fileName, exercise.size)
        }
    }

    private suspend fun imgToURI(path: String, img: String) =
        firebaseStorage.grammarReference.child("images/$path/$img").downloadUrl.await()

    private suspend fun loadExercise(fileName: String): GrammarExercises = withContext(Dispatchers.IO) {
        if (fileName.isEmpty()) {
            return@withContext GrammarExercises(0, listOf())
        }
        val file = withContext(Dispatchers.IO) {
            File.createTempFile(fileName.substringBefore("."), fileName.substringAfter("."))
        }
        try {
            firebaseStorage.grammarReference.child("exercises/$fileName").getFile(file).await()
        } catch (e: Exception) {
            Log.d("myTag", "error: ${e.message}")
            e.printStackTrace()
            return@withContext GrammarExercises(0, listOf())
        }
        val gson = Gson()
        val fileReader = FileReader(file)
        val entity = gson.fromJson(fileReader, GrammarExerciseNetworkEntity::class.java)
        file.delete()
        fileReader.close()
        return@withContext mapToGrammarExercises(entity)
    }

    private suspend fun mapToGrammarExercises(grammarExerciseEntity: GrammarExerciseNetworkEntity) =
        GrammarExercises(grammarExerciseEntity.count, grammarExerciseEntity.list.map {
            GrammarExercise(
                translate = it.translate,
                imagePath = imgToURI(it.imagePath, it.imageFile),
                text = it.text,
                words = it.words,
                correctWords = it.correctWords,
                correctPhrase = it.correctPhrase
            )
        })

}