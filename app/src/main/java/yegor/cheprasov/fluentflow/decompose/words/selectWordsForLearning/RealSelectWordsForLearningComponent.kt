package yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent

class RealSelectWordsForLearningComponent(
    componentContext: ComponentContext,
    val topicId: Int
) : BaseComponent(componentContext), SelectWordsForLearningComponent {

    private val wordsUseCase: WordsUseCase by inject()

    init {
        getWordsForLearning()
    }

    private fun getWordsForLearning() = scope.launch {
        val list = wordsUseCase.getWordsForLearningByTopic(topicId)
    }

}