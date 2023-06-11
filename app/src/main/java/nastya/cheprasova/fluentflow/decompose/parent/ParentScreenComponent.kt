package nastya.cheprasova.fluentflow.decompose.parent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.exercise.ExerciseComponent
import nastya.cheprasova.fluentflow.decompose.game.GameComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.main.GrammarMainScreen
import nastya.cheprasova.fluentflow.decompose.mainScreen.main.MainComponent
import nastya.cheprasova.fluentflow.decompose.onboarding.OnBoardingComponent
import nastya.cheprasova.fluentflow.decompose.words.main.WordsMainComponent

interface ParentScreenComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
        data class GrammarThemes(val component: GrammarMainScreen) : Child()
        data class Exercises(val component: ExerciseComponent) : Child()
        data class Game(val component: GameComponent) : Child()
        data class Words(val component: WordsMainComponent) : Child()

        data class OnBoarding(val component: OnBoardingComponent) : Child()
    }
}