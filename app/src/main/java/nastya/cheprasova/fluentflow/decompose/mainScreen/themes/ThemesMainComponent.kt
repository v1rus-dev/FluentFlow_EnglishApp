package nastya.cheprasova.fluentflow.decompose.mainScreen.themes

import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes.state.TopicsState

interface ThemesMainComponent {

    val topics: Value<TopicsState>

    fun event(event: Event)

    sealed interface Event {
        object OpenGrammars : Event
        object OpenExercises : Event
        object OpenGames : Event
    }

}