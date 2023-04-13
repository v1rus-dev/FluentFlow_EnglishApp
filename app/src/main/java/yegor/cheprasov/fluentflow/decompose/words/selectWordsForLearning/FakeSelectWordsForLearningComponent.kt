package yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity

class FakeSelectWordsForLearningComponent : SelectWordsForLearningComponent {
    override val uiState: Value<SelectWordsForLearningState>
        get() = MutableValue(SelectWordsForLearningState.Select(
            words = listOf(
                WordsForLearningViewEntity(
                    word = "flight attendant",
                    translate = "Бортпроводник"
                ),
                WordsForLearningViewEntity(
                    word = "hitchhike",
                    translate = "Автостоп"
                ),
                WordsForLearningViewEntity(
                    word = "suncreen",
                    translate = "Солнцезащитный крем"
                ),
            ),
            selectedCount = 1
        ))

    override fun event(event: SelectWordsForLearningComponent.Event) = Unit
}