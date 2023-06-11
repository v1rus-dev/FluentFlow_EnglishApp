package nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import nastya.cheprasova.fluentflow.decompose.grammarThemes.main.GrammarMainScreen
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.allThemes.GrammarThemesScreen
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.details.GrammarDetailsScreen
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.exercise.GrammarExerciseScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GrammarMainScreen(component: GrammarMainScreen) {
    val uiState = component.childStack.subscribeAsState()

    AnimatedContent(targetState = uiState.value.active.instance, label = "GrammarMainScreenAnimatedContent") { instance ->
        when(instance) {
            is GrammarMainScreen.Child.AllThemes -> GrammarThemesScreen(component = instance.component)
            is GrammarMainScreen.Child.Details -> GrammarDetailsScreen(component = instance.component)
            is GrammarMainScreen.Child.Exercise -> GrammarExerciseScreen(component = instance.component)
        }
    }
}