package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity

sealed class GrammarDetailType {
    data class TextViewEntity(val text: String) : GrammarDetailType()

    data class BlockViewEntity(
        val list: List<OneBlockVE>
    ) : GrammarDetailType()
}

data class OneBlockVE(
    val text: String,
    val translate: String
)