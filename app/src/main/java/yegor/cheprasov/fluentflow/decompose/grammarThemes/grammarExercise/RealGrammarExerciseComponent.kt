package yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.GrammarUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails.GrammarDetailsMapper
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.state.GrammarExerciseUiState
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.state.SuccessState
import yegor.cheprasov.fluentflow.utils.reduceMain

class RealGrammarExerciseComponent(
    componentContext: ComponentContext,
    private val fileName: String,
    private val _onBack: () -> Unit
) : BaseComponent(componentContext), GrammarExerciseComponent {

    private val grammarUseCase: GrammarUseCase by inject()
    private val grammarMapper: GrammarDetailsMapper by inject()

    private var currentIndex = 0
    private var allCountOfExercises: Int = 0
    private val grammarExerciseViewEntities = arrayListOf<GrammarExerciseViewEntity>()

    private val _uiState = MutableValue<GrammarExerciseUiState>(GrammarExerciseUiState.Loading)
    override val uiState: Value<GrammarExerciseUiState> = _uiState

    private val mutableFinish = MutableValue(false)
    override val finish: Value<Boolean> = mutableFinish

    init {
        loadExercise()
    }

    override fun checkAnswer(vararg answer: String) {
        scope.launch(dispatcherIO) {
            if (uiState.value is GrammarExerciseUiState.Success) {
                val isSuccess =
                    answer.toList() == grammarExerciseViewEntities[currentIndex].correctWords
                _uiState.reduceMain {
                    (it as GrammarExerciseUiState.Success).copy(
                        successState = if (isSuccess) {
                            SuccessState.Success
                        } else {
                            SuccessState.Error(
                                text = grammarExerciseViewEntities[currentIndex].text,
                                myAnswer = answer.toList(),
                                correctAnswer = grammarExerciseViewEntities[currentIndex].correctWords
                            )
                        }
                    )
                }
                if (isSuccess) {
                    delay(2000)
                    continueExercise()
                }
            }
        }
    }

    override fun finish(isLast: Boolean) {
        if (isLast) {
            _onBack()
        } else {
            continueExercise()
        }
    }

    override fun close() {
        _onBack()
    }

    private fun loadExercise() = scope.launch {
        grammarUseCase.loadExerciseFromFile(fileName)
            .map(grammarMapper::mapGrammarExercise)
            .onEach {
                grammarExerciseViewEntities.addAll(it)
                allCountOfExercises = it.size
            }
            .collectLatest { list ->
                _uiState.reduceMain {
                    GrammarExerciseUiState.Success(
                        percentage = 0f,
                        successState = SuccessState.None,
                        isLast = false,
                        grammarExerciseViewEntity = list.first()
                    )
                }
            }
    }

    private fun continueExercise() = scope.launch(dispatcherIO) {
        if (currentIndex != allCountOfExercises) {
            currentIndex++
            _uiState.reduceMain {
                GrammarExerciseUiState.Success(
                    percentage = (currentIndex.toFloat() / allCountOfExercises),
                    successState = SuccessState.None,
                    isLast = currentIndex == grammarExerciseViewEntities.lastIndex,
                    grammarExerciseViewEntity = grammarExerciseViewEntities[currentIndex]
                )
            }
        } else {
            mutableFinish.reduceMain { true }
        }
    }

    override fun onBack() {
        _onBack()
    }
}