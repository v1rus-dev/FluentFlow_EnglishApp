package yegor.cheprasov.fluentflow.decompose.words.learn

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.LearnWordsState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsTranslateViewEntity
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity

class FakeLearnWordsComponent : LearnWordsComponent {
    override val uiState: Value<LearnWordsState>
        get() = MutableValue(
            LearnWordsState.Success(
                words = listOf(
                    LearnWordsViewEntity(
                        word = "flight attendant",
                        translations = listOf(
                            LearnWordsTranslateViewEntity(
                                word = "абсолютно",
                                isCorrect = false
                            ),
                            LearnWordsTranslateViewEntity(
                                word = "бортпроводник",
                                isCorrect = true
                            ),
                            LearnWordsTranslateViewEntity(
                                word = "дававать",
                                isCorrect = false
                            ),
                            LearnWordsTranslateViewEntity(
                                word = "автостоп",
                                isCorrect = false
                            )
                        )
                    )
                )
            )
        )

    override fun event(event: LearnWordsComponent.Event) = Unit
}