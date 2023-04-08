package yegor.cheprasov.fluentflow.decompose.parent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.exercise.ExerciseComponent
import yegor.cheprasov.fluentflow.decompose.game.GameComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.main.GrammarMainScreen
import yegor.cheprasov.fluentflow.decompose.mainScreen.main.MainComponent
import yegor.cheprasov.fluentflow.decompose.words.WordsInformationComponent
import yegor.cheprasov.fluentflow.decompose.words.main.WordsMainComponent

interface ParentScreenComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
        data class GrammarThemes(val component: GrammarMainScreen) : Child()
        data class Exercises(val component: ExerciseComponent) : Child()
        data class Game(val component: GameComponent) : Child()
        data class Words(val component: WordsMainComponent) : Child()
    }
}