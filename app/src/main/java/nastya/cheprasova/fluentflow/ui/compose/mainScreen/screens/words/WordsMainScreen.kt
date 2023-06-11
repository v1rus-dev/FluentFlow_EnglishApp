package nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nastya.cheprasova.fluentflow.R
import nastya.cheprasova.fluentflow.decompose.mainScreen.words.FakeWordsMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.words.WordsMainComponent
import nastya.cheprasova.fluentflow.ui.compose.components.NewWordsAndPhrasesButton
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.components.TopicListItem
import nastya.cheprasova.fluentflow.ui.theme.background

@Composable
fun WordsMainScreen(component: WordsMainComponent) {
    val coroutineScope = rememberCoroutineScope()
    val state = component.uiState.subscribeAsState()

    var isCanClick by remember {
        mutableStateOf(true)
    }

    val onClick: (WordsTopicViewEntity) -> Unit = remember {
        {
            if (isCanClick) {
                isCanClick = false
                component.openTopic(it)
                coroutineScope.launch(Dispatchers.IO) {
                    delay(500L)
                    isCanClick = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NewWordsAndPhrasesButton(
                state = state.value.newWordsAndPhrasesState,
                modifier = Modifier.padding(start = 16.dp, top = 22.dp),
                showNumbers = false,
                enable = false
            ) {}
            Image(
                painter = painterResource(id = R.drawable.hand_img),
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    this.rotationZ = -30f
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Мой прогресс по темам", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            LazyColumn(contentPadding = PaddingValues(vertical = 12.dp)) {
                itemsIndexed(state.value.list) { index, item ->
                    TopicListItem(item, onClick)
                    if (index != state.value.list.lastIndex) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWordsMainScreen() {
    WordsMainScreen(component = FakeWordsMainComponent())
}