package nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import nastya.cheprasova.fluentflow.data.usecase.GrammarUseCase
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity
import nastya.cheprasova.fluentflow.utils.reduceMain

class RealGrammarDetailComponent(
    componentContext: ComponentContext,
    private val element: GrammarElementViewEntity,
    private val _openExercise: (Int) -> Unit,
    private val _onBack: () -> Unit
) : BaseComponent(componentContext), GrammarDetailsComponent {

    private val grammarUseCase: GrammarUseCase by inject()
    private val grammarDetailsMapper: GrammarDetailsMapper by inject()
    private val _uiState =
        MutableValue<GrammarUiStateDetail>(GrammarUiStateDetail.Loading(element.title))

    init {
        loadGrammarFromFile()
    }

    override val uiState: Value<GrammarUiStateDetail> = _uiState

    override fun event(event: GrammarDetailsComponent.Event) {
        when (event) {
            GrammarDetailsComponent.Event.OnBack -> _onBack()
            GrammarDetailsComponent.Event.OpenExercise -> _openExercise(element.grammarId)
        }
    }

    private fun loadGrammarFromFile() = scope.launch {
        grammarUseCase.loadGrammarFromFile(element.fileName)
            .map { grammarDetailsMapper.mapGrammarDetail(it, element.title) }
            .collectLatest { newState ->
                _uiState.reduceMain { newState }
            }
    }

    override fun onBack() {
        _onBack()
    }

}