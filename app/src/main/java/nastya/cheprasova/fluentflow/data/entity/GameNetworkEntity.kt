package nastya.cheprasova.fluentflow.data.entity

data class GameNetworkEntity(
    val correctWord: String = "",
    val translate: String = "",
    val gameId: Int = 0,
    val imgUri: String = "",
    val level: Int = 0,
    val words: List<String> = listOf()
)
