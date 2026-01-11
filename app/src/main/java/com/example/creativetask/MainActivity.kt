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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch



// -------------------- ACTIVITY --------------------

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CreativeTaskApp() }
    }
}


// -------------------- APP ROOT --------------------

@Composable
fun CreativeTaskApp() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF007AFF),
            background = Color.White,
            surface = Color.White
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {

            var showSkill by remember { mutableStateOf(false) }
            var showTime by remember { mutableStateOf(false) }
            var showTask by remember { mutableStateOf(false) }

            var selectedSkill by remember { mutableStateOf("") }
            var selectedTime by remember { mutableStateOf("") }

            when {
                showTask -> TaskScreen(selectedSkill, selectedTime)

                showTime -> TimeSelectionScreen { time ->
                    selectedTime = time
                    showTask = true
                }

                showSkill -> SkillSelectionScreen { skill ->
                    selectedSkill = skill
                    showTime = true
                }

                else -> WelcomeScreen {
                    showSkill = true
                }
            }
        }
    }
}

// -------------------- SCREENS --------------------

@Composable
fun WelcomeScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("Let’s personalize your creative journey")
        Spacer(Modifier.height(32.dp))
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
            Text("Get Started")
        }
    }
}

@Composable
fun SkillSelectionScreen(onContinue: (String) -> Unit) {
    val skills = listOf(
        "Writing", "Drawing", "Photography",
        "Music", "Video Editing", "Problem Solving"
    )
    var selected by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Your strength", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        skills.forEach {
            PillItem(it, selected == it) { selected = it }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { selected?.let(onContinue) },
            enabled = selected != null,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun TimeSelectionScreen(onFinish: (String) -> Unit) {
    val times = listOf("10 min", "20 min", "30 min", "45 min", "60 min")
    var selected by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Your time", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        times.forEach {
            PillItem(it, selected == it) { selected = it }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { selected?.let(onFinish) },
            enabled = selected != null,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Finish Setup")
        }
    }
}

@Composable
fun TaskScreen(skill: String, time: String) {
    val vm: TaskViewModel = viewModel()

    LaunchedEffect(Unit) {
        vm.generateTask(skill, time)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Today’s Task", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7FA))
            ) {
                Text(
                    vm.taskText.value,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text("Mark as Done")
        }
    }
}

// -------------------- COMPONENT --------------------

@Composable
fun PillItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) Color(0xFFE8F0FE) else Color(0xFFF5F5F5))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text, color = if (selected) Color(0xFF007AFF) else Color.Black)
    }
}

// -------------------- VIEWMODEL --------------------

class TaskViewModel : ViewModel() {

    val taskText = mutableStateOf("Loading task...")

    fun generateTask(skill: String, time: String) {
        viewModelScope.launch {
            // TEMP logic – replace with Gemini later
            taskText.value =
                "Create a $skill task that can be completed in $time."
        }
    }
}
