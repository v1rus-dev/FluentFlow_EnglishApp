package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yegor.cheprasov.fluentflow.decompose.words.learn.FakeLearnWordsComponent
import yegor.cheprasov.fluentflow.decompose.words.learn.LearnWordsComponent
import yegor.cheprasov.fluentflow.decompose.words.selectWordsForLearning.SelectWordsForLearningComponent
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.LearnWordsState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.state.SelectWordsForLearningState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsTranslateViewEntity
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LearnWordsScreen(component: LearnWordsComponent) {
    val uiState = component.uiState.subscribeAsState()

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { component.event(LearnWordsComponent.Event.CloseAll) }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                }
                Text(
                    text = "Изучаем",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState.value) {
                LearnWordsState.Initialize -> Column {}
                is LearnWordsState.Success -> {
                    HorizontalPager(
                        pageCount = (uiState.value as LearnWordsState.Success).words.size,
                        state = pagerState,
                        modifier = Modifier.padding(top = 26.dp),
                        userScrollEnabled = false,
                        pageSpacing = 16.dp
                    ) { pageIndex ->
                        Page(viewEntity = (uiState.value as LearnWordsState.Success).words[pageIndex]) {
                            if (pageIndex != (uiState.value as LearnWordsState.Success).words.lastIndex) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pageIndex + 1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Page(viewEntity: LearnWordsViewEntity, goToNext: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

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
                        .background(Color.White),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Выберите перевод",
                            fontSize = 12.sp,
                            color = Color(0xFFC5C6C7),
                            modifier = Modifier.padding(top = 14.dp)
                        )
                        Text(
                            text = viewEntity.word,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                viewEntity.translations.forEach {

                    val isCorrect = remember {
                        mutableStateOf<Boolean?>(null)
                    }

                    val checkWords: (LearnWordsTranslateViewEntity) -> Unit = remember {
                        {
                            isCorrect.value = it.isCorrect

                            if (it.isCorrect) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    delay(1500)
                                    goToNext()
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                when (isCorrect.value) {
                                    null -> {
                                        Color(0xFFFBFBFB)
                                    }

                                    false -> {
                                        Color(0xFFCE0000)
                                    }

                                    else -> {
                                        Color(0xFF00B733)
                                    }
                                }
                            )
                            .clickable {
                                if (isCorrect.value == null) {
                                    checkWords(it)
                                }
                            }
                            .padding(vertical = 26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = it.word.lowercase(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isCorrect.value != null) {
                                Color.White
                            } else {
                                Color.Black
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLearnWordsScreen() {
    LearnWordsScreen(component = FakeLearnWordsComponent())
}