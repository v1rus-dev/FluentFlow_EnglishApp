package yegor.cheprasov.fluentflow.decompose.words.learn

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.utils.RandomWords
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.LearnWordsState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsTranslateViewEntity
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity

class RealLearnWordsComponent(
    componentContext: ComponentContext,
    private val listForLearning: List<WordsForLearningViewEntity>,
    private val close: () -> Unit
) : BaseComponent(componentContext), LearnWordsComponent {

    private val randomWords: RandomWords by inject()
    private val shuffledList = arrayListOf<String>()

    private val _uiState = MutableValue<LearnWordsState>(LearnWordsState.Initialize)

    override val uiState: Value<LearnWordsState> = _uiState

    init {
        loadWords()
    }

    override fun event(event: LearnWordsComponent.Event) {
        when (event) {
            LearnWordsComponent.Event.CloseAll -> close()
        }
    }

    private fun loadWords() = scope.launch {
        val words = randomWords.getWords().words.shuffled()
        shuffledList.clear()
        shuffledList.addAll(words)
        _uiState.update { getState() }
    }

    private fun getState(): LearnWordsState = LearnWordsState.Success(
        words = listForLearning.map {
            LearnWordsViewEntity(
                word = it.word,
                translations = getTranslationList(it.translate)
            )
        }
    )

    private fun getTranslationList(currentWords: String): List<LearnWordsTranslateViewEntity> {
        val newShuffledList = shuffledList.shuffled().take(3).shuffled()
            .map { LearnWordsTranslateViewEntity(word = it, isCorrect = false) }
        val result = arrayListOf<LearnWordsTranslateViewEntity>().apply {
            addAll(newShuffledList)
            add(LearnWordsTranslateViewEntity(word = currentWords, isCorrect = true))
        }
        return result.shuffled()
    }
}