package yegor.cheprasov.fluentflow.decompose.words.information

import com.arkivanov.decompose.ComponentContext
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

class RealWordsInformationComponent(
    componentContext: ComponentContext,
    private val topic: WordsTopicViewEntity,
    private val _onClose: () -> Unit
) : BaseComponent(componentContext), WordsInformationComponent {

    override fun onBack() {
        _onClose()
    }

}