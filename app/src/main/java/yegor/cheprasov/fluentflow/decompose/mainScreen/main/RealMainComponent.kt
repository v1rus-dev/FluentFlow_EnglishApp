package yegor.cheprasov.fluentflow.decompose.mainScreen.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
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
) : BaseComponent(componentContext),
    MainComponent {

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
        childContext(key = "words")
    )

    override fun onEvent(event: MainComponent.Event) {

    }

}