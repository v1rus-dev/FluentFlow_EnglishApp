package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.exercise

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.state.GrammarExerciseUiState
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.state.SuccessState
import yegor.cheprasov.fluentflow.ui.compose.components.LoadingScreen
import yegor.cheprasov.fluentflow.ui.compose.components.ProgressBar
import yegor.cheprasov.fluentflow.ui.compose.components.SecondToolbar
import yegor.cheprasov.fluentflow.ui.theme.background

@Composable
fun ExerciseScreen(
    state: GrammarExerciseUiState,
    onCheck: (String) -> Unit,
    onContinue: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SecondToolbar(title = "Выберите правильный ответ") {
                onBack()
            }
        },
        backgroundColor = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (state) {
                GrammarExerciseUiState.Loading -> LoadingScreen()
                is GrammarExerciseUiState.Success -> SuccessScree(
                    state = state,
                    onCheck = onCheck,
                    onContinue = onContinue
                )
            }
        }
    }
}

@Composable
private fun SuccessScree(
    state: GrammarExerciseUiState.Success,
    onCheck: (String) -> Unit,
    onContinue: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ProgressBar(
            value = state.percentage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 80.dp)
        )
        Text(
            text = state.grammarExerciseViewEntity.translate,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.grammarExerciseViewEntity.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 36.dp)
                .size(100.dp)
        )
        AnimatedContent(targetState = state.successState, label = "ExerciseScreenLabel") {
            when (it) {
                is SuccessState.Error -> ErrorPart(
                    state = it,
                    isLast = state.isLast,
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .padding(horizontal = 16.dp),
                    onClick = onContinue
                )

                SuccessState.Success -> CheckTextPart(
                    text = state.grammarExerciseViewEntity.text,
                    correctWords = state.grammarExerciseViewEntity.correctWords,
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .padding(horizontal = 16.dp),
                    isCorrect = true
                )

                SuccessState.None -> AnswersPart(
                    text = state.grammarExerciseViewEntity.text,
                    answers = state.grammarExerciseViewEntity.words,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = onCheck
                )
            }
        }
    }
}