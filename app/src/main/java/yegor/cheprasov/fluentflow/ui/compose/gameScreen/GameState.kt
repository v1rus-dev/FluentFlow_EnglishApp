package yegor.cheprasov.fluentflow.ui.compose.gameScreen

data class GameState(
    val timer: String,
    val timerIsEnding: Boolean,
    val imgUri: String,
    val words: List<GameWord>,
    val currentIndex: Int
)

data class GameWord(
    val word: String,
    val isCorrect: Boolean? = null
)