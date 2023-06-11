package nastya.cheprasova.fluentflow.ui.compose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import nastya.cheprasova.fluentflow.decompose.parent.ParentScreenComponent
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.ExerciseScreen
import nastya.cheprasova.fluentflow.ui.compose.gameScreen.GameScreen
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.GrammarMainScreen
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.MainScreen
import nastya.cheprasova.fluentflow.ui.compose.onboarding.OnboardingScreen
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.WordsScreen

@Composable
fun ApplicationScreen(
    component: ParentScreenComponent
) {
    Children(stack = component.childStack, animation = stackAnimation { child, otherChild, direction ->
        fade() + scale()
    }) {
        when (val instance = it.instance) {
            is ParentScreenComponent.Child.Main -> MainScreen(component = instance.component)
            is ParentScreenComponent.Child.Exercises -> ExerciseScreen(component = instance.component)
            is ParentScreenComponent.Child.Game -> GameScreen(component = instance.component)
            is ParentScreenComponent.Child.GrammarThemes -> GrammarMainScreen(component = instance.component)
            is ParentScreenComponent.Child.Words -> WordsScreen(component = instance.component)
            is ParentScreenComponent.Child.OnBoarding -> OnboardingScreen(component = instance.component)
        }
    }
}