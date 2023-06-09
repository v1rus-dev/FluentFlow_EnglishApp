package nastya.cheprasova.fluentflow.decompose.grammarThemes

import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.data.usecase.Level
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity

interface GrammarThemesComponent {

    val uiState: Value<GrammarState>

    fun event(event: Event)

    sealed interface Event {
        object Back : Event

        data class SelectNewLevel(val level: Level) : Event
        data class ClickOnTheme(val value: GrammarElementViewEntity) : Event

        data class MakeFavorite(val value: GrammarElementViewEntity) : Event
    }
}