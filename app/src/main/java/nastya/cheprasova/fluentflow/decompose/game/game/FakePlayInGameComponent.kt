package nastya.cheprasova.fluentflow.decompose.game.game

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.gameScreen.GameState
import nastya.cheprasova.fluentflow.ui.compose.gameScreen.GameWord

class FakePlayInGameComponent : PlayInGameComponent {

    private val correctWord = "cat"
    private val list = listOf(
        GameWord(
            word = "dog"
        ),
        GameWord(word = "cat"),
        GameWord(word = "rat")
    )
    override val uiState: Value<GameState>
        get() = MutableValue(
            GameState(
                timer = "30",
                timerIsEnding = false,
                imgUri = "",
                words = list,
                currentIndex = 0
            )
        )

    private fun checkWord(word: String) {
        val newList = arrayListOf<GameWord>()
        list.forEach {
            if (it.word == word) {
                newList.add(
                    it.copy(
                        isCorrect = word == correctWord
                    )
                )
            } else {
                newList.add(it)
            }
        }
    }

    override fun event(event: PlayInGameComponent.Event) {
        when (event) {
            is PlayInGameComponent.Event.CheckWord -> checkWord(event.selectedWord)
            PlayInGameComponent.Event.OnBack -> Unit
        }
    }
}