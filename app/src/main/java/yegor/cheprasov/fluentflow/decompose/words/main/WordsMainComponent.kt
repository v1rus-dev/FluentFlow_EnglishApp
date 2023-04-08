package yegor.cheprasov.fluentflow.decompose.words.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.words.WordsInformationComponent

interface WordsMainComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Information(val component: WordsInformationComponent) : Child
    }
}