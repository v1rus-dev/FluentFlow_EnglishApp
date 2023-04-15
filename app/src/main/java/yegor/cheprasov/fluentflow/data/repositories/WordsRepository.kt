package yegor.cheprasov.fluentflow.data.repositories

import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import yegor.cheprasov.fluentflow.data.entity.WordsFromFile
import yegor.cheprasov.fluentflow.data.entity.WordsTopicNetworkEntity
import yegor.cheprasov.fluentflow.data.firestore.AppFirebaseStorage
import yegor.cheprasov.fluentflow.data.firestore.AppFirestore
import yegor.cheprasov.fluentflow.data.mappers.WordsMapper
import yegor.cheprasov.fluentflow.data.room.dao.WordsDao
import yegor.cheprasov.fluentflow.data.room.dao.WordsTopicDao
import yegor.cheprasov.fluentflow.data.room.entities.WordsEntity
import yegor.cheprasov.fluentflow.data.room.entities.WordsTopicEntity
import yegor.cheprasov.fluentflow.data.utils.map as appMap
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity
import java.io.File
import java.io.FileReader

class WordsRepository(
    private val appFirebaseStorage: AppFirebaseStorage,
    private val appFirestore: AppFirestore,
    private val wordsTopicDao: WordsTopicDao,
    private val wordsDao: WordsDao,
    private val wordsMapper: WordsMapper
) {

    suspend fun loadWordTopic() {
        val snapshot = appFirestore.db.collection("words").get().await()
        val words =
            snapshot.documents.mapNotNull { it.toObject(WordsTopicNetworkEntity::class.java) }
        insertToDb(words)
        loadWords(words)
    }

    fun observeAllWordTopics(): Flow<List<WordsTopicEntity>> = wordsTopicDao.observeAll()

    suspend fun getAllByTopicId(topicId: Int) = wordsDao.getAllByTopicId(topicId)

    suspend fun saveWordAsLearned(wordsViewEntity: LearnWordsViewEntity, topicId: Int) =
        wordsDao.saveWordAsLearned(wordsViewEntity.appMap {
            WordsEntity(
                translate = wordsViewEntity.translations.first { it.isCorrect }.word,
                word = wordsViewEntity.word,
                topicId = topicId,
                isLearned = true
            )
        })

    private suspend fun loadWords(words: List<WordsTopicNetworkEntity>) {
        val newWordsForDb = arrayListOf<WordsEntity>()
        words.forEach { topic ->
            val currentWords = wordsDao.getAllByTopicId(topic.wordsThemId)
            val wordsFromNetwork = loadWordsFromFile(topic.fileName)
            val newWords = wordsFromNetwork.list.filter { networkWord ->
                !currentWords.map { it.word }.contains(networkWord.word)
            }
                .map { wordsMapper.mapFromFile(it, topic.wordsThemId) }
            newWordsForDb.addAll(newWords)
        }
        wordsDao.insertAll(newWordsForDb)
    }

    private suspend fun loadWordsFromFile(fileName: String): WordsFromFile {
        val file = withContext(Dispatchers.IO) {
            File.createTempFile(fileName.substringBefore("."), fileName.substringAfter("."))
        }
        appFirebaseStorage.wordsReference.child(fileName).getFile(file).await()
        val gson = Gson()
        val fileReader = withContext(Dispatchers.IO) {
            FileReader(file)
        }
        val entity = gson.fromJson(fileReader, WordsFromFile::class.java)
        file.delete()
        withContext(Dispatchers.IO) {
            fileReader.close()
        }
        return entity
    }

    private suspend fun insertToDb(words: List<WordsTopicNetworkEntity>) {
        val currentWordsInDb = wordsTopicDao.getAll()
        val newWords =
            words.filter { !currentWordsInDb.map { it.topicId }.contains(it.wordsThemId) }
                .map {
                    val x = imgToUri(it.imagesFolder)
                    Log.d("myTag", "imgUri: $x")
                    wordsMapper.map(it, x)
                }
        wordsTopicDao.insertAll(newWords)
    }

    private suspend fun imgToUri(img: String): Uri =
        appFirebaseStorage.wordsReference.child("images/$img/main.png").downloadUrl.await()

}