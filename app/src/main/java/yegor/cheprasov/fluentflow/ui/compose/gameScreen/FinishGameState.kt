package yegor.cheprasov.fluentflow.ui.compose.gameScreen

import yegor.cheprasov.fluentflow.decompose.game.finishGame.FinishGameComponent

data class FinishGameState(
    val allWords: Int,
    val correctWords: Int,
    val errorWords: List<FinishGameComponent.Word>
)
