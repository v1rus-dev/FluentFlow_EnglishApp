package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnswersPart(text: List<String>, answers: List<String>, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Column(modifier = modifier) {
        TextPart(
            text = text,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 36.dp)
        )
        Column(modifier = Modifier.padding(top = 34.dp)) {
            answers.forEachIndexed { index, text ->
                Answer(text = text, onClick = onClick)
                if (index != answers.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun Answer(text: String, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onClick.invoke(text)
            },
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview
@Composable
private fun PreviewAnswersPart() {
    AnswersPart(text = listOf("The apartment", "big"), answers = listOf("is", "am", "are"), onClick = {})
}