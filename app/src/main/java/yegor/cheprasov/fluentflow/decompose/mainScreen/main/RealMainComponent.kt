package yegor.cheprasov.fluentflow.decompose.mainScreen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Person
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.RealProfileMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.RealThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.RealWordsMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.WordsMainComponent

class RealMainComponent(
    componentContext: ComponentContext,
    private val _onEvent: (MainComponent.Event) -> Unit
) : BaseComponent(componentContext), MainComponent {

    private val _selectedItem = MutableValue(bottomNavItems.first())

    override val selectedItem: Value<MainComponent.MenuItem>
        get() = _selectedItem

    override val bottomNavItems: List<MainComponent.MenuItem>
        get() = listOf(
            MainComponent.MenuItem(menuName = "Темы", menuIcon = Icons.Outlined.AssignmentInd, 0),
            MainComponent.MenuItem(menuName = "Слова", menuIcon = Icons.Outlined.Comment, 1),
            MainComponent.MenuItem(menuName = "Профиль", menuIcon = Icons.Outlined.Person, 2)
        )

    override val profileComponent: ProfileMainComponent = RealProfileMainComponent(
        childContext(key = "profile")
    )

    override val themesComponent: ThemesMainComponent = RealThemesMainComponent(
        childContext(key = "themes"),
        onEvent = { event ->
            when (event) {
                ThemesMainComponent.Event.OpenExercises -> _onEvent(MainComponent.Event.OpenExercises)
                ThemesMainComponent.Event.OpenGames -> _onEvent(MainComponent.Event.OpenGames)
                ThemesMainComponent.Event.OpenGrammars -> _onEvent(MainComponent.Event.OpenGrammars)
            }
        }
    )

    override val wordsComponent: WordsMainComponent = RealWordsMainComponent(
        childContext(key = "words"),
        _openTopic = {
            _onEvent(MainComponent.Event.OpenTopicWords(it))
        }
    )

    override fun selectItem(newItem: MainComponent.MenuItem) {
        _selectedItem.update { newItem }
    }

    override fun onEvent(event: MainComponent.Event) {

    }

}