package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details.utils.GrammarDetailType
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details.utils.OneBlockVE

@Composable
fun GrammarDetailBlockComponent(
    grammarDetailType: GrammarDetailType.BlockViewEntity,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color(0xFFEEEEEF)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            grammarDetailType.list.forEachIndexed { index, oneBlockVE ->
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = oneBlockVE.text, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = oneBlockVE.translate,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
                if (index != grammarDetailType.list.lastIndex) {
                    Divider(modifier = Modifier.fillMaxWidth(), color = Color(0xFFEEEEEF))
                }
            }
        }
    }
}

@Preview(name = "PreviewOneBlock")
@Composable
private fun PreviewGrammarDetailOneBlockComponent() {
    GrammarDetailBlockComponent(
        GrammarDetailType.BlockViewEntity(
            listOf(
                OneBlockVE(
                    text = "Hi! I am Max.",
                    translate = "Привет! Я Макс."
                )
            )
        )
    )
}

@Preview(name = "PreviewTwoBlock")
@Composable
private fun PreviewGrammarDetailTwoBlockComponent() {
    GrammarDetailBlockComponent(
        GrammarDetailType.BlockViewEntity(
            listOf(
                OneBlockVE(
                    text = "My suitcases are black.",
                    translate = "Мои чемоданы черные."
                ),
                OneBlockVE(
                    text = "They are big.",
                    translate = "Они большие."
                )
            )
        )
    )
}