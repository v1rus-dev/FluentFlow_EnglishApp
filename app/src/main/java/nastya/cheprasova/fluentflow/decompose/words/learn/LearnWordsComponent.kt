package nastya.cheprasova.fluentflow.decompose.words.learn

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.dialog.DialogComponent
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.state.LearnWordsState
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity

interface LearnWordsComponent {

    val uiState: Value<LearnWordsState>

    val errorDialog: Value<ChildSlot<*, DialogComponent>>

    fun event(event: Event)

    sealed interface Event {

        object OnFinish : Event

        data class ShowErrorDialog(
            val word: String,
            val correctWord: String,
            val chooseWord: String
        ) : Event

        data class Learned(val word: LearnWordsViewEntity) : Event

        object CloseAll : Event
    }
}