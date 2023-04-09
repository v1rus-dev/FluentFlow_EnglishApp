package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state

import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity

sealed interface SelectWordsForLearningState {

    object None : SelectWordsForLearningState
    data class Select(
        val words: List<WordsForLearningViewEntity>,
        val selectedCount: Int,
        val maxCountForSelected: Int = 4
    ) : SelectWordsForLearningState

    data class Check(
        val words: List<WordsForLearningViewEntity>
    ) : SelectWordsForLearningState
}
