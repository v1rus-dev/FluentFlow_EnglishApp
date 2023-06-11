package nastya.cheprasova.fluentflow.decompose.mainScreen.words

import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.state.WordsState

interface WordsMainComponent {
    val uiState: Value<WordsState>

    fun openTopic(topic: WordsTopicViewEntity)
}