package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LearnErrorDialog(
    word: String,
    correctAnswer: String,
    chooseAnswer: String,
    onContinue: () -> Unit
) {
    Dialog(onDismissRequest = { onContinue() }) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Неправильно!",
                        color = Color.Red,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(color = Color.LightGray)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = word,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 28.dp)
                    )
                    Text(
                        text = "Correct answer!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF00B733),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = correctAnswer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Correct answer!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = chooseAnswer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Card(
                        onClick = {
                            onContinue()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .height(50.dp), shape = RoundedCornerShape(25.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Хорошо",
                                color = Color.Red,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLearnErrorDialog() {
    LearnErrorDialog(
        word = "flight attendant",
        correctAnswer = "Бортпроводник",
        chooseAnswer = "Неправильный ответ",
        onContinue = {}
    )
}