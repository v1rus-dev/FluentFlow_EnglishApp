package yegor.cheprasov.fluentflow.decompose.game.finishGame

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.gameScreen.FinishGameState

class RealFinishGameComponent(
    componentContext: ComponentContext,
    private val allWords: Int,
    private val correctWords: Int,
    private val words: List<FinishGameComponent.Word>,
    private val _close: () -> Unit
) : BaseComponent(componentContext), FinishGameComponent {

    private val mutableUiState = MutableValue(createState())
    override val uiState: Value<FinishGameState>
        get() = mutableUiState

    override fun event(event: FinishGameComponent.Event) {
        when(event) {
            FinishGameComponent.Event.Continue -> _close()
        }
    }

    private fun createState(): FinishGameState =
        FinishGameState(allWords, correctWords, words)
}