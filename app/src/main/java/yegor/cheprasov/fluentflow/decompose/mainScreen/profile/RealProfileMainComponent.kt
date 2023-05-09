package yegor.cheprasov.fluentflow.decompose.mainScreen.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.GrammarUseCase
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.InfoCardElement
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.ProfileState
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.WordsInfo

class RealProfileMainComponent(
    componentContext: ComponentContext
) : BaseComponent(componentContext), ProfileMainComponent {

    private val wordsUseCase: WordsUseCase by inject()
    private val grammarUseCase: GrammarUseCase by inject()

    init {
        observeWords()
        observeGrammarExercises()
    }

    private val _uiState = MutableValue(
        getInitState()
    )
    override val state: Value<ProfileState>
        get() = _uiState

    override fun event(event: ProfileMainComponent.Event) {
        when (event) {
            is ProfileMainComponent.Event.ChangeName -> changeName(event.newName)
        }
    }

    private fun changeName(newName: String) {

    }

    private fun getInitState(): ProfileState =
        ProfileState(
            name = "",
            imgPath = "",
            wordsInfoList = listOf(),
            grammarExerciseInfoList = listOf()
        )

    private fun observeWords() = scope.launch(Dispatchers.IO) {
        wordsUseCase.observeTopics()
            .map {
                it.map {
                    val words = wordsUseCase.getAllWordsForByTopic(it.topicId)
                    WordsTopicWrapper(
                        title = it.title,
                        allCount = words.size,
                        ended = words.filter { it.isLearned }.size,
                        imgPath = it.imagePath
                    )
                }
            }
            .collectLatest { list ->
                _uiState.update { state ->
                    state.copy(
                        wordsInfoList = list.map {
                            WordsInfo(
                                name = it.title,
                                count = it.allCount,
                                ended = it.ended,
                                img = it.imgPath
                            )
                        }
                    )
                }
            }
    }

    private fun observeGrammarExercises() = scope.launch {
        grammarUseCase.observeGrammars()
            .map {
                it.map { entity ->
                    InfoCardElement(
                        name = entity.title,
                        percentage = if (entity.endedExercises == 0 || entity.allExercises == 0) {
                            0
                        } else {
                            ((entity.endedExercises / entity.allExercises) * 100f).toInt()
                        }
                    )
                }
            }
            .collect { list ->
                _uiState.update { it.copy(grammarExerciseInfoList = list) }
            }
    }

    data class WordsTopicWrapper(
        val title: String,
        val allCount: Int,
        val ended: Int,
        val imgPath: String
    )
}