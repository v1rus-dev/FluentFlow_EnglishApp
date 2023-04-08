package yegor.cheprasov.fluentflow.decompose.grammarThemes

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.GrammarUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity
import yegor.cheprasov.fluentflow.utils.reduceMain

class RealGrammarThemesComponent(
    componentContext: ComponentContext,
    private val _openDetails: (GrammarElementViewEntity) -> Unit,
    private val _onBack: () -> Unit
) : BaseComponent(componentContext), GrammarThemesComponent {

    private val grammarRepository: GrammarUseCase by inject()
    private val _uiState = MutableValue(GrammarState(grammars = listOf()))

    init {
        loadGrammars()
        observeGrammars()
    }

    private fun loadGrammars() {
        scope.launch(dispatcherIO) {
            grammarRepository.loadGrammars()
        }
    }

    private fun observeGrammars() {
        scope.launch(dispatcherIO) {
            grammarRepository.observeGrammars()
                .collectLatest { list ->
                    _uiState.reduceMain { it.copy(grammars = list) }
                }
        }
    }

    override fun onBack() {
        _onBack()
    }

    override fun event(event: GrammarThemesComponent.Event) {
        when(event) {
            GrammarThemesComponent.Event.Back -> _onBack()
            is GrammarThemesComponent.Event.ClickOnTheme -> {
                _openDetails(event.value)
            }
        }
    }

    override val uiState: Value<GrammarState> = _uiState
}