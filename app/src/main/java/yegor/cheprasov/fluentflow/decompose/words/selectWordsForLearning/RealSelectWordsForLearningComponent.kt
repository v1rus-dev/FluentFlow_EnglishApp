package yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState

class RealSelectWordsForLearningComponent(
    componentContext: ComponentContext,
    val topicId: Int,
    val _close: () -> Unit
) : BaseComponent(componentContext), SelectWordsForLearningComponent {

    private val wordsUseCase: WordsUseCase by inject()
    private val _uiState: MutableValue<SelectWordsForLearningState> =
        MutableValue(SelectWordsForLearningState.None)

    init {
        getWordsForLearning()
    }

    private fun getWordsForLearning() = scope.launch {
        val list = wordsUseCase.getWordsForLearningByTopic(topicId)
    }

    override fun onEvent(event: SelectWordsForLearningComponent.Event) {
        when(event) {
            SelectWordsForLearningComponent.Event.Close -> _close()
        }
    }

    override val uiState: Value<SelectWordsForLearningState>
        get() = _uiState

}