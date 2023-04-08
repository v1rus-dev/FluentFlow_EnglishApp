package yegor.cheprasov.fluentflow.data.repositories

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import yegor.cheprasov.fluentflow.data.entity.GrammarNetworkEntity
import yegor.cheprasov.fluentflow.data.firestore.AppFirebaseStorage
import yegor.cheprasov.fluentflow.data.firestore.AppFirestore
import yegor.cheprasov.fluentflow.data.entity.GrammarDetailNetworkEntity
import yegor.cheprasov.fluentflow.data.entity.GrammarExercise
import yegor.cheprasov.fluentflow.data.entity.GrammarExerciseNetworkEntity
import yegor.cheprasov.fluentflow.data.entity.GrammarExercises
import yegor.cheprasov.fluentflow.data.mappers.GrammarNetworkMapper
import yegor.cheprasov.fluentflow.data.room.dao.GrammarDao
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity
import java.io.File
import java.io.FileReader

class GrammarRepository(
    private val firestore: AppFirestore,
    private val firebaseStorage: AppFirebaseStorage,
    private val grammarDao: GrammarDao,
    private val grammarNetworkMapper: GrammarNetworkMapper
) {

    suspend fun loadGrammars() {
        val snapshop = firestore.db.collection("grammars").get().await()
        val grammarEntitiesNetwork =
            snapshop.documents.mapNotNull { it.toObject(GrammarNetworkEntity::class.java) }
                .map { grammarNetworkMapper.map(it) }
        insertToDatabase(grammarEntitiesNetwork)
    }

    fun observeGrammars(): Flow<List<GrammarEntity>> = grammarDao.observeAll()

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

    fun loadExercise(fileName: String): Flow<GrammarExercises> = flow {
        val file = withContext(Dispatchers.IO) {
            File.createTempFile(fileName.substringBefore("."), fileName.substringAfter("."))
        }
        firebaseStorage.grammarReference.child("exercises/$fileName").getFile(file).await()
        val gson = Gson()
        val fileReader = FileReader(file)
        val entity = gson.fromJson(fileReader, GrammarExerciseNetworkEntity::class.java)
        file.delete()
        fileReader.close()
        val result = mapToGrammarExercises(entity)
        emit(result)
    }.flowOn(Dispatchers.IO)

    private suspend fun insertToDatabase(grammarEntitiesNetwork: List<GrammarEntity>) {
        val currentGrammarsInDb = grammarDao.getAll()
        val newGrammars = grammarEntitiesNetwork.filter { !currentGrammarsInDb.map { it.title }.contains(it.title) }
        grammarDao.insertAll(newGrammars)
    }

    private suspend fun imgToURI(path: String, img: String) =
        firebaseStorage.grammarReference.child("images/$path/$img").downloadUrl.await()

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