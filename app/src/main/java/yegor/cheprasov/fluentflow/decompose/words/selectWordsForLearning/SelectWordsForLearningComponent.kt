package yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning

import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity

interface SelectWordsForLearningComponent {

    val uiState: Value<SelectWordsForLearningState>

    fun event(event: Event)

    sealed interface Event {
        object Close : Event

        data class OnLearn(val wordsForLearningViewEntity: WordsForLearningViewEntity) : Event

        data class OnIKnow(val wordsForLearningViewEntity: WordsForLearningViewEntity) : Event

        object OnContinue : Event
    }
}