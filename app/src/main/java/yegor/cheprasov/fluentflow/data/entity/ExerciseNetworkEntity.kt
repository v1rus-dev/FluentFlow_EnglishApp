package yegor.cheprasov.fluentflow.data.entity

data class ExerciseNetworkEntity(
    val correctWords: List<String>,
    val sentense: String,
    val words: List<String>,
    val level: Int
)
