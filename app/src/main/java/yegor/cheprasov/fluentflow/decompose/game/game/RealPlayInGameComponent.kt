package yegor.cheprasov.fluentflow.decompose.game.game

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.room.entities.GameEntity
import yegor.cheprasov.fluentflow.data.usecase.GameUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.game.finishGame.FinishGameComponent
import yegor.cheprasov.fluentflow.ui.compose.gameScreen.GameState
import yegor.cheprasov.fluentflow.ui.compose.gameScreen.GameWord
import yegor.cheprasov.fluentflow.utils.KorosTimerTask
import kotlin.time.Duration.Companion.seconds

class RealPlayInGameComponent(
    componentContext: ComponentContext,
    private val gameMode: PlayInGameComponent.GameMode,
    private val _onContinue: (Int, Int, List<FinishGameComponent.Word>) -> Unit,
    private val _onBack: () -> Unit
) : BaseComponent(componentContext), PlayInGameComponent {

    private val gameUseCase: GameUseCase by inject()

    private var currentIndex = 0
    private lateinit var currentGame: GameEntity
    private val gameList = arrayListOf<GameEntity>()
    private val errorWords = arrayListOf<FinishGameComponent.Word>()

    private var time = 30
    private val timer = KorosTimerTask.getInstance(
        name = "PlayInGameTimer",
        repeat = 1.seconds,
        coroutineScope = scope
    ) {
        tick()
    }

    private val mutableUiState = MutableValue(
        GameState(
            timer = "",
            timerIsEnding = false,
            imgUri = "",
            words = listOf(),
            currentIndex = 0
        )
    )

    init {
        getListForMode()
    }

    private fun getListForMode() = scope.launch {
        val list = gameUseCase.getListByMode(gameMode)

        Log.d("myTag", "list: $list")
        if (list.isEmpty()) {
            scope.launch(Dispatchers.Main) {
                _onBack()
            }
            return@launch
        }

        gameList.addAll(list)
        currentGame = gameList.first()
        timer.start()
        val newWords = arrayListOf<GameWord>()
        currentGame.words.forEach {
            newWords.add(GameWord(it))
        }
        newWords.add(GameWord(currentGame.correctWord))
        mutableUiState.update {
            GameState(
                timer = getTime(),
                timerIsEnding = time <= 5,
                imgUri = currentGame.imgUri,
                words = newWords.shuffled(),
                currentIndex = 0
            )
        }
    }

    override fun onBack() {
        super.onBack()
        _onBack()
    }

    override val uiState: Value<GameState>
        get() = mutableUiState

    override fun event(event: PlayInGameComponent.Event) {
        when (event) {
            PlayInGameComponent.Event.OnBack -> _onBack()
            is PlayInGameComponent.Event.CheckWord -> checkWord(event.selectedWord)
        }
    }

    private fun checkWord(word: String) {
        val newWords = arrayListOf<GameWord>()
        uiState.value.words.forEach {
            if (it.word == word) {
                newWords.add(
                    it.copy(
                        isCorrect = word == currentGame.correctWord
                    )
                )
            } else {
                newWords.add(it)
            }
        }
        mutableUiState.update { it.copy(words = newWords) }

        if (word == currentGame.correctWord) {
            saveAsEnded(currentGame)
        } else {
            errorWords.add(FinishGameComponent.Word(currentGame.correctWord, currentGame.translate))
        }

        if (currentGame != gameList.last()) {
            scope.launch(Dispatchers.IO) {
                delay(300L)
                next()
            }
        } else {
            finish()
        }
    }

    private fun saveAsEnded(currentGame: GameEntity) = scope.launch {
        gameUseCase.saveGameAsEnded(currentGame.gameId)
    }

    private fun tick() {
        time--
        if (time >= 0) {
            mutableUiState.update {
                it.copy(timer = getTime())
            }
        } else {
            timer.cancel()
            if (currentGame != gameList.last()) {
                errorWords.add(
                    FinishGameComponent.Word(
                        currentGame.correctWord,
                        currentGame.translate
                    )
                )
                next()
            } else {
                finish()
            }
        }
    }

    private fun getTime(): String =
        if (time > 10) {
            time.toString()
        } else {
            "0$time"
        }

    private fun next() {
        currentIndex++
        currentGame = gameList[currentIndex]
        timer.cancel()
        time = 30
        val newWords = arrayListOf<GameWord>()
        currentGame.words.forEach {
            newWords.add(GameWord(it))
        }
        newWords.add(GameWord(currentGame.correctWord))
        mutableUiState.update {
            GameState(
                timer = getTime(),
                timerIsEnding = time <= 5,
                imgUri = currentGame.imgUri,
                words = newWords,
                currentIndex = currentIndex
            )
        }
        timer.start()
    }

    private fun finish() = scope.launch(Dispatchers.Main) {
        timer.cancel()
        val allWordsSize = gameList.size
        val correct = allWordsSize - errorWords.size
        _onContinue(allWordsSize, correct, errorWords)
    }
}