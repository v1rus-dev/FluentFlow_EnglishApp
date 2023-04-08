package yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words

import android.net.Uri

data class WordsTopicViewEntity(
    val title: String,
    val allCount: Int,
    val endedCount: Int,
    val imagePath: Uri,
    val fileName: String
)