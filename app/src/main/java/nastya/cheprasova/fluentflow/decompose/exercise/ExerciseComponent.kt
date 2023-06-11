package nastya.cheprasova.fluentflow.decompose.exercise

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.dialog.DialogComponent
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.SelectWordViewEntity
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.state.ExerciseState

interface ExerciseComponent {

    val uiState: Value<ExerciseState>

    val dialogState: Value<ChildSlot<*, DialogComponent>>

    fun event(event: Event)

    sealed interface Event {
        object Check : Event

        data class SelectWord(val wordViewEntity: SelectWordViewEntity) : Event

        data class RemoveWord(val wordViewEntity: SelectWordViewEntity) : Event

        object GoToNext : Event

        object Close : Event
    }
}