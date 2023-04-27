package yegor.cheprasov.fluentflow.decompose.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import org.koin.core.component.inject
import yegor.cheprasov.fluentflow.data.usecase.Level
import yegor.cheprasov.fluentflow.data.usecase.LevelUseCase
import yegor.cheprasov.fluentflow.decompose.BaseComponent

class RealOnboardingComponent(
    componentContext: ComponentContext,
    private val _continue: () -> Unit
) : BaseComponent(componentContext), OnBoardingComponent {

    private val levelUseCase: LevelUseCase by inject()

    private val mutableUiState = MutableValue(SelectedState())

    private var selectedLevel: Int? = null
    override val uiState: Value<SelectedState>
        get() = mutableUiState

    override fun event(event: OnBoardingComponent.Event) {
        when(event) {
            OnBoardingComponent.Event.SelectAdvanced -> {
                selectedLevel = Level.Advanced.id
                updateState()
            }
            OnBoardingComponent.Event.SelectElementary -> {
                selectedLevel = Level.Elementary.id
                updateState()
            }
            OnBoardingComponent.Event.SelectMiddle -> {
                selectedLevel = Level.Middle.id
                updateState()
            }
            OnBoardingComponent.Event.Continue -> onContinue()
        }
    }

    private fun updateState() {
        mutableUiState.update {
            it.copy(selectedLevel = selectedLevel)
        }
    }

    private fun onContinue() {
        levelUseCase.setLevel(Level.getLevelById(selectedLevel!!))
        _continue()
    }

}