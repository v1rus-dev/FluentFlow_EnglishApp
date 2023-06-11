package nastya.cheprasova.fluentflow.decompose.words.information

import android.net.Uri
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

class FakeWordsInformationComponent : WordsInformationComponent {
    override val uiState: Value<WordsTopicViewEntity>
        get() = MutableValue(
            WordsTopicViewEntity(
                title = "Путешествия",
                allCount = 22,
                endedCount = 17,
                imagePath = Uri.EMPTY,
                fileName = "",
                topicId = 0
            )
        )

    override fun event(event: WordsInformationComponent.Event) = Unit
}