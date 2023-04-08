package yegor.cheprasov.fluentflow.decompose.mainScreen.words

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.mappers.WordsMapper
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.components.NewWordsAndPhrasesState
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.state.WordsState
import yegor.cheprasov.fluentflow.utils.reduceMain

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
                .map { list -> list.map { wordsMapper.mapToViewEntity(it) } }
                .collectLatest { list ->
                    _uiState.reduceMain { it.copy(list = list) }
                }
        }
    }

}