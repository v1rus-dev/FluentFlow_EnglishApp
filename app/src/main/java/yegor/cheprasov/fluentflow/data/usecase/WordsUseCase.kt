package yegor.cheprasov.fluentflow.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yegor.cheprasov.fluentflow.data.repositories.WordsRepository

class WordsUseCase(private val wordsRepository: WordsRepository) {

    suspend fun loadWordsTopics() = withContext(Dispatchers.IO) {
        wordsRepository.loadWordTopic()
    }

    fun observeTopics() = wordsRepository.observeAllWordTopics()
}