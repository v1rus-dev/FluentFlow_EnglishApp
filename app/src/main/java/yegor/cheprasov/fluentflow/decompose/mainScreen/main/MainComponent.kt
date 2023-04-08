package yegor.cheprasov.fluentflow.decompose.mainScreen.main

import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.WordsMainComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

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
        val menuIcon: ImageVector,
        val id: Int
    )
}