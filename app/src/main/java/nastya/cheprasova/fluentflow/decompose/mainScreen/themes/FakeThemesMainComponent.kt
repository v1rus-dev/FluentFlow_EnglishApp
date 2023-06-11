package nastya.cheprasova.fluentflow.decompose.mainScreen.themes

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.R
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes.state.Topic
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes.state.TopicsState

class FakeThemesMainComponent : ThemesMainComponent {
    override val topics: Value<TopicsState>
        get() = MutableValue(getTopicState())

    override fun event(event: ThemesMainComponent.Event) = Unit

    private fun getTopicState(): TopicsState =
        TopicsState(
            topics = listOf(
                Topic(
                    "Грамматика",
                    "Изучайте правила грамматики",
                    66,
                    0,
                    background = R.drawable.first_them_bg,
                    image = R.drawable.grammar_img
                ),
                Topic(
                    title = "Упражнения",
                    desc = "Практикуйте ваш английский",
                    percentages = 46,
                    id = 1,
                    background = R.drawable.first_them_bg,
                    image = R.drawable.exercise_img
                ),
                Topic(
                    title = "Игра",
                    desc = "Как много слов вы знаете?",
                    percentages = 20,
                    id = 2,
                    background = R.drawable.first_them_bg,
                    image = R.drawable.vocabulary_img
                )
            )
        )
}