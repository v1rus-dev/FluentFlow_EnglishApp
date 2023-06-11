package nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.state

import nastya.cheprasova.fluentflow.ui.compose.components.NewWordsAndPhrasesState
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

data class WordsState(
    val newWordsAndPhrasesState: NewWordsAndPhrasesState,
    val list: List<WordsTopicViewEntity>
)