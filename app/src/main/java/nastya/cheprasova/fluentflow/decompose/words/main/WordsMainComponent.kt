package nastya.cheprasova.fluentflow.decompose.words.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.words.learn.LearnWordsComponent
import nastya.cheprasova.fluentflow.decompose.words.information.WordsInformationComponent
import nastya.cheprasova.fluentflow.decompose.words.selectWordsForLearning.SelectWordsForLearningComponent

interface WordsMainComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Information(val component: WordsInformationComponent) : Child

        data class SelectWordsForLearning(val component: SelectWordsForLearningComponent) : Child

        data class LearnWords(val component: LearnWordsComponent) : Child
    }
}