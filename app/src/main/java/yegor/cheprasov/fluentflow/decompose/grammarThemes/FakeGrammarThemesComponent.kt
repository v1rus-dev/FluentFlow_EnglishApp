package yegor.cheprasov.fluentflow.decompose.grammarThemes

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity

class FakeGrammarThemesComponent : GrammarThemesComponent {
    override val uiState: Value<GrammarState>
        get() = MutableValue(
            GrammarState(
                grammars = listOf(
                    GrammarEntity(
                        title = "Title",
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        examples = listOf("Am", "is", "are")
                    )
                )
            )
        )

    override fun event(event: GrammarThemesComponent.Event) = Unit
}