package yegor.cheprasov.fluentflow.decompose.words.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.words.RealWordsInformationComponent
import yegor.cheprasov.fluentflow.decompose.words.WordsInformationComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

class RealWordsMainComponent(
    componentContext: ComponentContext,
    private val topic: WordsTopicViewEntity,
    private val _onClose: () -> Unit
) : BaseComponent(componentContext), WordsMainComponent {

    private val navigation = StackNavigation<Config>()

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.Information(topic),
        handleBackButton = false,
        childFactory = ::createChild
    )

    override fun onBack() {
        _onClose()
    }

    override val childStack: Value<ChildStack<*, WordsMainComponent.Child>>
        get() = _childStack

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): WordsMainComponent.Child =
        when (config) {
            is Config.Information -> WordsMainComponent.Child.Information(
                component = information(
                    componentContext,
                    config.topic
                )
            )
        }

    private fun information(
        componentContext: ComponentContext,
        topic: WordsTopicViewEntity
    ): WordsInformationComponent =
        RealWordsInformationComponent(componentContext, topic, _onClose)

    private sealed interface Config : Parcelable {

        @Parcelize
        data class Information(val topic: WordsTopicViewEntity) : Config

    }
}