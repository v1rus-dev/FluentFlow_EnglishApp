package nastya.cheprasova.fluentflow.ui.compose.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SoundButton(word: String, modifier: Modifier = Modifier) {
    Card(
        onClick = {},
        shape = CircleShape,
        backgroundColor = Color(0xFFBD6EEB),
        modifier = modifier
            .padding(top = 26.dp)
            .size(50.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.VolumeUp,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(13.dp)
        )
    }
}

@Composable
fun rememberTextToSpeech() {
    val context = LocalContext.current
}

class AppTextToSpeech(context: Context) {



}