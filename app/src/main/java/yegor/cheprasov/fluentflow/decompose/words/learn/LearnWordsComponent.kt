package yegor.cheprasov.fluentflow.decompose.words.learn

import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.LearnWordsState

interface LearnWordsComponent {

    val uiState: Value<LearnWordsState>

    fun event(event: Event)

    sealed interface Event {
        object CloseAll : Event
    }
}