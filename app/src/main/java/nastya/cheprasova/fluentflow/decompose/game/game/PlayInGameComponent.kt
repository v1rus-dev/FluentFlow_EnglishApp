package nastya.cheprasova.fluentflow.decompose.game.game

import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.gameScreen.GameState

interface PlayInGameComponent {

    val uiState: Value<GameState>

    fun event(event: Event)


    sealed interface Event {

        object OnBack : Event

        data class CheckWord(val selectedWord: String) : Event

    }

    enum class GameMode {
        New,
        Ended,
        Mix
    }

}