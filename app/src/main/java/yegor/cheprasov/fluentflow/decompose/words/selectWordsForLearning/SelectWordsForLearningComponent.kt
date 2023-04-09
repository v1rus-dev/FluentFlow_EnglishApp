package yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning

import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState

interface SelectWordsForLearningComponent {

    val uiState: Value<SelectWordsForLearningState>

    fun onEvent(event: Event)

    sealed interface Event {
        object Close : Event
    }
}