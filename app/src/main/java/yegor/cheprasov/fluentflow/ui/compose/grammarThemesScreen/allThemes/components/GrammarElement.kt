package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.allThemes.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity

@Composable
fun GrammarElement(
    element: GrammarElementViewEntity,
    modifier: Modifier = Modifier,
    makeFavorite: (GrammarElementViewEntity) -> Unit,
    onClick: (GrammarElementViewEntity) -> Unit
) {
    GrammarMenuCard(
        element = element,
        modifier = modifier,
        makeFavorite = makeFavorite,
        onClick = onClick
    )
}

@Composable
@Preview
private fun PreviewGrammarElement() {
    GrammarElement(getGrammarElementState(), makeFavorite = {}) {}
}

private fun getGrammarElementState() = GrammarElementViewEntity(
    title = "Present Simple",
    subtitle = "Am, is, are",
    grammarId = 0,
    examples = listOf("This is", "It is"),
    allExercises = 3,
    endedExercises = 1,
    fileName = "",
    exerciseFile = "",
    isFavorite = false
)