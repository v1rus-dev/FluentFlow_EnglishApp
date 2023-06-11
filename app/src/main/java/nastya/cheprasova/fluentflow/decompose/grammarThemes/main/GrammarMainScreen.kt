package nastya.cheprasova.fluentflow.decompose.grammarThemes.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.grammarThemes.GrammarThemesComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails.GrammarDetailsComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarExercise.GrammarExerciseComponent

interface GrammarMainScreen {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class AllThemes(val component: GrammarThemesComponent) : Child
        class Details(val component: GrammarDetailsComponent) : Child
        class Exercise(val component: GrammarExerciseComponent) : Child
    }

}