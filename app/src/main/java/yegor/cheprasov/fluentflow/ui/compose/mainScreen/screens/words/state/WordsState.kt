package yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.state

import yegor.cheprasov.fluentflow.ui.compose.components.NewWordsAndPhrasesState
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

data class WordsState(
    val newWordsAndPhrasesState: NewWordsAndPhrasesState,
    val list: List<WordsTopicViewEntity>
)