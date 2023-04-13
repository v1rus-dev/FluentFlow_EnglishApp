@file:OptIn(ExperimentalMaterialApi::class)

package yegor.cheprasov.fluentflow.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yegor.cheprasov.fluentflow.R
import yegor.cheprasov.fluentflow.ui.theme.background

private val FIRST_BG = Color(0xFFBD6EEB)
private val SECOND_BG = Color(0xFFB748F8)

@Composable
fun NewWordsAndPhrasesButton(
    modifier: Modifier = Modifier,
    state: NewWordsAndPhrasesState? = null,
    onClick: () -> Unit
) {
    Card(onClick = { onClick() }, shape = RoundedCornerShape(24.dp)) {
        Box(
            modifier = modifier
                .width(144.dp)
                .height(100.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = SECOND_BG,
            ) {

            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(97.dp),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = FIRST_BG,
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.lamp),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        if (state != null) {
                            Text(
                                text = "${state.learnedAmount}/${state.allAmount}",
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 14.dp)
                    ) {
                        Text(
                            text = "Учить новые слова и фразы",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RelearnWordsButton(
    modifier: Modifier = Modifier,
    firstColor: Color = Color(0xFFF9C950),
    secondColor: Color = Color(0xFFEEB524),
    onClick: () -> Unit
) {
    Card(onClick = { onClick() }, shape = RoundedCornerShape(24.dp)) {
        Box(
            modifier = modifier
                .width(144.dp)
                .height(100.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = secondColor,
            ) {

            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(97.dp),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = firstColor,
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = Icons.Filled.Update,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 14.dp)
                    ) {
                        Text(
                            text = "Учить новые слова и фразы",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewNewWordsAnPhrases() {
    NewWordsAndPhrasesButton(
        state = NewWordsAndPhrasesState(
            0,
            0
        ),
        onClick = {}
    )
}

@Composable
@Preview
private fun PreviewRelearnWordsButton() {
    RelearnWordsButton(
        onClick = {}
    )
}

data class NewWordsAndPhrasesState(
    val learnedAmount: Int,
    val allAmount: Int
)