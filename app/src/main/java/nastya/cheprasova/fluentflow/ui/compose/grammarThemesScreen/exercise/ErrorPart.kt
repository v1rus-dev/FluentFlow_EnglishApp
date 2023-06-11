package nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarExercise.state.SuccessState
import nastya.cheprasova.fluentflow.ui.compose.components.AppButton

@Composable
fun ErrorPart(
    state: SuccessState.Error,
    isLast: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CheckTextPart(text = state.text, correctWords = state.myAnswer, isCorrect = false)
            Text(
                "CORRECT ANSWER",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 16.dp),
                color = Color.LightGray
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                state.text.forEachIndexed { index, s ->
                    Text(s, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    if (index <= state.correctAnswer.lastIndex) {
                        Text(
                            text = state.correctAnswer[index],
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.Green,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.padding(horizontal = 11.dp)
                        )
                    }
                }
            }
        }
        AppButton(
            text = if (isLast) {
                "Завершить"
            } else {
                "Продолжить"
            }, modifier = Modifier.padding(bottom = 22.dp)
        ) {
            onClick.invoke(isLast)
        }
    }
}

@Preview
@Composable
private fun PreviewErrorPart() {
    ErrorPart(
        state = SuccessState.Error(
            text = listOf("The apartment", "big"),
            correctAnswer = listOf("is"),
            myAnswer = listOf("am")
        ),
        isLast = false,
        onClick = {}
    )
}