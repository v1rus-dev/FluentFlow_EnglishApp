package yegor.cheprasov.fluentflow.decompose.words.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.launch
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.words.learn.LearnWordsComponent
import yegor.cheprasov.fluentflow.decompose.words.learn.RealLearnWordsComponent
import yegor.cheprasov.fluentflow.decompose.words.information.RealWordsInformationComponent
import yegor.cheprasov.fluentflow.decompose.words.information.WordsInformationComponent
import yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning.RealSelectWordsForLearningComponent
import yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning.SelectWordsForLearningComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity

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

            is Config.SelectWordsForLearning -> WordsMainComponent.Child.SelectWordsForLearning(
                component = selectWordsForLearning(
                    componentContext,
                    config.id
                )
            )

            is Config.LearnWords -> WordsMainComponent.Child.LearnWords(
                component = checkSelectedComponent(componentContext, config.list)
            )
        }

    private fun information(
        componentContext: ComponentContext,
        topic: WordsTopicViewEntity
    ): WordsInformationComponent =
        RealWordsInformationComponent(componentContext, topic) { event ->
            when (event) {
                WordsInformationComponent.Event.Close -> _onClose()
                WordsInformationComponent.Event.LearnNewWords -> {
                    navigation.push(Config.SelectWordsForLearning(topic.topicId))
                }

                WordsInformationComponent.Event.RelearnWords -> Unit
            }
        }

    private fun selectWordsForLearning(
        componentContext: ComponentContext,
        topicId: Int
    ): SelectWordsForLearningComponent =
        RealSelectWordsForLearningComponent(
            componentContext = componentContext,
            topicId = topicId,
            onContinue = {
                navigation.push(Config.LearnWords(it))
            },
            close = {
                navigation.pop()
            })

    private fun checkSelectedComponent(
        componentContext: ComponentContext,
        list: List<WordsForLearningViewEntity>
    ): LearnWordsComponent =
        RealLearnWordsComponent(componentContext, list, topic.topicId) {
            scope.launch(dispatcherMain) {
                _onClose()
            }
        }

    private sealed interface Config : Parcelable {

        @Parcelize
        data class Information(val topic: WordsTopicViewEntity) : Config

        @Parcelize
        data class SelectWordsForLearning(val id: Int) : Config

        @Parcelize
        data class LearnWords(val list: List<WordsForLearningViewEntity>) : Config
    }
}