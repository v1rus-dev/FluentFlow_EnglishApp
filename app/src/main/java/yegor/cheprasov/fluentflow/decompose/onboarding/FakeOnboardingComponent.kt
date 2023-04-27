package yegor.cheprasov.fluentflow.decompose.onboarding

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class FakeOnboardingComponent : OnBoardingComponent {
    override val uiState: Value<SelectedState>
        get() = MutableValue(SelectedState())

    override fun event(event: OnBoardingComponent.Event) = Unit
}