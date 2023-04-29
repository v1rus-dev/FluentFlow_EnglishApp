package yegor.cheprasov.fluentflow.decompose.grammarThemes

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity
import yegor.cheprasov.fluentflow.data.usecase.Level

class FakeGrammarThemesComponent : GrammarThemesComponent {
    override val uiState: Value<GrammarState>
        get() = MutableValue(
            GrammarState(
                currentLevel = Level.Middle,
                grammars = listOf(
                    GrammarEntity(
                        title = "Title",
                        grammarId = 0,
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        level = 0,
                        allExercises = 0,
                        endedExercises = 0,
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        grammarId = 0,
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        level = 0,
                        allExercises = 0,
                        endedExercises = 0,
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        grammarId = 0,
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        level = 0,
                        allExercises = 0,
                        endedExercises = 0,
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        grammarId = 0,
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        level = 0,
                        allExercises = 0,
                        endedExercises = 0,
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        grammarId = 0,
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        level = 0,
                        allExercises = 0,
                        endedExercises = 0,
                        examples = listOf("Am", "is", "are")
                    ),
                    GrammarEntity(
                        title = "Title",
                        grammarId = 0,
                        subtitle = "Subtitle",
                        fileName = "",
                        exerciseFile = "",
                        level = 0,
                        allExercises = 0,
                        endedExercises = 0,
                        examples = listOf("Am", "is", "are")
                    )
                )
            )
        )

    override fun event(event: GrammarThemesComponent.Event) = Unit
}