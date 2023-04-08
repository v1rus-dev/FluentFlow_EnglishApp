package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.allThemes.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity

@Composable
fun GrammarElement(element: GrammarElementViewEntity, onClick: (GrammarElementViewEntity) -> Unit) {
    GrammarMenuCard(
        element = element,
        onClick = onClick
    )
}

@Composable
@Preview
private fun PreviewGrammarElement() {
    GrammarElement(getGrammarElementState()) {}
}

private fun getGrammarElementState() = GrammarElementViewEntity(
    title = "Present Simple",
    subtitle = "Am, is, are",
    examples = listOf("This is", "It is"),
    percentage = 35,
    fileName = "",
    exerciseFile = "",
    isFavorite = false
)