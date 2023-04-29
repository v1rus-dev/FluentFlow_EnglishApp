package yegor.cheprasov.fluentflow.decompose.mainScreen.profile

import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.ProfileState

interface ProfileMainComponent {

    val state: Value<ProfileState>

    fun event(event: Event)

    sealed interface Event {

        data class ChangeName(val newName: String) : Event

    }
}