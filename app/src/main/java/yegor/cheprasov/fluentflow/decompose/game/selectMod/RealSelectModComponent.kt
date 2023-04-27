package yegor.cheprasov.fluentflow.decompose.game.selectMod

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.BaseComponent

class RealSelectModComponent(
    componentContext: ComponentContext,
    private val value: Value<Boolean>,
    private val _event: (SelectModComponent.Event) -> Unit,
    private val _close: () -> Unit
) : BaseComponent(componentContext), SelectModComponent {

    private var isCanOpen: Boolean = false

    init {
        observeIsFinished()
    }

    override fun event(event: SelectModComponent.Event) {
        _event(event)
    }

    private fun observeIsFinished() {
        value.subscribe {
            isCanOpen = true
        }
    }

    override fun onBack() {
        super.onBack()
        _close()
    }

}