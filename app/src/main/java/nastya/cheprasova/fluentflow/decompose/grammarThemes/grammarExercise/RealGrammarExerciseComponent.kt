package nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarExercise

import android.net.Uri
import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import nastya.cheprasova.fluentflow.data.usecase.GrammarUseCase
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails.GrammarDetailsMapper
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarExercise.state.GrammarExerciseUiState
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarExercise.state.SuccessState
import nastya.cheprasova.fluentflow.utils.reduceMain

class RealGrammarExerciseComponent(
    componentContext: ComponentContext,
    private val grammarId: Int,
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
        getExercises()
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
                    val gr = grammarExerciseViewEntities[currentIndex]
                    grammarUseCase.setExerciseLikeEnded(gr.id, grammarId)
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

    private fun getExercises() = scope.launch {
        val list = grammarUseCase.getExercisesByGrammarId(grammarId)
            .filter { !it.isEnded }
            .map {
                GrammarExerciseViewEntity(
                    translate = it.translate,
                    image = Uri.parse(it.imagePath),
                    text = it.text,
                    words = it.words,
                    correctWords = it.correctWords,
                    correctPhrase = it.correctPhrase,
                    id = it._id
                )
            }
        if (list.isEmpty()) {
            launch(Dispatchers.Main) {
                _onBack()
            }
            return@launch
        }
        grammarExerciseViewEntities.addAll(list)
        allCountOfExercises = list.size
        _uiState.update {
            GrammarExerciseUiState.Success(
                percentage = 0f,
                successState = SuccessState.None,
                isLast = false,
                grammarExerciseViewEntity = grammarExerciseViewEntities.first()
            )
        }
    }

    private fun continueExercise() = scope.launch(dispatcherIO) {
        Log.d("myTag", "currentIndex: $currentIndex")
        Log.d("myTag", "allCountOfExercise: $allCountOfExercises")
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