package yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.R
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.FakeProfileComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.profile.ProfileMainComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.ProfileState
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.state.WordsInfo

@Composable
fun ProfileMainScreen(component: ProfileMainComponent) {

    val state by component.state.subscribeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { TopPart(state = state) }
            item { WordsPart(state = state) }
            InfoPart()
        }
    }
}

private fun LazyListScope.SubListPartTop(state: ProfileState) {
    item {
        TopPart(state)
    }
}

@Composable
private fun TopPart(state: ProfileState, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val selectNewImage = remember {
        {

        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(targetState = state.imgPath, label = "AnimatedProfileImage") {
            if (it.isEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.person_icon),
                    contentDescription = null,
                    tint = Color(0xFFC5C6C7),
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            selectNewImage()
                        }
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(
                            state.imgPath
                        ).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )
            }
        }

        Text(text = state.name, modifier = Modifier.padding(start = 24.dp))
    }
}

@Composable
private fun WordsPart(state: ProfileState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Слова",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp)
        )
        LazyRow(contentPadding = PaddingValues(16.dp)) {
            itemsIndexed(state.wordsInfoList) { index: Int, item: WordsInfo ->
                Word(wordsInfo = item)
                if (index != state.wordsInfoList.lastIndex) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }
}

@Composable
private fun Word(wordsInfo: WordsInfo) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(141.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(wordsInfo.img)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(vertical = 22.dp)
                    .size(30.dp)
            )
            Column(
                modifier = Modifier.padding(start = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = wordsInfo.count.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = wordsInfo.name,
                    fontSize = 16.sp,
                    color = Color(0xFFC5C6C7),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

private fun LazyListScope.InfoPart() {
    items(10) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(324.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Text(text = "Text: $it")
        }
    }
}

@Preview
@Composable
private fun PreviewProfileMainScreen() {
    ProfileMainScreen(component = FakeProfileComponent())
}