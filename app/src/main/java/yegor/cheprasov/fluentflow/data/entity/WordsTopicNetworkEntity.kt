package yegor.cheprasov.fluentflow.data.entity

data class WordsTopicNetworkEntity(
    val fileName: String = "",
    val imagesFolder: String = "",
    val title: String = "",
    val wordsThemId: Int = 0
)

data class WordsFromFile(
    val list: List<WordsFromFileEntity> = listOf()
)

data class WordsFromFileEntity(
    val word: String,
    val translate: String
)