package yegor.cheprasov.fluentflow.decompose.words.information

import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

interface WordsInformationComponent {

    val uiState: Value<WordsTopicViewEntity>

    fun event(event: Event)

    sealed interface Event {

        object LearnNewWords : Event

        object RelearnWords : Event
        object Close : Event
    }

}