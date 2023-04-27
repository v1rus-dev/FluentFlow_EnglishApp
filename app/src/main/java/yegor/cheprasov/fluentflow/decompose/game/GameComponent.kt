package yegor.cheprasov.fluentflow.decompose.game

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.game.finishGame.FinishGameComponent
import yegor.cheprasov.fluentflow.decompose.game.game.PlayInGameComponent
import yegor.cheprasov.fluentflow.decompose.game.preGame.PreGameComponent
import yegor.cheprasov.fluentflow.decompose.game.selectMod.SelectModComponent

interface GameComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class SelectMod(val component: SelectModComponent) : Child

        data class PreGame(val component: PreGameComponent) : Child

        data class PlayInGame(val component: PlayInGameComponent) : Child

        data class FinishGame(val component: FinishGameComponent) : Child
    }

}