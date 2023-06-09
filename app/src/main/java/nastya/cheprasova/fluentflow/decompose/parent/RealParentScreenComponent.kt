package nastya.cheprasova.fluentflow.decompose.parent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.koin.core.component.inject
import nastya.cheprasova.fluentflow.data.usecase.LevelUseCase
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.decompose.exercise.ExerciseComponent
import nastya.cheprasova.fluentflow.decompose.exercise.RealExerciseComponent
import nastya.cheprasova.fluentflow.decompose.game.GameComponent
import nastya.cheprasova.fluentflow.decompose.game.RealGameComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.main.GrammarMainScreen
import nastya.cheprasova.fluentflow.decompose.grammarThemes.main.RealGrammarMainScreen
import nastya.cheprasova.fluentflow.decompose.mainScreen.main.MainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.main.RealMainComponent
import nastya.cheprasova.fluentflow.decompose.onboarding.OnBoardingComponent
import nastya.cheprasova.fluentflow.decompose.onboarding.RealOnboardingComponent
import nastya.cheprasova.fluentflow.decompose.words.main.RealWordsMainComponent
import nastya.cheprasova.fluentflow.decompose.words.main.WordsMainComponent
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

class RealParentScreenComponent(
    componentContext: ComponentContext
) : BaseComponent(componentContext), ParentScreenComponent {

    private val levelUseCase: LevelUseCase by inject()

    private val navigation = StackNavigation<ParentConfiguration>()

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = if (levelUseCase.getCurrentLevelIndex() == -1) {
            ParentConfiguration.OnBoarding
        } else {
            ParentConfiguration.Main
        },
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

            is ParentConfiguration.Words -> ParentScreenComponent.Child.Words(
                words(
                    componentContext,
                    config.topic
                )
            )

            ParentConfiguration.OnBoarding -> ParentScreenComponent.Child.OnBoarding(onboarding(componentContext))
        }

    private fun onboarding(componentContext: ComponentContext): OnBoardingComponent =
        RealOnboardingComponent(componentContext) {
            navigation.push(ParentConfiguration.Main)
        }

    private fun main(componentContext: ComponentContext): MainComponent =
        RealMainComponent(componentContext) {
            when (it) {
                MainComponent.Event.OpenExercises -> {
                    navigation.push(ParentConfiguration.Exercises)
                }

                MainComponent.Event.OpenGames -> {
                    navigation.push(ParentConfiguration.Game)
                }

                MainComponent.Event.OpenGrammars -> {
                    navigation.push(ParentConfiguration.GrammarThemes)
                }

                is MainComponent.Event.OpenTopicWords -> {
                    navigation.push(ParentConfiguration.Words(it.topic))
                }
            }
        }

    private fun grammarThemes(componentContext: ComponentContext): GrammarMainScreen =
        RealGrammarMainScreen(componentContext) {
            navigation.pop()
        }

    private fun exercises(componentContext: ComponentContext): ExerciseComponent =
        RealExerciseComponent(componentContext) {
            navigation.pop()
        }

    private fun game(componentContext: ComponentContext): GameComponent =
        RealGameComponent(componentContext) {
            navigation.pop()
        }

    private fun words(
        componentContext: ComponentContext,
        topic: WordsTopicViewEntity
    ): WordsMainComponent =
        RealWordsMainComponent(componentContext, topic) {
            navigation.pop()
        }

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
        object OnBoarding : ParentConfiguration()

        @Parcelize
        data class Words(val topic: WordsTopicViewEntity) : ParentConfiguration()

    }

    override val childStack: Value<ChildStack<*, ParentScreenComponent.Child>>
        get() = _childStack
}