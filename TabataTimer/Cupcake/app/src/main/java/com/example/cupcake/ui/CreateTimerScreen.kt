package com.example.cupcake.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.database.Timer

@Composable
fun CreateTimerScreen(
    onCreateButtonClicked: (timer: Timer) -> Unit = {},
    onCreateAndRunButtonClicked: (timer: Timer) -> Unit = {},
) {
    val maxLength = 4

    var nameInput by remember { mutableStateOf("") }
    var prepSecsInput by remember { mutableStateOf("0") }
    var workoutSecsInput by remember { mutableStateOf("0") }
    var restSecsInput by remember { mutableStateOf("0") }
    var repeatsInput by remember { mutableStateOf("0") }


    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "New Timer", style = MaterialTheme.typography.h4)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Timer name")
            OutlinedTextField(value = nameInput,
                onValueChange = {
                    nameInput = it
                })
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (prepSecsInput.toInt() > 0) {
                    prepSecsInput = (prepSecsInput.toInt() - 1).toString()
                }
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Preparation")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    value = prepSecsInput,
                    readOnly = true,
                    onValueChange = {
                        if (it.toIntOrNull() != null && it.length <= maxLength)
                            prepSecsInput = it
                    })
            }
            IconButton(onClick = {
                prepSecsInput = (prepSecsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (workoutSecsInput.toInt() > 0) {
                    workoutSecsInput = (workoutSecsInput.toInt() - 1).toString()
                } }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Workout")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    value = workoutSecsInput,
                    readOnly = true,
                    onValueChange = {
                        if (it.toIntOrNull() != null && it.length <= maxLength)
                            workoutSecsInput = it
                    })
            }
            IconButton(onClick = {
                workoutSecsInput = (workoutSecsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (restSecsInput.toInt() > 0) {
                    restSecsInput = (restSecsInput.toInt() - 1).toString()
                } }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Rest")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    value = restSecsInput,
                    readOnly = true,
                    onValueChange = {
                        if (it.toIntOrNull() != null && it.length <= maxLength)
                            restSecsInput = it
                    })
            }
            IconButton(onClick = {
                restSecsInput = (restSecsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (repeatsInput.toInt() > 0) {
                    repeatsInput = (repeatsInput.toInt() - 1).toString()
                } }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Repeats")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    value = repeatsInput,
                    readOnly = true,
                    onValueChange = {
                        if (it.toIntOrNull() != null && it.length <= maxLength)
                            repeatsInput = it
                    })
            }
            IconButton(onClick = {
                repeatsInput = (repeatsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                val timer = Timer(0,
                    nameInput,
                    prepSecsInput.toInt(),
                    workoutSecsInput.toInt(),
                    restSecsInput.toInt(),
                    restSecsInput.toInt()
                )
                onCreateButtonClicked(timer)
            }) {
                Icon(modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_create_24),
                    contentDescription = null)
                Text(text = "Create")
            }
            Button(onClick = {
                val timer = Timer(0,
                    nameInput,
                    prepSecsInput.toInt(),
                    workoutSecsInput.toInt(),
                    restSecsInput.toInt(),
                    restSecsInput.toInt()
                )
                onCreateAndRunButtonClicked(timer)
            }) {
                Icon(modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_access_alarm_24),
                    contentDescription = null)
                Text(text = "Create & Run")
            }
        }
    }
}