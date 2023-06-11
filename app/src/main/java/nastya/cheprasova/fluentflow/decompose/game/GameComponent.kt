package nastya.cheprasova.fluentflow.decompose.game

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.game.finishGame.FinishGameComponent
import nastya.cheprasova.fluentflow.decompose.game.game.PlayInGameComponent
import nastya.cheprasova.fluentflow.decompose.game.selectMod.SelectModComponent

interface GameComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class SelectMod(val component: SelectModComponent) : Child

        data class PlayInGame(val component: PlayInGameComponent) : Child

        data class FinishGame(val component: FinishGameComponent) : Child
    }

}