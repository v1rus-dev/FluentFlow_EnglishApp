package yegor.cheprasov.fluentflow.decompose.mainScreen.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.ProfileState
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.WordsInfo

class RealProfileMainComponent(
    componentContext: ComponentContext
) : BaseComponent(componentContext), ProfileMainComponent {

    private val wordsUseCase: WordsUseCase by inject()

    init {
        observeWords()
    }

    private val _uiState = MutableValue(
        getInitState()
    )
    override val state: Value<ProfileState>
        get() = _uiState

    override fun event(event: ProfileMainComponent.Event) {
        when(event) {
            is ProfileMainComponent.Event.ChangeName -> changeName(event.newName)
        }
    }

    private fun changeName(newName: String) {

    }

    private fun getInitState(): ProfileState =
        ProfileState(
            name = "",
            imgPath = "",
            wordsInfoList = listOf()
        )

    private fun observeWords() = scope.launch(Dispatchers.IO) {
        wordsUseCase.observeTopics()
            .collectLatest { list ->
                _uiState.update {
                    it.copy(
                        wordsInfoList = list.map {
                            WordsInfo(
                                name = it.title,
                                count = 0,
                                img = it.imagePath
                            )
                        }
                    )
                }
            }
    }
}