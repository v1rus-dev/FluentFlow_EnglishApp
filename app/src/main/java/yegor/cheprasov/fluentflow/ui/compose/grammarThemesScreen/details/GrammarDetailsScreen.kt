package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails.FakeGrammarDetailComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails.GrammarDetailsComponent
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails.GrammarUiStateDetail
import yegor.cheprasov.fluentflow.ui.compose.components.LoadingScreen
import yegor.cheprasov.fluentflow.ui.compose.components.SecondToolbar
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details.utils.GrammarDetailType
import yegor.cheprasov.fluentflow.ui.theme.background

@Composable
fun GrammarDetailsScreen(component: GrammarDetailsComponent) {

    val state = component.uiState.subscribeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SecondToolbar(
                title = state.value.title, onBack = {
                    component.event(GrammarDetailsComponent.Event.OnBack)
                })
        },
        backgroundColor = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (val st = state.value) {
                is GrammarUiStateDetail.Loading -> {
                    LoadingScreen()
                }

                is GrammarUiStateDetail.Success -> {
                    SuccessScreen(st.list) {
                        component.event(GrammarDetailsComponent.Event.OpenExercise)
                    }
                }
            }
        }
    }
}

@Composable
private fun SuccessScreen(list: List<GrammarDetailType>, onPractice: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SuccessLazyColumnPart(list = list)
        PracticeBtn(modifier = Modifier.padding(16.dp)) {
            onPractice.invoke()
        }
    }
}

@Composable
private fun SuccessLazyColumnPart(list: List<GrammarDetailType>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 84.dp
        )
    ) {
        itemsIndexed(list) { index, item ->
            when (val type = item) {
                is GrammarDetailType.BlockViewEntity -> GrammarDetailBlockComponent(
                    grammarDetailType = type
                )

                is GrammarDetailType.TextViewEntity -> GrammarDetailTextComponent(
                    grammarDetailType = type
                )
            }
            if (index != list.lastIndex) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGrammarDetailsScreen() {
    GrammarDetailsScreen(component = FakeGrammarDetailComponent())
}