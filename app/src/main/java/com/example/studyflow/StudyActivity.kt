package com.example.studyflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyflow.ui.theme.StudyFlowTheme

class StudyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyFlowTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    var timeOfDay by remember { mutableStateOf("") }
    var suggestion by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = timeOfDay,
                onValueChange = { timeOfDay = it },
                label = { Text(stringResource(id = R.string.enter_time_hint)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    suggestion = getSuggestionForTime(timeOfDay)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.get_suggestion))
            }
            Spacer(modifier = Modifier.height(32.dp))
            if (suggestion.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.suggestion_prefix) + suggestion,
                    fontSize = 18.sp,
                    lineHeight = 24.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    timeOfDay = ""
                    suggestion = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.reset))
            }
        }
    }
}

private fun getSuggestionForTime(time: String): String {
    return when (time.lowercase().trim()) {
        "morning" -> "Send a 'Good Morning' text to a family memeber"
        "afternoon" -> "Time for a review or some practice problems. Stay hydrated!"
        "evening" -> "Summarize what you learned today and plan for tomorrow."
        "night" -> "Light reading or reflection. Get enough sleep!"
        else -> "Keep up the great work! Enter a time of day to get a specific tip."
    }
}
