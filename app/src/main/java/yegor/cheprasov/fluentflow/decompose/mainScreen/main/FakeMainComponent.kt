package yegor.cheprasov.fluentflow.decompose.mainScreen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Person
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.FakeProfileComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.FakeThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.FakeWordsMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.WordsMainComponent

class FakeMainComponent : MainComponent {

    private val _selectedItem = MutableValue(bottomNavItems.first())

    override val profileComponent: ProfileMainComponent = FakeProfileComponent()
    override val themesComponent: ThemesMainComponent = FakeThemesMainComponent()
    override val wordsComponent: WordsMainComponent = FakeWordsMainComponent()
    override val selectedItem: Value<MainComponent.MenuItem>
        get() = _selectedItem

    override fun onEvent(event: MainComponent.Event) = Unit
    override fun selectItem(newItem: MainComponent.MenuItem) {
        _selectedItem.update { newItem }
    }

    override val bottomNavItems: List<MainComponent.MenuItem>
        get() = listOf(
            MainComponent.MenuItem(menuName = "Темы", menuIcon = Icons.Outlined.AssignmentInd, 0),
            MainComponent.MenuItem(menuName = "Слова", menuIcon = Icons.Outlined.Comment, 1),
            MainComponent.MenuItem(menuName = "Профиль", menuIcon = Icons.Outlined.Person, 2)
        )
}