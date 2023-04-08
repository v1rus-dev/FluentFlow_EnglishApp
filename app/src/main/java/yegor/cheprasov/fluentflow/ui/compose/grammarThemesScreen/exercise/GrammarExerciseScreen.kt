package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.exercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.FakeGrammarExerciseComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.GrammarExerciseComponent

@Composable
fun GrammarExerciseScreen(component: GrammarExerciseComponent) {
    val uiState = component.uiState.subscribeAsState()
    val finishState = component.finish.subscribeAsState()

    LaunchedEffect(key1 = finishState) {
        if (finishState.value) {
            component.close()
        }
    }

    ExerciseScreen(state = uiState.value, onCheck = {
        component.checkAnswer(it)
    }, onContinue = { component.finish(it) }) {
        component.close()
    }
}

@Preview
@Composable
private fun PreviewGrammarExerciseScreen() {
    GrammarExerciseScreen(component = FakeGrammarExerciseComponent())
}