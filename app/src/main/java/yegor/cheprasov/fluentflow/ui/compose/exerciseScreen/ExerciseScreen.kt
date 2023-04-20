package yegor.cheprasov.fluentflow.ui.compose.exerciseScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import yegor.cheprasov.fluentflow.decompose.dialog.DialogState
import yegor.cheprasov.fluentflow.decompose.exercise.ExerciseComponent
import yegor.cheprasov.fluentflow.decompose.exercise.FakeExerciseComponent
import yegor.cheprasov.fluentflow.ui.compose.components.AppButton
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
                    CurrentExerciseScreen(state = state, removeWord = {
                        component.event(ExerciseComponent.Event.RemoveWord(it))
                    }, selectWord = {
                        component.event(ExerciseComponent.Event.SelectWord(it))
                    }, check = {
                        component.event(ExerciseComponent.Event.Check)
                    })
                }

                ExerciseState.Loading -> LoadingScreen()
            }
        }
    }
}

@Composable
fun CurrentExerciseScreen(
    state: ExerciseState.CurrentExercise,
    selectWord: (SelectWordViewEntity) -> Unit,
    removeWord: (SelectWordViewEntity) -> Unit,
    check: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SoundButton(word = state.sentense)
        Text(
            text = state.sentense,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp)
        )
        EnteredWords(
            words = state.exercise.selectWords,
            modifier = Modifier.padding(top = 16.dp)
        ) { word: SelectWordViewEntity ->
            removeWord(word)
        }
        SelectWordsPart(
            words = state.exercise.allWords,
            modifier = Modifier.padding(top = 16.dp),
            onSelect = { word ->
                selectWord(word)
            }
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            AppButton(text = "Проверить ответ", modifier = Modifier.padding(bottom = 22.dp)) {
                check()
            }
        }
    }
}

@Composable
private fun EnteredWords(
    words: List<SelectWordViewEntity>,
    modifier: Modifier = Modifier,
    removeWord: (SelectWordViewEntity) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 124.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        backgroundColor = Color.White
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 4.dp
        ) {
            words.forEach {
                Word(word = it, onRemove = removeWord)
            }
        }
    }
}

@Composable
private fun SelectWordsPart(
    words: List<SelectWordViewEntity>,
    modifier: Modifier = Modifier,
    onSelect: (SelectWordViewEntity) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 4.dp,
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        crossAxisAlignment = FlowCrossAxisAlignment.Center
    ) {
        words.forEach { word ->
            SelectWord(word = word, onSelect)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Word(word: SelectWordViewEntity, onRemove: (SelectWordViewEntity) -> Unit) {
    Card(
        onClick = {
            onRemove(word)
        },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(0xFFEEEEEF),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(
            text = word.text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SelectWord(word: SelectWordViewEntity, onClick: (SelectWordViewEntity) -> Unit) {
    val isSelected = word.isSelected
    Card(
        onClick = {
            onClick(word)
        },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = if (!isSelected) {
            Color.White
        } else {
            Color.Gray
        },
        contentColor = if (!isSelected) {
            Color.Black
        } else {
            Color.Gray
        },
        border = if (!isSelected) {
            BorderStroke(1.dp, Color.LightGray)
        } else {
            null
        }
    ) {
        Text(
            text = word.text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun PreviewExerciseScreen() {
    ExerciseScreen(component = FakeExerciseComponent())
}

@Preview
@Composable
private fun PreviewSelectWord() {
    var isSelected by remember {
        mutableStateOf(false)
    }
    SelectWord(word = SelectWordViewEntity("смотрю")) {}

}