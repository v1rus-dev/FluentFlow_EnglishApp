package nastya.cheprasova.fluentflow.decompose.exercise

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import nastya.cheprasova.fluentflow.data.room.entities.ExerciseEntity
import nastya.cheprasova.fluentflow.data.usecase.MainExerciseUseCase
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.decompose.dialog.DialogComponent
import nastya.cheprasova.fluentflow.decompose.dialog.RealErrorLearningWordsDialogComponent
import nastya.cheprasova.fluentflow.decompose.dialog.SuccessDialog
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.ExerciseSelectState
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.SelectWordViewEntity
import nastya.cheprasova.fluentflow.ui.compose.exerciseScreen.state.ExerciseState

class RealExerciseComponent(
    componentContext: ComponentContext,
    private val _onClose: () -> Unit
) : BaseComponent(componentContext), ExerciseComponent {

    private val exerciseUseCase: MainExerciseUseCase by inject()
    private val list = arrayListOf<ExerciseEntity>()
    private var currentIndex: Int = 0
    private var currentItem: ExerciseEntity? = null

    private val dialogNavigation = SlotNavigation<DialogConfig>()

    private val _dialog = childSlot(
        source = dialogNavigation,
        handleBackButton = false
    ) { configuration: DialogConfig, componentContext: ComponentContext ->
        when (configuration) {
            is DialogConfig.Error -> RealErrorLearningWordsDialogComponent(
                componentContext,
                word = configuration.sentence,
                correctWords = configuration.correctSentence,
                chooseWord = configuration.chooseSentence,
                onDismissed = {
                    dialogNavigation.dismiss()
                }
            ) as DialogComponent

            is DialogConfig.Success ->
                SuccessDialog(
                    componentContext,
                    configuration.sentense,
                    onDismissed = {
                        dialogNavigation.dismiss()
                    }
                ) as DialogComponent
        }
    }

    private val _uiState: MutableValue<ExerciseState> = MutableValue(ExerciseState.Loading)

    override val uiState: Value<ExerciseState> = _uiState

    override val dialogState: Value<ChildSlot<*, DialogComponent>> = _dialog

    init {
        observeExercise()
        load()
    }

    override fun event(event: ExerciseComponent.Event) {
        when (event) {
            ExerciseComponent.Event.Check -> {
                if (uiState.value is ExerciseState.CurrentExercise) {
                    with(uiState.value as ExerciseState.CurrentExercise) {
                        check(list = exercise.selectWords.map { it.text }, sentence = sentense)
                    }
                }
            }
            ExerciseComponent.Event.GoToNext -> goToNext()
            ExerciseComponent.Event.Close -> _onClose()
            is ExerciseComponent.Event.SelectWord -> selectWord(event.wordViewEntity)
            is ExerciseComponent.Event.RemoveWord -> removeWord(event.wordViewEntity)
        }
    }

    private fun load() = scope.launch {
        exerciseUseCase.load()
    }

    private fun selectWord(wordViewEntity: SelectWordViewEntity) {
        _uiState.update { state ->
            if (state is ExerciseState.CurrentExercise) {
                val selectedWords = arrayListOf<SelectWordViewEntity>().also {
                    it.addAll(state.exercise.selectWords)
                }
                selectedWords.add(wordViewEntity)
                state.copy(
                    exercise = state.exercise.copy(
                        selectWords = selectedWords,
                        allWords = state.exercise.allWords.map {
                            it.copy(isSelected = selectedWords.map { it.text }.contains(it.text))
                        })
                )
            } else {
                state
            }
        }
    }

//    private fun<T: Any> MutableValue<T>.compareTypeAndUpdate(expected: KClass<T>, newValue: (T) -> T) {
//        if (this.value::class == expected) {
//            update {
//                newValue(it)
//            }
//        }
//    }

    private fun removeWord(word: SelectWordViewEntity) {
        _uiState.update { state ->
            if (state is ExerciseState.CurrentExercise) {
                val newSelectedWords = arrayListOf<SelectWordViewEntity>().also {
                    it.addAll(state.exercise.selectWords)
                    it.remove(word)
                }
                state.copy(exercise = state.exercise.copy(
                    selectWords = newSelectedWords,
                    allWords = state.exercise.allWords.map {
                        it.copy(isSelected = newSelectedWords.map { it.text }.contains(it.text))
                    }
                ))
            } else {
                state
            }
        }
    }

    private fun check(list: List<String>, sentence: String) {
        var result = true

        if (currentItem == null) {
            return
        }

        if (list.size != currentItem!!.correctWords.size) {
            result = false
        } else {
            currentItem!!.correctWords.forEachIndexed { index, s ->
                if (result && list[index] != s) {
                    result = false
                }
            }
        }

        if (result) {
            dialogNavigation.activate(DialogConfig.Success(sentence))
        } else {
            dialogNavigation.activate(DialogConfig.Error(
                sentence,
                correctSentence = currentItem!!.correctWords.toText(),
                chooseSentence = list.toText()
            ))
        }
    }

    private fun List<String>.toText(): String {
        var result = ""
        forEachIndexed { index, s ->
            if (index != lastIndex) {
                result += "$s "
            } else {
                result + s
            }
        }
        return result
    }

    private fun observeExercise() = scope.launch {
        exerciseUseCase.observeExercise()
            .first {
                list.clear()
                list.addAll(it)
                if (list.isNotEmpty()) {
                    start()
                }
                true
            }
    }

    private fun start() {
        currentItem = list.first()
        currentIndex = 0
        currentItem?.let { entity ->
            _uiState.update {
                ExerciseState.CurrentExercise(
                    sentense = entity.sentense,
                    correctWords = entity.correctWords,
                    exercise = ExerciseSelectState(
                        selectWords = listOf(),
                        allWords = entity.words.map {
                            SelectWordViewEntity(it, false)
                        }
                    )
                )
            }
        }
    }

    private fun goToNext() {
        if (currentIndex != list.lastIndex) {
            currentIndex++
            currentItem = list[currentIndex]
        } else {

        }
    }

    @Parcelize
    private sealed class DialogConfig : Parcelable {
        data class Success(val sentense: String) : DialogConfig()

        data class Error(
            val sentence: String,
            val correctSentence: String,
            val chooseSentence: String
        ) : DialogConfig()
    }
}