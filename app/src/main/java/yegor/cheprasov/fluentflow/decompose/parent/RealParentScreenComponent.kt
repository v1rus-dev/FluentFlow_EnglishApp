package yegor.cheprasov.fluentflow.decompose.parent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.exercise.ExerciseComponent
import yegor.cheprasov.fluentflow.decompose.exercise.RealExerciseComponent
import yegor.cheprasov.fluentflow.decompose.game.GameComponent
import yegor.cheprasov.fluentflow.decompose.game.RealGameComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.GrammarThemesComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.RealGrammarThemesComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.main.GrammarMainScreen
import yegor.cheprasov.fluentflow.decompose.grammarThemes.main.RealGrammarMainScreen
import yegor.cheprasov.fluentflow.decompose.mainScreen.main.MainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.main.RealMainComponent
import yegor.cheprasov.fluentflow.decompose.words.RealWordsComponent
import yegor.cheprasov.fluentflow.decompose.words.WordsComponent

class RealParentScreenComponent(
    componentContext: ComponentContext
) : BaseComponent(componentContext), ParentScreenComponent {

    private val navigation = StackNavigation<ParentConfiguration>()

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = ParentConfiguration.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: ParentConfiguration,
        componentContext: ComponentContext
    ): ParentScreenComponent.Child =
        when (config) {
            ParentConfiguration.Main -> ParentScreenComponent.Child.Main(main(componentContext))
            ParentConfiguration.Exercises -> ParentScreenComponent.Child.Exercises(
                exercises(
                    componentContext
                )
            )
            ParentConfiguration.Game -> ParentScreenComponent.Child.Game(game(componentContext))
            ParentConfiguration.GrammarThemes -> ParentScreenComponent.Child.GrammarThemes(
                grammarThemes(componentContext)
            )
            ParentConfiguration.Words -> ParentScreenComponent.Child.Words(words(componentContext))
        }

    private fun main(componentContext: ComponentContext): MainComponent =
        RealMainComponent(componentContext) {
            when(it) {
                MainComponent.Event.OpenExercises -> {
                    navigation.push(ParentConfiguration.Exercises)
                }
                MainComponent.Event.OpenGames -> {
                    navigation.push(ParentConfiguration.Game)
                }
                MainComponent.Event.OpenGrammars -> {
                    navigation.push(ParentConfiguration.GrammarThemes)
                }
            }
        }

    private fun grammarThemes(componentContext: ComponentContext): GrammarMainScreen =
        RealGrammarMainScreen(componentContext) {
            navigation.pop()
        }

    private fun exercises(componentContext: ComponentContext): ExerciseComponent =
        RealExerciseComponent(componentContext)

    private fun game(componentContext: ComponentContext): GameComponent =
        RealGameComponent(componentContext)

    private fun words(componentContext: ComponentContext): WordsComponent =
        RealWordsComponent(componentContext)

    private sealed class ParentConfiguration : Parcelable {

        @Parcelize
        object Main : ParentConfiguration()

        @Parcelize
        object GrammarThemes : ParentConfiguration()

        @Parcelize
        object Exercises : ParentConfiguration()

        @Parcelize
        object Game : ParentConfiguration()

        @Parcelize
        object Words : ParentConfiguration()

    }

    override val childStack: Value<ChildStack<*, ParentScreenComponent.Child>>
        get() = _childStack
}