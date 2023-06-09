package nastya.cheprasova.fluentflow.decompose.grammarThemes.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.GrammarThemesComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.RealGrammarThemesComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails.GrammarDetailsComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails.RealGrammarDetailComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarExercise.GrammarExerciseComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarExercise.RealGrammarExerciseComponent
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity

class RealGrammarMainScreen(componentContext: ComponentContext, private val _onBack: () -> Unit) :
    BaseComponent(componentContext), GrammarMainScreen {

    private val navigation = StackNavigation<Config>()

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.AllGrammars,
        handleBackButton = false,
        childFactory = ::childFactory
    )

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): GrammarMainScreen.Child =
        when (config) {
            Config.AllGrammars -> GrammarMainScreen.Child.AllThemes(
                component = allGrammars(
                    componentContext
                )
            )

            is Config.Details -> GrammarMainScreen.Child.Details(
                component = details(
                    componentContext,
                    config.entity
                )
            )

            is Config.Exercise -> GrammarMainScreen.Child.Exercise(
                component = exercise(
                    componentContext,
                    config.grammarId
                )
            )
        }

    private fun allGrammars(componentContext: ComponentContext): GrammarThemesComponent =
        RealGrammarThemesComponent(componentContext, _openDetails = {
            navigation.push(Config.Details(it))
        }, _onBack)

    private fun details(
        componentContext: ComponentContext,
        elementViewEntity: GrammarElementViewEntity
    ): GrammarDetailsComponent =
        RealGrammarDetailComponent(componentContext, elementViewEntity, _openExercise = {
            navigation.push(Config.Exercise(it))
        }, _onBack = {
            navigation.pop()
        })

    private fun exercise(
        componentContext: ComponentContext,
        grammarId: Int
    ): GrammarExerciseComponent =
        RealGrammarExerciseComponent(componentContext, grammarId, _onBack = {
            navigation.pop()
        })

    private sealed interface Config : Parcelable {

        @Parcelize
        object AllGrammars : Config

        @Parcelize
        data class Details(val entity: GrammarElementViewEntity) : Config

        @Parcelize
        data class Exercise(val grammarId: Int) : Config
    }

    override val childStack: Value<ChildStack<*, GrammarMainScreen.Child>> = _childStack
}