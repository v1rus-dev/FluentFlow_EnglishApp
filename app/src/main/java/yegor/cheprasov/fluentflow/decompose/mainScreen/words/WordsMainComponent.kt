package yegor.cheprasov.fluentflow.decompose.mainScreen.words

import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.state.WordsState

interface WordsMainComponent {
    val uiState: Value<WordsState>

    fun openTopic(topic: WordsTopicViewEntity)
}