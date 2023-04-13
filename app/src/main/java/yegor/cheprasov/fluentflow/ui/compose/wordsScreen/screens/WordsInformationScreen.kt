package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.decompose.words.information.FakeWordsInformationComponent
import yegor.cheprasov.fluentflow.decompose.words.information.WordsInformationComponent
import yegor.cheprasov.fluentflow.ui.compose.components.NewWordsAndPhrasesButton
import yegor.cheprasov.fluentflow.ui.compose.components.Percentage
import yegor.cheprasov.fluentflow.ui.compose.components.RelearnWordsButton

@Composable
fun WordsInformationScreen(component: WordsInformationComponent) {
    val context = LocalContext.current
    val uiState by component.uiState.subscribeAsState()
    Scaffold(topBar = {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { component.event(WordsInformationComponent.Event.Close) },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = null)
            }
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.padding(top = 46.dp).size(100.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uiState.imagePath)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.padding(20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp)
                        .padding(top = 24.dp)
                ) {
                    if (uiState.endedCount != 0) {
                        Percentage(percentage = uiState.allCount / uiState.endedCount)
                    } else {
                        Percentage(percentage = 0)
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${uiState.endedCount} / ",
                        color = Color.LightGray,
                        fontSize = 18.sp
                    )
                    Text(text = uiState.allCount.toString(), fontSize = 18.sp)
                }

                Text(
                    text = getCountLearnedText(uiState.endedCount, uiState.allCount, uiState.title),
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 58.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NewWordsAndPhrasesButton {
                    component.event(WordsInformationComponent.Event.LearnNewWords)
                }
                RelearnWordsButton {
                    component.event(WordsInformationComponent.Event.RelearnWords)
                }
            }
        }
    }
}

private fun getCountLearnedText(endedCount: Int, allCount: Int, title: String): String =
    if (isAllWordLearned(endedCount, allCount)) {
        "Вы уже знаете все слова по теме \"$title\""
    } else if (endedCount == 1) {
        "Вы уже знаете $endedCount слово по теме \"$title\""
    } else if (endedCount in 3..4) {
        "Вы уже знаете $endedCount слова по теме \"$title\""
    } else {
        "Вы уже знаете $endedCount слов по теме \"$title\""
    }

private fun isAllWordLearned(endedCount: Int, allCount: Int): Boolean =
    endedCount == allCount

@Preview
@Composable
private fun PreviewWordsInformationScreen() {
    WordsInformationScreen(component = FakeWordsInformationComponent())
}