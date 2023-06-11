package nastya.cheprasova.fluentflow.decompose.onboarding

import com.arkivanov.decompose.value.Value

interface OnBoardingComponent {

    val uiState: Value<SelectedState>

    fun event(event: Event)

    sealed interface Event {

        object SelectElementary : Event

        object SelectMiddle : Event

        object SelectAdvanced : Event

        object Continue : Event

    }

}

data class SelectedState(
    val selectedLevel: Int? = null
)