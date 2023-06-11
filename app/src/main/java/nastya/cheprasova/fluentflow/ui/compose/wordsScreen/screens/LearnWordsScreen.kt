package nastya.cheprasova.fluentflow.ui.compose.wordsScreen.screens

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import nastya.cheprasova.fluentflow.decompose.dialog.DialogState
import nastya.cheprasova.fluentflow.decompose.words.learn.FakeLearnWordsComponent
import nastya.cheprasova.fluentflow.decompose.words.learn.LearnWordsComponent
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.state.LearnWordsState
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsTranslateViewEntity
import nastya.cheprasova.fluentflow.ui.compose.wordsScreen.viewEntity.LearnWordsViewEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LearnWordsScreen(component: LearnWordsComponent) {
    val uiState = component.uiState.subscribeAsState()

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val dialogSlot by component.errorDialog.subscribeAsState()
    var currentPage: Int by remember {
        mutableStateOf(0)
    }

    val onDismissDialog: () -> Unit = remember {
        {
            Log.d("myTag", "dismissDialog")
            if (currentPage != (uiState.value as LearnWordsState.Success).words.lastIndex) {
                coroutineScope.launch {
                    currentPage++
                    pagerState.animateScrollToPage(currentPage)
                }
            } else {
                component.event(LearnWordsComponent.Event.OnFinish)
            }
        }
    }

    dialogSlot.child?.instance?.also {
        val state by it.state.subscribeAsState()
        with(state as DialogState.ErrorDialogState) {
            LearnErrorDialog(word = word, correctAnswer = correctWord, chooseAnswer = chooseWord) {
                it.onDismissClicked()
                onDismissDialog()
            }
        }
    }

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
                        Page(
                            viewEntity = (uiState.value as LearnWordsState.Success).words[pageIndex],
                            notCorrect = { word, selectWords, correctWord ->
                                component.event(
                                    LearnWordsComponent.Event.ShowErrorDialog(
                                        word,
                                        correctWord,
                                        selectWords
                                    )
                                )
                            }
                        ) {
                            component.event(LearnWordsComponent.Event.Learned(it))
                            if (pageIndex != (uiState.value as LearnWordsState.Success).words.lastIndex) {
                                currentPage++
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pageIndex + 1)
                                }
                            } else {
                                component.event(LearnWordsComponent.Event.OnFinish)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Page(
    viewEntity: LearnWordsViewEntity,
    notCorrect: (String, String, String) -> Unit,
    goToNext: (LearnWordsViewEntity) -> Unit
) {

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

                PagePart(
                    list = viewEntity.translations,
                    notCorrect = { selectedWord: String, correctWord: String ->
                        notCorrect(viewEntity.word, selectedWord, correctWord)
                    }) {
                    goToNext(viewEntity)
                }
            }
        }
    }
}

@Composable
fun PagePart(
    list: List<LearnWordsTranslateViewEntity>,
    notCorrect: (String, String) -> Unit,
    goToNext: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    var isClicked by remember {
        mutableStateOf(false)
    }

    var isCorrectClick by remember {
        mutableStateOf(false)
    }

    var isClickedWord by remember {
        mutableStateOf("")
    }

    val isClickedCorrect = remember {
        {
            coroutineScope.launch(Dispatchers.IO) {
                delay(1500)
                goToNext()
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        list.forEach { viewEntity ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isClicked && isClickedWord == viewEntity.word) {
                            if (isCorrectClick) {
                                Color(0xFF00B733)

                            } else {
                                Color(0xFFCE0000)

                            }
                        } else {
                            Color(0xFFFBFBFB)
                        }
                    )
                    .clickable(enabled = !isClicked) {
                        isClicked = true
                        isClickedWord = viewEntity.word
                        isCorrectClick = viewEntity.isCorrect
                        if (viewEntity.isCorrect) {
                            isClickedCorrect()
                        } else {
                            notCorrect(
                                viewEntity.word,
                                list.firstOrNull { it.isCorrect }?.word ?: ""
                            )
                        }
                    }
                    .padding(vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = viewEntity.word.lowercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLearnWordsScreen() {
    LearnWordsScreen(component = FakeLearnWordsComponent())
}