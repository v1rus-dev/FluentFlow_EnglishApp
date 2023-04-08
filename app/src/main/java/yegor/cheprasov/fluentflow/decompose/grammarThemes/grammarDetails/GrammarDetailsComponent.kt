package yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails

import com.arkivanov.decompose.value.Value

interface GrammarDetailsComponent {

    val uiState: Value<GrammarUiStateDetail>

    fun event(event: Event)

    sealed interface Event {
        object OnBack : Event
        object OpenExercise : Event
    }
}