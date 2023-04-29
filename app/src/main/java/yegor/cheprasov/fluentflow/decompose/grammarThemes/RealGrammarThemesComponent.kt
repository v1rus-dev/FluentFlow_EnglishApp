package yegor.cheprasov.fluentflow.decompose.grammarThemes

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.GrammarUseCase
import yegor.cheprasov.fluentflow.data.usecase.Level
import yegor.cheprasov.fluentflow.data.usecase.LevelUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity
import yegor.cheprasov.fluentflow.utils.reduceMain

class RealGrammarThemesComponent(
    componentContext: ComponentContext,
    private val _openDetails: (GrammarElementViewEntity) -> Unit,
    private val _onBack: () -> Unit
) : BaseComponent(componentContext), GrammarThemesComponent {

    private val newGetting = MutableStateFlow(0)

    private val grammarRepository: GrammarUseCase by inject()
    private val levelUseCase: LevelUseCase by inject()
    private val _uiState = MutableValue(
        GrammarState(
            currentLevel = levelUseCase.getCurrentLevel(),
            grammars = listOf()
        )
    )

    init {
        loadGrammars()
        observeGrammars()
    }

    override fun event(event: GrammarThemesComponent.Event) {
        when (event) {
            GrammarThemesComponent.Event.Back -> _onBack()
            is GrammarThemesComponent.Event.ClickOnTheme -> {
                _openDetails(event.value)
            }

            is GrammarThemesComponent.Event.SelectNewLevel -> selectNewLevel(event.level)
            is GrammarThemesComponent.Event.MakeFavorite -> {

            }
        }
    }

    override val uiState: Value<GrammarState> = _uiState

    private fun loadGrammars() {
        scope.launch(dispatcherIO) {
            grammarRepository.loadGrammars()
        }
    }

    private fun observeGrammars() {
        scope.launch(dispatcherIO) {
            grammarRepository.observeGrammars()
                .combine(newGetting, transform = { list, _ ->
                    list
                })
                .map {
                    it.filter { it.level == levelUseCase.getCurrentLevel().id }
                        .sortedBy { it.isFavorite }
                }
                .collect { list ->
                    _uiState.reduceMain { it.copy(grammars = list) }
                }
        }
    }

    private fun selectNewLevel(level: Level) {
        levelUseCase.setLevel(level)
        _uiState.update { it.copy(currentLevel = level) }
        scope.launch {
            newGetting.emit(newGetting.value + 1)
        }
    }

    override fun onBack() {
        _onBack()
    }
}