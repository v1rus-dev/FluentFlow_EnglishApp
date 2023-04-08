package yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details.utils.GrammarDetailType
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details.utils.OneBlockVE

class FakeGrammarDetailComponent : GrammarDetailsComponent {
    override val uiState: Value<GrammarUiStateDetail>
        get() = MutableValue(GrammarUiStateDetail.Success(
            _title = "Fake title",
            list = listOf(
                GrammarDetailType.TextViewEntity("This is the text block for preview"),
                GrammarDetailType.BlockViewEntity(
                    listOf(OneBlockVE("This is my preview", "Это мое превью."))
                )
            )
        ))

    override fun event(event: GrammarDetailsComponent.Event) = Unit
}