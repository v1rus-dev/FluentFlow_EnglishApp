package yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words

import android.net.Uri
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class WordsTopicViewEntity(
    val title: String,
    val allCount: Int,
    val endedCount: Int,
    val imagePath: Uri,
    val fileName: String
): Parcelable