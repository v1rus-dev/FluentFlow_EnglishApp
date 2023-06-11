package nastya.cheprasova.fluentflow.decompose.mainScreen.words

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import nastya.cheprasova.fluentflow.data.mappers.WordsMapper
import nastya.cheprasova.fluentflow.data.usecase.WordsUseCase
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.ui.compose.components.NewWordsAndPhrasesState
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.state.WordsState
import nastya.cheprasova.fluentflow.utils.reduceMain

class RealWordsMainComponent(
    componentContext: ComponentContext,
    private val _openTopic: (WordsTopicViewEntity) -> Unit
) : BaseComponent(componentContext), WordsMainComponent {

    private val wordsUseCase: WordsUseCase by inject()
    private val wordsMapper: WordsMapper by inject()
    private val _uiState = MutableValue(
        WordsState(
            newWordsAndPhrasesState = NewWordsAndPhrasesState(0, 0),
            list = listOf()
        )
    )

    init {
        observeWords()
        loadWordsTopics()
    }

    override val uiState: Value<WordsState> = _uiState

    override fun openTopic(topic: WordsTopicViewEntity) = _openTopic(topic)

    private fun loadWordsTopics() = scope.launch {
        wordsUseCase.loadWordsTopics()
    }

    private fun observeWords() = scope.launch {
        withContext(dispatcherIO) {
            wordsUseCase.observeTopics()
                .map { list ->
                    list.map {
                        val words = wordsUseCase.getAllWordsForByTopic(it.topicId)
                        wordsMapper.mapToViewEntity(
                            it,
                            words.size,
                            words.filter { it.isLearned }.size
                        )
                    }
                }
                .collectLatest { list ->
                    _uiState.reduceMain { it.copy(list = list) }
                }
        }
    }

}