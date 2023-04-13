package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state

import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity

sealed interface LearnWordsState {
    object Initialize : LearnWordsState

    data class Success(
        val words: List<LearnWordsViewEntity>
    ) : LearnWordsState
}

