package yegor.cheprasov.fluentflow.decompose.game.selectMod

import com.arkivanov.decompose.ComponentContext
import yegor.cheprasov.fluentflow.decompose.BaseComponent

class RealSelectModComponent(
    componentContext: ComponentContext,
    private val _event: (SelectModComponent.Event) -> Unit
) : BaseComponent(componentContext), SelectModComponent {

    override fun event(event: SelectModComponent.Event) {
        _event(event)
    }

}