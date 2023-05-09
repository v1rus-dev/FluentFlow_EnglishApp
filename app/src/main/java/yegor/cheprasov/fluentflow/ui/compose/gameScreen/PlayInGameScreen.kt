package yegor.cheprasov.fluentflow.ui.compose.gameScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.decompose.game.game.FakePlayInGameComponent
import yegor.cheprasov.fluentflow.decompose.game.game.PlayInGameComponent


@Composable
fun PlayInGameScreen(
    playInGameComponent: PlayInGameComponent
) {
    val uiState by playInGameComponent.uiState.subscribeAsState()

    AnimatedContent(targetState = uiState.currentIndex, label = "") {
        PlayInGameScreen1(uiState = uiState, close = {
            playInGameComponent.event(PlayInGameComponent.Event.OnBack)
        }) {
            playInGameComponent.event(PlayInGameComponent.Event.CheckWord(it.word))
        }
    }

}

@Composable
fun PlayInGameScreen1(
    uiState: GameState,
    close: () -> Unit,
    onClick: (GameWord) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var isCanClick: Boolean by remember {
        mutableStateOf(true)
    }

    val onClick1: (GameWord) -> Unit = remember {
        {
            if (isCanClick) {
                onClick(it)
                isCanClick = false
            }
        }
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { close() }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }

            Text(
                text = "Выберите значение",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Timer(
                time = uiState.timer,
                isEnding = uiState.timerIsEnding,
                modifier = Modifier.padding(top = 30.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(uiState.imgUri)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 50.dp)
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.weight(1f))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 22.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(uiState.words) { index, item ->
                    Word(item, onClick1)
                    if (index != uiState.words.lastIndex) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun Timer(time: String, isEnding: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Timer,
            contentDescription = null,
            tint = if (isEnding) {
                Color.Red
            } else {
                Color.Green
            }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = time,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            color = if (isEnding) {
                Color.Red
            } else {
                Color.Green
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Word(item: GameWord, onClick: (GameWord) -> Unit) {
    Card(
        onClick = { onClick(item) },
        enabled = item.isCorrect == null,
        backgroundColor = when (item.isCorrect) {
            null -> Color.White
            true -> Color.Green
            false -> Color.Red
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(15.dp),
        border = if (item.isCorrect == null) {
            BorderStroke(1.dp, Color.LightGray)
        } else {
            null
        }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.word,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = if (item.isCorrect == null) {
                    Color.Black
                } else {
                    Color.White
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPlayInGameScreen() {
    PlayInGameScreen(playInGameComponent = FakePlayInGameComponent())
}