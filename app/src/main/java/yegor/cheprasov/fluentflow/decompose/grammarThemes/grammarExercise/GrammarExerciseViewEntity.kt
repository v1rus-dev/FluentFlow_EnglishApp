package yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise

import android.net.Uri

data class GrammarExerciseViewEntity(
    val translate: String,
    val image: Uri,
    val text: List<String>,
    val words: List<String>,
    val correctWords: List<String>,
    val correctPhrase: String
)