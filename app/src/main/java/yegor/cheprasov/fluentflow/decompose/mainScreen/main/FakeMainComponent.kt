package yegor.cheprasov.fluentflow.decompose.mainScreen.main

import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.FakeProfileComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.FakeThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.FakeWordsMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.words.WordsMainComponent

class FakeMainComponent : MainComponent {
    override val profileComponent: ProfileMainComponent = FakeProfileComponent()
    override val themesComponent: ThemesMainComponent = FakeThemesMainComponent()
    override val wordsComponent: WordsMainComponent = FakeWordsMainComponent()

    override fun onEvent(event: MainComponent.Event) = Unit
}