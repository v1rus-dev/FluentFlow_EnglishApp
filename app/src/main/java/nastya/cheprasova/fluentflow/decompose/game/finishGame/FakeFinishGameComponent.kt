package nastya.cheprasova.fluentflow.decompose.game.finishGame

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.gameScreen.FinishGameState

class FakeFinishGameComponent : FinishGameComponent {
    override val uiState: Value<FinishGameState>
        get() = MutableValue(
            FinishGameState(
                allWords = 10,
                correctWords = 8,
                errorWords = listOf(
                    FinishGameComponent.Word(
                        "dog", "собака"
                    ), FinishGameComponent.Word(
                        "cat", "кот"
                    )
                )
            )
        )

    override fun event(event: FinishGameComponent.Event) = Unit
}