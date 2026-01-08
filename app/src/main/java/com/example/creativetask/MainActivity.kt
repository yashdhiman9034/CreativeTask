package com.example.creativetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreativeTaskApp()
        }
    }
}

@Composable
fun CreativeTaskApp() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF007AFF), // iOS blue
            background = Color.White,
            surface = Color.White
        )
    )
 {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            var showSkillScreen by remember { mutableStateOf(false) }

            var showTimeScreen by remember { mutableStateOf(false) }

            var showTaskScreen by remember { mutableStateOf(false) }

            when {
                showTaskScreen -> TaskScreen()
                showTimeScreen -> TimeSelectionScreen(
                    onFinish = { showTaskScreen = true }
                )
                showSkillScreen -> SkillSelectionScreen(
                    onContinue = { showTimeScreen = true }
                )
                else -> WelcomeScreen(
                    onStartClick = { showSkillScreen = true }
                )
            }


        }

    }
}

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Let’s personalize your creative journey",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStartClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Started")
        }
    }
}
@Composable
fun SkillSelectionScreen(onContinue: () -> Unit) {
    val skills = listOf(
        "Writing",
        "Drawing",
        "Photography",
        "Music",
        "Video Editing",
        "Problem Solving"
    )

    var selectedSkill by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Your strength",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Choose one to personalize tasks",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        skills.forEach { skill ->
            SkillItem(
                text = skill,
                selected = selectedSkill == skill,
                onClick = { selectedSkill = skill }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            enabled = selectedSkill != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Continue")
        }

    }
}
@Composable
fun SkillItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) Color(0xFFE8F0FE) else Color(0xFFF5F5F5)
    val textColor = if (selected) Color(0xFF007AFF) else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
@Composable
fun TimeSelectionScreen(onFinish: () -> Unit) {
    val times = listOf("10 min", "20 min", "30 min", "45 min", "60 min")
    var selectedTime by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Your time",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "How much time can you give daily?",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        times.forEach { time ->
            SkillItem(
                text = time,
                selected = selectedTime == time,
                onClick = { selectedTime = time }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinish,
            enabled = selectedTime != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Finish Setup")
        }

    }
}
@Composable
fun TaskScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Today’s Task",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F7FA)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Write a short paragraph describing your day without using the letter ‘A’.",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "⏱ 20 minutes",
                        color = Color.Gray
                    )
                }
            }
        }

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Mark as Done")
        }
    }
}
