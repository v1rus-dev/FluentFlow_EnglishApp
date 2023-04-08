package yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.state.GrammarExerciseUiState

class FakeGrammarExerciseComponent : GrammarExerciseComponent {
    override val uiState: Value<GrammarExerciseUiState>
        get() = MutableValue(GrammarExerciseUiState.Loading)
    override val finish: Value<Boolean>
        get() = MutableValue(false)

    override fun checkAnswer(vararg answer: String) = Unit
    override fun finish(isLast: Boolean) = Unit
    override fun close() = Unit
}