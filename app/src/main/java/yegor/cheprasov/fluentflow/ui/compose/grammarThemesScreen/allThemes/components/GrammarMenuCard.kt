package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.allThemes.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yegor.cheprasov.fluentflow.ui.compose.components.FavoriteButton
import yegor.cheprasov.fluentflow.ui.compose.components.Percentage
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity

private val titleTextColor = Color(0xFFC5C6C7)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GrammarMenuCard(
    element: GrammarElementViewEntity,
    modifier: Modifier = Modifier,
    onClick: (GrammarElementViewEntity) -> Unit
) {
    val context = LocalContext.current
    Card(
        onClick = { onClick(element) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = element.subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = titleTextColor
                )
                Text(
                    text = element.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(modifier = Modifier.padding(top = 6.dp)) {
                    element.examples.forEachIndexed { index, example ->
                        GrammarExample(text = example)
                        if (index != element.examples.lastIndex) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                FavoriteButton(
                    isFavorite = element.isFavorite,
                    modifier = Modifier.size(24.dp)
                ) {
                    Toast.makeText(context, "Tap on favorite", Toast.LENGTH_SHORT).show()
                }
                Percentage(
                    percentage = element.percentage,
                    modifier = Modifier
                        .width(80.dp)
                        .padding(top = 32.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGrammarMenuCard() {
    GrammarMenuCard(
        element = GrammarElementViewEntity(
            title = "Am, is, are",
            subtitle = "Present Simple",
            examples = listOf("This is", "It is"),
            percentage = 35,
            isFavorite = false,
            fileName = "",
            exerciseFile = ""
        )
    ) {}
}