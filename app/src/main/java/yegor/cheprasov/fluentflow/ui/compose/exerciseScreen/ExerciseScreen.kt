package yegor.cheprasov.fluentflow.ui.compose.exerciseScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.data.room.entities.ExerciseEntity
import yegor.cheprasov.fluentflow.decompose.dialog.DialogState
import yegor.cheprasov.fluentflow.decompose.exercise.ExerciseComponent
import yegor.cheprasov.fluentflow.decompose.exercise.FakeExerciseComponent
import yegor.cheprasov.fluentflow.ui.compose.components.LoadingScreen
import yegor.cheprasov.fluentflow.ui.compose.components.SoundButton
import yegor.cheprasov.fluentflow.ui.compose.exerciseScreen.state.ExerciseState
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.screens.LearnErrorDialog
import yegor.cheprasov.fluentflow.ui.compose.wordsScreen.screens.SuccessDialog

@Composable
fun ExerciseScreen(component: ExerciseComponent) {
    val uiState by component.uiState.subscribeAsState()

    val dialogState by component.dialogState.subscribeAsState()

    dialogState.child?.instance?.also {
        val state by it.state.subscribeAsState()
        when (val st = state) {
            is DialogState.ErrorDialogState -> {
                LearnErrorDialog(
                    word = st.word,
                    correctAnswer = st.correctWord,
                    chooseAnswer = st.chooseWord
                ) {
                    component.event(event = ExerciseComponent.Event.GoToNext)
                }
            }

            is DialogState.SuccessDialogState -> {
                SuccessDialog(word = st.word) {
                    component.event(event = ExerciseComponent.Event.GoToNext)
                }
            }
        }
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { component.event(ExerciseComponent.Event.Close) }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = null)
            }
            Text(
                text = "Переведите предложение",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (val state = uiState) {
                is ExerciseState.CurrentExercise -> {
                    CurrentExerciseScreen(state.exercise)
                }

                ExerciseState.Loading -> LoadingScreen()
            }
        }
    }
}

@Composable
fun CurrentExerciseScreen(exercise: ExerciseEntity) {

    val selectedWords = remember {
        mutableStateListOf<String>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SoundButton(word = exercise.sentense)
        Text(
            text = exercise.sentense,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp)
        )

    }
}

@Composable
private fun EnteredWords(words: List<String>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 124.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        backgroundColor = Color.White
    ) {

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Word(word: String, onRemove: (String) -> Unit) {
    Card(
        onClick = {
            onRemove(word)
        },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(text = word,)
    }
}

@Preview
@Composable
private fun PreviewExerciseScreen() {
    ExerciseScreen(component = FakeExerciseComponent())
}