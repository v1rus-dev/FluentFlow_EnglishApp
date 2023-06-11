package nastya.cheprasova.fluentflow.ui.compose.gameScreen

import nastya.cheprasova.fluentflow.decompose.game.finishGame.FinishGameComponent

data class FinishGameState(
    val allWords: Int,
    val correctWords: Int,
    val errorWords: List<FinishGameComponent.Word>
)
