package yegor.cheprasov.fluentflow.decompose.game.preGame

import com.arkivanov.decompose.ComponentContext
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.game.game.PlayInGameComponent

class RealPreGameComponent(
    componentContext: ComponentContext,
    private val mode: PlayInGameComponent.GameMode,
    private val onStart: (PlayInGameComponent.GameMode) -> Unit
) : BaseComponent(componentContext), PreGameComponent {

    override fun event(event: PreGameComponent.Event) {
        when(event) {
            PreGameComponent.Event.Start -> onStart(mode)
        }
    }
}