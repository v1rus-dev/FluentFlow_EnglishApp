package yegor.cheprasov.fluentflow.decompose.exercise

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.data.room.entities.ExerciseEntity
import yegor.cheprasov.fluentflow.decompose.dialog.DialogComponent
import yegor.cheprasov.fluentflow.ui.compose.exerciseScreen.state.ExerciseState

class FakeExerciseComponent : ExerciseComponent {
    override val uiState: Value<ExerciseState>
        get() = MutableValue(ExerciseState.CurrentExercise(
            ExerciseEntity(
                sentense = "I walk and she swims",
                level = 0,
                isEnded = false,
                words = listOf("иду", "дышал", "Погода", "я", "смотрю", "Она", "и", "собака", "плывет", "свет", "самолет"),
                correctWords = listOf("я", "иду", "и", "она", "плывет")
            )
        ))
    override val dialogState: Value<ChildSlot<*, DialogComponent>>
        get() = MutableValue(ChildSlot<Unit, DialogComponent>())

    override fun event(event: ExerciseComponent.Event) = Unit
}