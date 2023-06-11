package nastya.cheprasova.fluentflow.decompose.mainScreen.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import nastya.cheprasova.fluentflow.R
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.profile.RealProfileMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.themes.RealThemesMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.words.RealWordsMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.words.WordsMainComponent

class RealMainComponent(
    componentContext: ComponentContext,
    private val _onEvent: (MainComponent.Event) -> Unit
) : BaseComponent(componentContext), MainComponent {

    private val _selectedItem = MutableValue(bottomNavItems.first())

    override val selectedItem: Value<MainComponent.MenuItem>
        get() = _selectedItem

    override val bottomNavItems: List<MainComponent.MenuItem>
        get() = listOf(
            MainComponent.MenuItem(menuName = "Темы", menuIcon = R.drawable.ic_plan, 0),
            MainComponent.MenuItem(menuName = "Слова", menuIcon = R.drawable.ic_words, 1),
            MainComponent.MenuItem(menuName = "Профиль", menuIcon = R.drawable.ic_profile, 2)
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