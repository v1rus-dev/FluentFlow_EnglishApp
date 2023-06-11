package nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nastya.cheprasova.fluentflow.R
import nastya.cheprasova.fluentflow.ui.compose.components.Percentage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThemeItem(
    title: String,
    description: String,
    percentage: Int,
    @DrawableRes background: Int,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(126.dp)
            .clip(RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .paint(
                    painterResource(id = background),
                    contentScale = ContentScale.Crop
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Default,
                    modifier = Modifier.padding(top = 48.dp)
                )
                Text(
                    text = description,
                    fontSize = 10.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Light
                )
                Percentage(
                    percentage = percentage.toFloat(),
                    modifier = Modifier
                        .width(134.dp)
                        .padding(top = 11.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    }
}

@Preview(name = "ThemItem")
@Composable
private fun PreviewThemeItem() {
    ThemeItem(
        title = "Грамматика",
        description = "Изучайте правила грамматики",
        percentage = 75,
        background = R.drawable.first_them_bg,
        image = R.drawable.grammar_img
    ) {}
}