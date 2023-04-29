package yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state

data class ProfileState(
    val name: String,
    val imgPath: String,
    val wordsInfoList: List<WordsInfo>
)

data class WordsInfo(
    val name: String,
    val count: Int,
    val img: String
)