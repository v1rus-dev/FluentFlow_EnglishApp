package nastya.cheprasova.fluentflow.ui.compose.gameScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import nastya.cheprasova.fluentflow.decompose.game.finishGame.FakeFinishGameComponent
import nastya.cheprasova.fluentflow.decompose.game.finishGame.FinishGameComponent
import nastya.cheprasova.fluentflow.ui.compose.components.AppButton

@Composable
fun FinishGameScreen(component: FinishGameComponent) {
    val uiState by component.uiState.subscribeAsState()

    Scaffold(bottomBar = {
        AppButton(
            text = "Хорошо",
            modifier = Modifier
                .padding(bottom = 22.dp)
                .padding(horizontal = 16.dp)
        ) {
            component.event(FinishGameComponent.Event.Continue)
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Отлично выполнено!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Вы ответили правильно на ${uiState.correctWords} слов из ${uiState.allWords}",
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
            LazyColumn(modifier = Modifier.padding(top = 35.dp)) {
                itemsIndexed(uiState.errorWords) { index, item ->
                    ErrorWord(item)
                    if (index != uiState.errorWords.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorWord(item: FinishGameComponent.Word) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = item.word,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Red
            )
            Text(text = item.translate, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
        }
        Icon(imageVector = Icons.Rounded.Error, contentDescription = null, tint = Color.Red)
    }
}

@Preview
@Composable
private fun PreviewFinishGameScreen() {
    FinishGameScreen(component = FakeFinishGameComponent())
}