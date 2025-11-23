package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

@Composable
fun TodoApp() {

    var taskText by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(listOf<String>()) }

    // For EDIT operation
    var editMode by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "My Todo App", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // INPUT FIELD
        OutlinedTextField(
            value = taskText,
            onValueChange = { taskText = it },
            label = { Text("Enter task") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ADD / UPDATE BUTTON
        Button(
            onClick = {
                if (taskText.isNotEmpty()) {
                    if (editMode) {
                        // UPDATE OPERATION
                        val updatedList = taskList.toMutableList()
                        updatedList[editIndex] = taskText
                        taskList = updatedList
                        editMode = false
                        editIndex = -1
                    } else {
                        // CREATE OPERATION
                        taskList = taskList + taskText
                    }
                    taskText = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(if (editMode) "Update Task" else "Add Task")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // READ AND DISPLAY TASKS
        for ((index, task) in taskList.withIndex()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "• $task")

                Row {
                    // EDIT BUTTON
                    TextButton(onClick = {
                        taskText = task
                        editMode = true
                        editIndex = index
                    }) {
                        Text("Edit")
                    }

                    // DELETE BUTTON
                    TextButton(onClick = {
                        taskList = taskList - task
                    }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
