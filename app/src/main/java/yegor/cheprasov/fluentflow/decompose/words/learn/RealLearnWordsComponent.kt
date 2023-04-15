package yegor.cheprasov.fluentflow.decompose.words.learn

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
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.data.utils.RandomWords
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.dialog.DialogComponent
import yegor.cheprasov.fluentflow.decompose.dialog.RealErrorLearningWordsDialogComponent
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.LearnWordsState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsTranslateViewEntity
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity

class RealLearnWordsComponent(
    componentContext: ComponentContext,
    private val listForLearning: List<WordsForLearningViewEntity>,
    private val topicId: Int,
    private val close: () -> Unit
) : BaseComponent(componentContext), LearnWordsComponent {

    private val wordsUse: WordsUseCase by inject()

    private val randomWords: RandomWords by inject()
    private val shuffledList = arrayListOf<String>()

    private val _uiState = MutableValue<LearnWordsState>(LearnWordsState.Initialize)

    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val uiState: Value<LearnWordsState> = _uiState

    private val _dialog = childSlot(
        source = dialogNavigation,
        handleBackButton = false
    ) { configuration: DialogConfig, componentContext: ComponentContext ->
        RealErrorLearningWordsDialogComponent(
            componentContext,
            word = configuration.word,
            correctWords = configuration.correctWord,
            chooseWord = configuration.chooseWord,
            onDismissed = dialogNavigation::dismiss
        )
    }
    override val errorDialog: Value<ChildSlot<*, DialogComponent>> = _dialog

    init {
        loadWords()
    }

    override fun event(event: LearnWordsComponent.Event) {
        when (event) {
            LearnWordsComponent.Event.CloseAll -> close()
            LearnWordsComponent.Event.OnFinish -> close()

            is LearnWordsComponent.Event.ShowErrorDialog -> {
                dialogNavigation.activate(
                    DialogConfig(
                        event.word,
                        event.correctWord,
                        event.chooseWord
                    )
                )
            }

            is LearnWordsComponent.Event.Learned -> {
                learned(event.word)
            }
        }
    }

    private fun loadWords() = scope.launch {
        val words = randomWords.getWords().words.shuffled()
        shuffledList.clear()
        shuffledList.addAll(words)
        _uiState.update { getState() }
    }

    private fun learned(word: LearnWordsViewEntity) {
        scope.launch {
            wordsUse.saveWordAsLearned(word, topicId)
        }
    }

    private fun getState(): LearnWordsState = LearnWordsState.Success(
        words = listForLearning.map {
            LearnWordsViewEntity(
                word = it.word,
                translations = getTranslationList(it.translate)
            )
        }
    )

    private fun getTranslationList(currentWords: String): List<LearnWordsTranslateViewEntity> {
        val newShuffledList = shuffledList.shuffled().take(3).shuffled()
            .map { LearnWordsTranslateViewEntity(word = it, isCorrect = false) }
        val result = arrayListOf<LearnWordsTranslateViewEntity>().apply {
            addAll(newShuffledList)
            add(LearnWordsTranslateViewEntity(word = currentWords, isCorrect = true))
        }
        return result.shuffled()
    }

    @Parcelize
    private data class DialogConfig(
        val word: String,
        val correctWord: String,
        val chooseWord: String
    ) : Parcelable
}