package nastya.cheprasova.fluentflow.ui.compose.wordsScreen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import nastya.cheprasova.fluentflow.decompose.words.main.WordsMainComponent
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.screens.LearnWordsScreen
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.screens.SelectWordsForLearningScreen
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.screens.WordsInformationScreen

@Composable
fun WordsScreen(component: WordsMainComponent) {
    Children(stack = component.childStack) {
        when (val instance = it.instance) {
            is WordsMainComponent.Child.Information -> WordsInformationScreen(component = instance.component)
            is WordsMainComponent.Child.SelectWordsForLearning -> SelectWordsForLearningScreen(
                component = instance.component
            )

            is WordsMainComponent.Child.LearnWords -> LearnWordsScreen(component = instance.component)
        }
    }
}