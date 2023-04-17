package yegor.cheprasov.fluentflow.decompose.exercise

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.room.entities.ExerciseEntity
import yegor.cheprasov.fluentflow.data.usecase.MainExerciseUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.dialog.DialogComponent
import yegor.cheprasov.fluentflow.decompose.dialog.RealErrorLearningWordsDialogComponent
import yegor.cheprasov.fluentflow.decompose.dialog.SuccessDialog
import yegor.cheprasov.fluentflow.ui.compose.exerciseScreen.state.ExerciseState

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

    override val uiState: Value<ExerciseState> = MutableValue(ExerciseState.Loading)

    override val dialogState: Value<ChildSlot<*, DialogComponent>> = _dialog

    init {
        observeExercise()
        load()
    }

    override fun event(event: ExerciseComponent.Event) {
        when (event) {
            is ExerciseComponent.Event.Check -> check(event.list)
            ExerciseComponent.Event.GoToNext -> goToNext()
            ExerciseComponent.Event.Close -> _onClose()
        }
    }

    private fun load() = scope.launch {
        exerciseUseCase.load()
    }

    private fun check(list: List<String>) {
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
    }

    private fun observeExercise() = scope.launch {
        exerciseUseCase.observeExercise()
            .first {
                list.clear()
                list.addAll(it)
                start()
                true
            }
    }

    private fun start() {
        currentItem = list.first()
        currentIndex = 0
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