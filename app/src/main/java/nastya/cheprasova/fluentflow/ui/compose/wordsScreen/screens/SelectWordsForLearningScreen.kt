package nastya.cheprasova.fluentflow.ui.compose.wordsScreen.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.launch
import nastya.cheprasova.fluentflow.decompose.words.selectWordsForLearning.FakeSelectWordsForLearningComponent
import nastya.cheprasova.fluentflow.decompose.words.selectWordsForLearning.SelectWordsForLearningComponent
import nastya.cheprasova.fluentflow.ui.compose.components.AppButton
import nastya.cheprasova.fluentflow.ui.compose.components.Percentage
import nastya.cheprasova.fluentflow.ui.compose.components.SoundButton
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity.WordsForLearningViewEntity
import nastya.cheprasova.fluentflow.ui.theme.background

@Composable
fun SelectWordsForLearningScreen(component: SelectWordsForLearningComponent) {
    val uiState = component.uiState.subscribeAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { component.event(SelectWordsForLearningComponent.Event.Close) }) {
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
            } else if (uiState.value is SelectWordsForLearningState.Check) {
                Text(
                    text = "Изучаем",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
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
                    SelectWords(
                        uiState.value as SelectWordsForLearningState.Select,
                        onIKnow = {
                            component.event(SelectWordsForLearningComponent.Event.OnIKnow(it))
                        },
                        onLearn = {
                            component.event(SelectWordsForLearningComponent.Event.OnLearn(it))
                        }
                    )
                }

                is SelectWordsForLearningState.Check -> {
                    Check(state = uiState.value as SelectWordsForLearningState.Check) {
                        component.event(SelectWordsForLearningComponent.Event.OnContinue)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectWords(
    state: SelectWordsForLearningState.Select,
    onIKnow: (WordsForLearningViewEntity) -> Unit,
    onLearn: (WordsForLearningViewEntity) -> Unit
) {
    val percentage = state.selectedCount / state.maxCountForSelected
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Percentage(
            percentage = percentage * 100f,
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp)
        )
        HorizontalPager(
            pageCount = state.words.size,
            state = pagerState,
            modifier = Modifier.padding(top = 26.dp),
            userScrollEnabled = false,
            pageSpacing = 16.dp
        ) { pageIndex ->
            Page(
                viewEntity = state.words[pageIndex],
                onIKnow = {
                    onIKnow(it)
                    if (pageIndex != state.words.lastIndex) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pageIndex + 1)
                        }
                    }
                },
                onLearn = {
                    onLearn(it)
                    if (pageIndex != state.words.lastIndex) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pageIndex + 1)
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Page(
    viewEntity: WordsForLearningViewEntity,
    onIKnow: (WordsForLearningViewEntity) -> Unit,
    onLearn: (WordsForLearningViewEntity) -> Unit
) {
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
                    text = viewEntity.word,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 38.dp)
                )
                Text(
                    text = viewEntity.translate,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp),
                    color = Color.LightGray
                )
                SoundButton(word = viewEntity.word)
            }
            Column {
                Card(
                    onClick = {
                        onIKnow(viewEntity)
                    },
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
                    onClick = {
                        onLearn(viewEntity)
                    },
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
fun Check(state: SelectWordsForLearningState.Check, onContinue: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Внимательно изучите",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )
                    }
                    state.words.forEach {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFBFBFB))
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = it.word, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = it.translate, fontSize = 12.sp, color = Color(0xFFC5C6C7))
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppButton(
                text = "Продолжить",
                modifier = Modifier
                    .padding(bottom = 22.dp)
                    .padding(horizontal = 64.dp)
            ) {
                onContinue()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSelectWordsForLearningScreen() {
    SelectWordsForLearningScreen(component = FakeSelectWordsForLearningComponent())
}