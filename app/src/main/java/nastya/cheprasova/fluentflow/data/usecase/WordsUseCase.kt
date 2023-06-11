package nastya.cheprasova.fluentflow.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nastya.cheprasova.fluentflow.data.repositories.WordsRepository
import nastya.cheprasova.fluentflow.data.room.entities.WordsEntity
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity

class WordsUseCase(private val wordsRepository: WordsRepository) {

    suspend fun loadWordsTopics() = withContext(Dispatchers.IO) {
        wordsRepository.loadWordTopic()
    }

    fun observeTopics() = wordsRepository.observeAllWordTopics()

    suspend fun getAllWordsForByTopic(topicId: Int): List<WordsEntity> =
        wordsRepository.getAllByTopicId(topicId)

    suspend fun getWordsForLearningByTopic(topicId: Int): List<WordsEntity> = withContext(Dispatchers.IO) {
        wordsRepository.getAllByTopicId(topicId).filter { !it.isLearned }.take(15)
    }

    suspend fun saveWordAsLearned(word: LearnWordsViewEntity, topicId: Int) = withContext(Dispatchers.IO) {
        wordsRepository.saveWordAsLearned(word, topicId)
    }
}