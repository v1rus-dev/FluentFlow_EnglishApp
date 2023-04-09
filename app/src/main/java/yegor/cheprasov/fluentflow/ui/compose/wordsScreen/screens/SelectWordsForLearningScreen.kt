package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning.FakeSelectWordsForLearningComponent
import yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning.SelectWordsForLearningComponent
import yegor.cheprasov.fluentflow.ui.compose.components.Percentage
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity

@Composable
fun SelectWordsForLearningScreen(component: SelectWordsForLearningComponent) {
    val uiState = component.uiState.subscribeAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { component.onEvent(SelectWordsForLearningComponent.Event.Close) }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = null)
            }
            if (uiState.value is SelectWordsForLearningState.Select) {
                with(uiState.value as SelectWordsForLearningState.Select) {
                    Text(
                        text = "Вы выбрали $selectedCount/$maxCountForSelected",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState.value) {
                SelectWordsForLearningState.None -> Unit
                is SelectWordsForLearningState.Select -> {
                    SelectWords(uiState.value as SelectWordsForLearningState.Select)
                }

                is SelectWordsForLearningState.Check -> {
                    Check(state = uiState.value as SelectWordsForLearningState.Check)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectWords(state: SelectWordsForLearningState.Select) {
    val percentage = state.selectedCount / state.maxCountForSelected
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Percentage(
            percentage = percentage * 100,
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp)
        )
        HorizontalPager(
            pageCount = state.words.size,
            modifier = Modifier.padding(top = 26.dp),
            pageSpacing = 16.dp
        ) { page ->
            Page(state.words[page])
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Page(wordsForLearningViewEntity: WordsForLearningViewEntity) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .fillMaxHeight()
            .padding(top = 100.dp, bottom = 180.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = wordsForLearningViewEntity.word,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 38.dp)
                )
                Text(
                    text = wordsForLearningViewEntity.translate,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp),
                    color = Color.LightGray
                )
                Card(
                    onClick = {},
                    shape = CircleShape,
                    backgroundColor = Color(0xFFBD6EEB),
                    modifier = Modifier
                        .padding(top = 26.dp)
                        .size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(13.dp)
                    )
                }
            }
           Column {
                Card(
                    onClick = {},
                    backgroundColor = Color(0xFFE4FFEC),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 77.dp)
                        .height(42.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Я знаю",
                            color = Color(0xFF00B733),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Card(
                    onClick = {},
                    backgroundColor = Color(0xFFF9C950),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp, bottom = 32.dp)
                        .height(42.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Учить",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Check(state: SelectWordsForLearningState.Check) {

}

@Preview
@Composable
private fun PreviewSelectWordsForLearningScreen() {
    SelectWordsForLearningScreen(component = FakeSelectWordsForLearningComponent())
}