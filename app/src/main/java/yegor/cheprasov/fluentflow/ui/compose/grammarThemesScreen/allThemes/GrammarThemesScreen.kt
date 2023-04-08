package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.allThemes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.decompose.grammarThemes.FakeGrammarThemesComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.GrammarThemesComponent
import yegor.cheprasov.fluentflow.ui.compose.components.SecondToolbar
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.allThemes.components.GrammarElement
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity

@Composable
fun GrammarThemesScreen(component: GrammarThemesComponent) {
    val uiState = component.uiState.subscribeAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SecondToolbar(title = "Грамматика") {
            component.event(GrammarThemesComponent.Event.Back)
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)) {
                itemsIndexed(items = uiState.value.grammars) { index, item ->
                    GrammarElement(
                        element = GrammarElementViewEntity(
                            title = item.title,
                            subtitle = item.subtitle,
                            examples = item.examples,
                            fileName = item.fileName,
                            exerciseFile = item.exerciseFile,
                            percentage = 0,
                            isFavorite = false
                        )
                    ) {
                        component.event(GrammarThemesComponent.Event.ClickOnTheme(it))
                    }
                    if (index != uiState.value.grammars.lastIndex) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGrammarThemesScreen() {
    GrammarThemesScreen(component = FakeGrammarThemesComponent())
}