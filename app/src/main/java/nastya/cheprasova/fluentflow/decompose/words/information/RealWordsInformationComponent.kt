package nastya.cheprasova.fluentflow.decompose.words.information

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

class RealWordsInformationComponent(
    componentContext: ComponentContext,
    private val topic: WordsTopicViewEntity,
    private val _event: (WordsInformationComponent.Event) -> Unit
) : BaseComponent(componentContext), WordsInformationComponent {

    private val _uiState = MutableValue(topic)

    override val uiState: Value<WordsTopicViewEntity>
        get() = _uiState

    override fun event(event: WordsInformationComponent.Event) {
        when(event) {
            WordsInformationComponent.Event.Close -> _event(WordsInformationComponent.Event.Close)
            WordsInformationComponent.Event.LearnNewWords -> _event(WordsInformationComponent.Event.LearnNewWords)
            WordsInformationComponent.Event.RelearnWords -> _event(WordsInformationComponent.Event.RelearnWords)
        }
    }

    override fun onBack() {
        _event(WordsInformationComponent.Event.Close)
    }

}