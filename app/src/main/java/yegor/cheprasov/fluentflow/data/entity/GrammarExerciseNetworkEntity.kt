package yegor.cheprasov.fluentflow.data.entity

import android.net.Uri

data class GrammarExerciseNetworkEntity(
    val count: Int,
    val list: List<GrammarOneExercise>
)

data class GrammarOneExercise(
    val translate: String,
    val imageFile: String,
    val imagePath: String,
    val text: List<String>,
    val words: List<String>,
    val correctWords: List<String>,
    val correctPhrase: String
)

data class GrammarExercises(
    val count: Int,
    val list: List<GrammarExercise>
)

data class GrammarExercise(
    val translate: String,
    val imagePath: Uri,
    val text: List<String>,
    val words: List<String>,
    val correctWords: List<String>,
    val correctPhrase: String
)