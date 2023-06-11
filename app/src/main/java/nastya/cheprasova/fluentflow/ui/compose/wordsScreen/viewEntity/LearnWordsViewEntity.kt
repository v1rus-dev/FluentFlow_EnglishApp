package nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity

data class LearnWordsViewEntity(
    val word: String,
    val translations: List<LearnWordsTranslateViewEntity>
)

data class LearnWordsTranslateViewEntity(
    val word: String,
    val isCorrect: Boolean,
    var isClicked: Boolean = false
)