package nastya.cheprasova.fluentflow.decompose.dialog

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class RealErrorLearningWordsDialogComponent(
    componentContext: ComponentContext,
    val word: String,
    val correctWords: String,
    val chooseWord: String,
    val onDismissed: () -> Unit
) : ComponentContext by componentContext, DialogComponent {

    override val state: Value<DialogState> = MutableValue(createState())

    override fun onDismissClicked() = onDismissed()

    private fun createState(): DialogState.ErrorDialogState =
        DialogState.ErrorDialogState(word, correctWords, chooseWord)

}