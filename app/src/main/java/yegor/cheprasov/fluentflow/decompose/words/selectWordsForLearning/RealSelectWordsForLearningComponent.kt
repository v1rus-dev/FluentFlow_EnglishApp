package yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity
import yegor.cheprasov.fluentflow.utils.reduceMain

class RealSelectWordsForLearningComponent(
    componentContext: ComponentContext,
    val topicId: Int,
    val onContinue: (List<WordsForLearningViewEntity>) -> Unit,
    val close: () -> Unit
) : BaseComponent(componentContext), SelectWordsForLearningComponent {

    private val wordsUseCase: WordsUseCase by inject()
    private val _uiState: MutableValue<SelectWordsForLearningState> =
        MutableValue(SelectWordsForLearningState.None)
    private val selectedList = arrayListOf<WordsForLearningViewEntity>()
    private val iKnowList = arrayListOf<WordsForLearningViewEntity>()

    init {
        getWordsForLearning()
    }

    private fun getWordsForLearning() = scope.launch {
        val list = wordsUseCase.getWordsForLearningByTopic(topicId)
        _uiState.reduceMain {
            SelectWordsForLearningState.Select(
                words = list.map {
                    WordsForLearningViewEntity(
                        word = it.word,
                        translate = it.translate
                    )
                },
                selectedCount = selectedList.size
            )
        }
    }

    override fun event(event: SelectWordsForLearningComponent.Event) {
        when (event) {
            SelectWordsForLearningComponent.Event.Close -> close()
            is SelectWordsForLearningComponent.Event.OnIKnow -> onIKnow(event.wordsForLearningViewEntity)
            is SelectWordsForLearningComponent.Event.OnLearn -> onLearn(event.wordsForLearningViewEntity)
            SelectWordsForLearningComponent.Event.OnContinue -> onContinue(selectedList)
        }
    }

    private fun onIKnow(wordsForLearningViewEntity: WordsForLearningViewEntity) {
        iKnowList.add(wordsForLearningViewEntity)
    }

    private fun onLearn(wordsForLearningViewEntity: WordsForLearningViewEntity) {
        selectedList.add(wordsForLearningViewEntity)
        if (selectedList.size < 4) {
            if (uiState.value is SelectWordsForLearningState.Select) {
                _uiState.update {
                    (it as SelectWordsForLearningState.Select).copy(selectedCount = selectedList.size)
                }
            }
        } else {
            _uiState.update {
                SelectWordsForLearningState.Check(selectedList)
            }
        }
    }


    override val uiState: Value<SelectWordsForLearningState>
        get() = _uiState

    override fun onBack() {
        close()
    }

}