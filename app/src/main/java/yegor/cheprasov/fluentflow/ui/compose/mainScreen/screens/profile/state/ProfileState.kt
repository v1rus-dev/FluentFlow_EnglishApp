package yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state

data class ProfileState(
    val name: String,
    val imgPath: String,
    val wordsInfoList: List<WordsInfo>,
    val grammarExerciseInfoList: List<InfoCardElement>
)

data class WordsInfo(
    val name: String,
    val count: Int,
    val ended: Int,
    val img: String
)

data class InfoCardElement(
    val name: String,
    val percentage: Int
)