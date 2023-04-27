package yegor.cheprasov.fluentflow.ui.compose.gameScreen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import yegor.cheprasov.fluentflow.decompose.game.GameComponent

@Composable
fun GameScreen(component: GameComponent) {
    Children(stack = component.childStack) {
        when(val instance = it.instance) {
            is GameComponent.Child.SelectMod -> SelectModeScreen(selectModComponent = instance.component)
            is GameComponent.Child.PlayInGame -> PlayInGameScreen(playInGameComponent = instance.component)
            is GameComponent.Child.PreGame -> PreGameScreen(component = instance.component)
            is GameComponent.Child.FinishGame -> FinishGameScreen(component = instance.component)
        }
    }
}