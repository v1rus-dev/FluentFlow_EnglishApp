package yegor.cheprasov.fluentflow.decompose.dialog

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class SuccessDialog(
    componentContext: ComponentContext,
    val word: String,
    val onDismissed: () -> Unit
) : ComponentContext by componentContext, DialogComponent {

    override val state: Value<DialogState> = MutableValue(createState())

    override fun onDismissClicked() = onDismissed()

    private fun createState(): DialogState.SuccessDialogState =
        DialogState.SuccessDialogState(word)
}