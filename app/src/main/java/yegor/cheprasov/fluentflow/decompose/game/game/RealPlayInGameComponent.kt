package yegor.cheprasov.fluentflow.decompose.game.game

import com.arkivanov.decompose.ComponentContext
import yegor.cheprasov.fluentflow.decompose.BaseComponent

class RealPlayInGameComponent(
    componentContext: ComponentContext,
    private val gameMode: PlayInGameComponent.GameMode,
    private val _onBack: () -> Unit
) : BaseComponent(componentContext), PlayInGameComponent {


    override fun onBack() {
        super.onBack()
        onBack()
    }

    override fun event(event: PlayInGameComponent.Event) {
        when (event) {
            PlayInGameComponent.Event.OnBack -> _onBack()
        }
    }


}