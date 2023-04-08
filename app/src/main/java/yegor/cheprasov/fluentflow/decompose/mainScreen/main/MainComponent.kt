package yegor.cheprasov.fluentflow.decompose.mainScreen.main

import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.WordsMainComponent

interface MainComponent {

    val profileComponent: ProfileMainComponent
    val themesComponent: ThemesMainComponent
    val wordsComponent: WordsMainComponent

    fun onEvent(event: Event)

    sealed interface Event {
        object OpenGrammars : Event
        object OpenExercises : Event
        object OpenGames : Event
    }
}