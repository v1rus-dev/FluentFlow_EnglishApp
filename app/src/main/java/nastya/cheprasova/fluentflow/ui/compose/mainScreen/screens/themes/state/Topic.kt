package nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes.state

import androidx.annotation.DrawableRes

data class Topic(
    val title: String,
    val desc: String,
    val percentages: Int,
    val id: Int,
    @DrawableRes val background: Int,
    @DrawableRes val image: Int
)