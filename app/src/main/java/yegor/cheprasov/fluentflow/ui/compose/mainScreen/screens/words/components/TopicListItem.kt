package yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import yegor.cheprasov.fluentflow.ui.compose.components.Percentage
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

@Composable
fun TopicListItem(item: WordsTopicViewEntity, onClick: (WordsTopicViewEntity) -> Unit) {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick(item) }, verticalAlignment = Alignment.CenterVertically) {
        Card(
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White,
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier.size(50.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Log.d("myTag", "imagePath: ${item.imagePath}")
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(item.imagePath)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 13.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.title)
                Row {
                    Text(text = "${item.endedCount} / ", color = Color.LightGray)
                    Text(text = item.allCount.toString())
                }
            }
            if (item.endedCount != 0) {
                Percentage(percentage = item.allCount / item.endedCount)
            } else {
                Percentage(percentage = 0)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTopicListItem() {
    TopicListItem(
        item = WordsTopicViewEntity(
            title = "Компьютеры",
            allCount = 24,
            endedCount = 10,
            imagePath = Uri.EMPTY,
            fileName = "",
            topicId = 0
        )
    ) {}
}