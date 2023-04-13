package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class WordsForLearningViewEntity(
    val word: String,
    val translate: String
) : Parcelable