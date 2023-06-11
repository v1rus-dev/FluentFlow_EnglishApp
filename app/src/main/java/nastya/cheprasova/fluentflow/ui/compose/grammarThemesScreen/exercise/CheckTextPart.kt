package nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckTextPart(
    text: List<String>,
    correctWords: List<String>,
    isCorrect: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            text.forEachIndexed { index, s ->
                Text(s, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                if (index <= correctWords.lastIndex) {
                    Text(
                        text = correctWords[index],
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = if (isCorrect) {
                            Color.Green
                        } else {
                            Color.Red
                        },
                        textDecoration = if (isCorrect) {
                            TextDecoration.Underline
                        } else {
                            TextDecoration.LineThrough
                        },
                        modifier = Modifier.padding(horizontal = 11.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSuccessPart() {
    CheckTextPart(
        text = listOf("The apartment", "big"),
        correctWords = listOf("is"),
        isCorrect = true
    )
}