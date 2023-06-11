
package nastya.cheprasova.fluentflow.ui.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val PERCENTAGE_COLOR_FIRST = Color(0xFF5BD3C7)
val PERCENTAGE_COLOR_SECOND = Color(0xFFE5E5E5)

@Composable
fun Percentage(
    percentage: Float,
    modifier: Modifier = Modifier
) {
    val percentageValue = percentage * 0.01f
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(7.dp),
        backgroundColor = PERCENTAGE_COLOR_SECOND,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(percentageValue)
                    .height(7.dp),
                backgroundColor =  PERCENTAGE_COLOR_FIRST,
                elevation = 0.dp,
                shape = RoundedCornerShape(5.dp)
            ) {

            }
        }
    }
}

@Preview(name = "ThemItemPercentage")
@Composable
private fun PreviewPercentage() {
    Percentage(76f)
}