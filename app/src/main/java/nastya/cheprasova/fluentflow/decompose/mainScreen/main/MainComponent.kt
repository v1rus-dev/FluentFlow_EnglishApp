package nastya.cheprasova.fluentflow.decompose.mainScreen.main

import androidx.annotation.DrawableRes
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.words.WordsMainComponent
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

interface MainComponent {

    val profileComponent: ProfileMainComponent
    val themesComponent: ThemesMainComponent
    val wordsComponent: WordsMainComponent

    val selectedItem: Value<MenuItem>

    fun onEvent(event: Event)

    fun selectItem(newItem: MenuItem)

    sealed interface Event {
        object OpenGrammars : Event
        object OpenExercises : Event
        object OpenGames : Event

        data class OpenTopicWords(val topic: WordsTopicViewEntity) : Event
    }

    val bottomNavItems: List<MenuItem>

    data class MenuItem(
        val menuName: String,
        @DrawableRes val menuIcon: Int,
        val id: Int
    )
}