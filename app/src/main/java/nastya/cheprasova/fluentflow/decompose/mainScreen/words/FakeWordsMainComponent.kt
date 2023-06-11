package nastya.cheprasova.fluentflow.decompose.mainScreen.words

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.components.NewWordsAndPhrasesState
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.state.WordsState

class FakeWordsMainComponent : WordsMainComponent {
    override val uiState: Value<WordsState>
        get() = MutableValue(WordsState(NewWordsAndPhrasesState(0,0), emptyList()))

    override fun openTopic(topic: WordsTopicViewEntity) = Unit
}