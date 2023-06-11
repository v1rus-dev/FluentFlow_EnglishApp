package nastya.cheprasova.fluentflow.decompose.mainScreen.profile

import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.profile.state.ProfileState

interface ProfileMainComponent {

    val state: Value<ProfileState>

    fun event(event: Event)

    sealed interface Event {

        data class ChangeName(val newName: String) : Event

    }
}