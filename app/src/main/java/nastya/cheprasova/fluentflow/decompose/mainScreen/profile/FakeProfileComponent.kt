package nastya.cheprasova.fluentflow.decompose.mainScreen.profile

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.profile.state.InfoCardElement
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.profile.state.ProfileState
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.profile.state.WordsInfo

class FakeProfileComponent : ProfileMainComponent {
    override val state: Value<ProfileState>
        get() = MutableValue(
            ProfileState(
                name = "Test Name",
                imgPath = "",
                wordsInfoList = listOf(
                    WordsInfo(
                        name = "Книги",
                        count = 34,
                        ended = 10,
                        img = ""
                    )
                ),
                grammarExerciseInfoList = listOf(
                    InfoCardElement("Am, is, are", 100),
                    InfoCardElement("Натоящее время", 95),
                    InfoCardElement("Сколько стоит", 15),
                    InfoCardElement("Я, мы, ты", 35),
                )
            )
        )

    override fun event(event: ProfileMainComponent.Event) = Unit
}