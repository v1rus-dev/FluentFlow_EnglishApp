package nastya.cheprasova.fluentflow.decompose.exercise

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.dialog.DialogComponent
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.ExerciseSelectState
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.SelectWordViewEntity
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.state.ExerciseState

class FakeExerciseComponent : ExerciseComponent {
    override val uiState: Value<ExerciseState>
        get() = MutableValue(
            ExerciseState.CurrentExercise(
                sentense = "I walk and she swims",
                correctWords = listOf("я", "иду", "и", "она", "плывет"),
                exercise = ExerciseSelectState(
                    selectWords = listOf(),
                    allWords = listOf(
                        SelectWordViewEntity("иду"),
                        SelectWordViewEntity("дышал"),
                        SelectWordViewEntity("Погода"),
                        SelectWordViewEntity("я"),
                        SelectWordViewEntity("смотрю"),
                        SelectWordViewEntity("Она"),
                        SelectWordViewEntity("и"),
                        SelectWordViewEntity("собака"),
                        SelectWordViewEntity("плывет"),
                        SelectWordViewEntity("свет"),
                        SelectWordViewEntity("самолет"),
                    )
                )
            )
        )
    override val dialogState: Value<ChildSlot<*, DialogComponent>>
        get() = MutableValue(ChildSlot<Unit, DialogComponent>())

    override fun event(event: ExerciseComponent.Event) = Unit
}