package yegor.cheprasov.fluentflow.decompose.mainScreen.words

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.components.NewWordsAndPhrasesState
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.state.WordsState

class FakeWordsMainComponent : WordsMainComponent {
    override val uiState: Value<WordsState>
        get() = MutableValue(WordsState(NewWordsAndPhrasesState(0,0), emptyList()))

    override fun openTopic(topic: WordsTopicViewEntity) = Unit
}