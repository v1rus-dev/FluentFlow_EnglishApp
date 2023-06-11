package nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.allThemes

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import nastya.cheprasova.fluentflow.R
import nastya.cheprasova.fluentflow.data.usecase.Level
import nastya.cheprasova.fluentflow.data.utils.map
import nastya.cheprasova.fluentflow.decompose.grammarThemes.FakeGrammarThemesComponent
import nastya.cheprasova.fluentflow.decompose.grammarThemes.GrammarThemesComponent
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.allThemes.components.GrammarElement
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GrammarThemesScreen(component: GrammarThemesComponent) {
    val uiState = component.uiState.subscribeAsState()

    Log.d("myTag", "uiState: ${uiState.value.map { it.grammars }}")

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val selectNewLevel: (Level) -> Unit = remember {
        {
            component.event(GrammarThemesComponent.Event.SelectNewLevel(it))
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        component.event(GrammarThemesComponent.Event.Back)
                    })
                Text(
                    text = "Грамматика",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            Box {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                    isExpanded = !isExpanded
                }) {
                    Text(
                        text = uiState.value.currentLevel.levelName,
                        fontSize = 16.sp,
                        color = Color.LightGray
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }
                DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                    Level.values().forEach {
                        DropdownMenuItem(onClick = {
                            selectNewLevel(it)
                            isExpanded = !isExpanded
                        }) {
                            Text(it.levelName, modifier = Modifier.padding(24.dp))
                        }
                    }
                }
            }
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
                            grammarId = item.grammarId,
                            examples = item.examples,
                            fileName = item.fileName,
                            exerciseFile = item.exerciseFile,
                            allExercises = item.allExercises,
                            endedExercises = item.endedExercises,
                            isFavorite = item.isFavorite
                        ),
                        modifier = Modifier.animateItemPlacement(),
                        makeFavorite = {
                            component.event(GrammarThemesComponent.Event.MakeFavorite(it))
                        }
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