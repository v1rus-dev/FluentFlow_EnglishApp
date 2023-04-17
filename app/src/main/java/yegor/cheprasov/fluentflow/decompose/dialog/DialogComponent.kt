package yegor.cheprasov.fluentflow.decompose.dialog

import com.arkivanov.decompose.value.Value

interface DialogComponent {

    val state: Value<DialogState>

    fun onDismissClicked()

}

sealed interface DialogState {

    data class ErrorDialogState(
        val word: String,
        val correctWord: String,
        val chooseWord: String
    ) : DialogState

    data class SuccessDialogState(
        val word: String
    ) : DialogState

}