package yegor.cheprasov.fluentflow.ui.compose.exerciseScreen.state

import yegor.cheprasov.fluentflow.ui.compose.exerciseScreen.ExerciseSelectState

sealed interface ExerciseState {
    object Loading : ExerciseState
    data class CurrentExercise(val sentense: String, val correctWords: List<String>, val exercise: ExerciseSelectState) : ExerciseState
}