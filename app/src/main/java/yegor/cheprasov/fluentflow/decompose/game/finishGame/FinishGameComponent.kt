package yegor.cheprasov.fluentflow.decompose.game.finishGame

import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import yegor.cheprasov.fluentflow.ui.compose.gameScreen.FinishGameState

interface FinishGameComponent {

    val uiState: Value<FinishGameState>

    fun event(event: Event)

    sealed interface Event {

        object Continue : Event

    }

    @Parcelize
    data class Word(
        val word: String,
        val translate: String
    ) : Parcelable

}