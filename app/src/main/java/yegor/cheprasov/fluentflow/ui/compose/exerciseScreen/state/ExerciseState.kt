package yegor.cheprasov.fluentflow.ui.compose.exerciseScreen.state

import yegor.cheprasov.fluentflow.data.room.entities.ExerciseEntity

sealed interface ExerciseState {
    object Loading : ExerciseState
    data class CurrentExercise(val exercise: ExerciseEntity) : ExerciseState
}