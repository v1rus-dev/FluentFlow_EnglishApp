package nastya.cheprasova.fluentflow.decompose.words.learn

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.dialog.DialogComponent
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.state.LearnWordsState
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsTranslateViewEntity
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity

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
    override val errorDialog: Value<ChildSlot<*, DialogComponent>>
        get() = MutableValue(
            ChildSlot<Unit, DialogComponent>(child = null)
        )

    override fun event(event: LearnWordsComponent.Event) = Unit
}