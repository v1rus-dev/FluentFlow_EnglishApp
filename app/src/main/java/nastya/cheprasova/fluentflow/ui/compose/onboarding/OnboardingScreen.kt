package nastya.cheprasova.fluentflow.ui.compose.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import nastya.cheprasova.fluentflow.R
import nastya.cheprasova.fluentflow.decompose.onboarding.FakeOnboardingComponent
import nastya.cheprasova.fluentflow.decompose.onboarding.OnBoardingComponent

@Composable
fun OnboardingScreen(component: OnBoardingComponent) {

    val state by component.uiState.subscribeAsState()

    val onClick: (Int) -> Unit = remember {
        {
            component.event(
                when (it) {
                    0 -> {
                        OnBoardingComponent.Event.SelectElementary
                    }

                    1 -> {
                        OnBoardingComponent.Event.SelectMiddle
                    }

                    else -> {
                        OnBoardingComponent.Event.SelectAdvanced
                    }
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Button(
                onClick = { component.event(OnBoardingComponent.Event.Continue) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 22.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                enabled = state.selectedLevel != null,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFBD6EEB),
                    disabledBackgroundColor = Color.LightGray
                )
            ) {
                Text(
                    text = "Продолжить",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_img),
                    contentDescription = null
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.shape_onboarding),
                        contentDescription = null
                    )
                    Text(
                        text = "Насколько хорошо вы \n" +
                                "знаете английский?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 30.dp)
            ) {
                LevelButton(
                    text = "Я только начинаю изучать английский",
                    level = 0,
                    isSelected = state.selectedLevel != null && state.selectedLevel == 0,
                    onClick = onClick
                )
                Spacer(modifier = Modifier.height(20.dp))
                LevelButton(
                    text = "Я могу вести простую беседу",
                    level = 1,
                    isSelected = state.selectedLevel != null && state.selectedLevel == 1,
                    onClick = onClick
                )
                Spacer(modifier = Modifier.height(20.dp))
                LevelButton(
                    text = "У меня средний уровень или выше",
                    level = 2,
                    isSelected = state.selectedLevel != null && state.selectedLevel == 2,
                    onClick = onClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LevelButton(
    text: String,
    level: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit
) {
    if (level < 0 || level > 2) {
        return
    }

    Card(
        onClick = {
            onClick(level)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(91.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = if (isSelected) {
            Color(0xFFF6E8FF)
        } else {
            Color.White
        },
        border = BorderStroke(
            1.dp, if (isSelected) {
                Color(0xFFBD6EEB)
            } else {
                Color.LightGray
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Card(
                    modifier = Modifier
                        .width(8.dp)
                        .height(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFBD6EEB)),
                    backgroundColor = Color(0xFFBD6EEB)
                ) {}
                Card(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(8.dp)
                        .height(24.dp),
                    border = BorderStroke(1.dp, Color(0xFFBD6EEB)),
                    backgroundColor = if (level >= 1) {
                        Color(0xFFBD6EEB)
                    } else {
                        Color.White
                    }
                ) {}
                Card(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(8.dp)
                        .height(32.dp),
                    border = BorderStroke(1.dp, Color(0xFFBD6EEB)),
                    backgroundColor = if (level >= 2) {
                        Color(0xFFBD6EEB)
                    } else {
                        Color.White
                    }
                ) {}
            }
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 30.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewOnboardingScreen() {
    OnboardingScreen(component = FakeOnboardingComponent())
}