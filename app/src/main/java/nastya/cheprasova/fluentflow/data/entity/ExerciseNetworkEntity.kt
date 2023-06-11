package nastya.cheprasova.fluentflow.data.entity

data class ExerciseNetworkEntity(
    val correctWords: List<String> = listOf(),
    val sentense: String = "",
    val words: List<String> = listOf(),
    val level: Int = 0
)
