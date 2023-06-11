package nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.profile.state.InfoCardElement

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InfoCard(text: String, list: List<InfoCardElement>, modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    val changeExpand = remember { { isExpanded = !isExpanded } }
    Card(
        onClick = { if (list.size > 3) { changeExpand() } },
        modifier = modifier.fillMaxWidth().animateContentSize(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(text = text, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            list.take(3).forEachIndexed { index, infoCardElement ->
                InfoLine(element = infoCardElement)
                Spacer(modifier = Modifier.height(16.dp))
            }
            if (!isExpanded && list.size > 3) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "См. подробнее", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            } else if (isExpanded) {
                val ls = list.slice(3..list.lastIndex)
                ls.forEachIndexed { index, infoCardElement ->
                    InfoLine(element = infoCardElement)
                    if (index != ls.lastIndex) { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
private fun InfoLine(element: InfoCardElement, modifier: Modifier = Modifier) {

    val localDensity = LocalDensity.current

    var height by remember {
        mutableStateOf(0.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                height = with(localDensity) { coordinates.size.height.toDp() }
            }
            .clip(RoundedCornerShape(12.dp))
            .background(getColorForPercentage(element.percentage).copy(alpha = 0.3f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(element.percentage / 100f)
                .height(height),
            shape = if (element.percentage == 100) {
                RoundedCornerShape(12.dp)
            } else {
                RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
            },
            backgroundColor = getColorForPercentage(element.percentage)
        ) {}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = element.name,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            if (element.percentage < 100) {
                Text(
                    text = "${element.percentage}% Выполнено",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "Выполнено",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
            }
        }
    }
}

private fun getColorForPercentage(percentage: Int): Color =
    when (percentage) {
        in 0..20 -> Color(0xFFE00828)
        in 20..70 -> Color(0xFFFF8C71)
        in 70..100 -> Color(0xFF14AD68)
        else -> Color.White
    }

@Preview
@Composable
private fun PreviewInfoCard() {
    InfoCard(
        text = "Грамматика",
        list = listOf(
            InfoCardElement("Am, is, are", 100),
            InfoCardElement("Натоящее время", 95),
            InfoCardElement("Сколько стоит", 15),
            InfoCardElement("Я, мы, ты", 35),
            InfoCardElement("Am, is, are", 100),
            InfoCardElement("Натоящее время", 95),
            InfoCardElement("Сколько стоит", 15),
        )
    )
}