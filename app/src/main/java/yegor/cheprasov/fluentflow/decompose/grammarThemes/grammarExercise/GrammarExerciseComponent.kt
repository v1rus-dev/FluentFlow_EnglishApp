package yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise

import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.state.GrammarExerciseUiState

interface GrammarExerciseComponent {

    val uiState: Value<GrammarExerciseUiState>

    val finish: Value<Boolean>

    fun checkAnswer(vararg answer: String)

    fun finish(isLast: Boolean)

    fun close()

}