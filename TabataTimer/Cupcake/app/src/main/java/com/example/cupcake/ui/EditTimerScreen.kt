package com.example.cupcake.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.database.Timer
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun EditTimerScreen(
    timerState: MutableStateFlow<Timer>,
    onSaveButtonClicked: (timer: Timer) -> Unit = {},
) {
    val currentTimer = timerState.collectAsState().value
    val maxLength = 4

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        Text(text = "Edit Timer", style = MaterialTheme.typography.h4)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Timer Name")
            OutlinedTextField(value = currentTimer.timerName,
                onValueChange = {
                    timerState.value = currentTimer.copy(timerName = it)
                })
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (currentTimer.prepTime > 0) {
                    timerState.value = currentTimer.copy(prepTime = currentTimer.prepTime - 1)
                    //prepSecsInput = (prepSecsInput.toInt() - 1).toString()
                }
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Preparation")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    value = currentTimer.prepTime.toString(),
                    readOnly = true,
                    onValueChange = { })
            }
            IconButton(onClick = {
                timerState.value = currentTimer.copy(prepTime = currentTimer.prepTime + 1)
                //prepSecsInput = (prepSecsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (currentTimer.workoutTime > 0) {
                    timerState.value = currentTimer.copy(workoutTime = currentTimer.workoutTime - 1)
                    //prepSecsInput = (prepSecsInput.toInt() - 1).toString()
                }
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Workout")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    value = currentTimer.workoutTime.toString(),
                    readOnly = true,
                    onValueChange = {  })
            }
            IconButton(onClick = {
                timerState.value = currentTimer.copy(workoutTime = currentTimer.workoutTime + 1)
                //workoutSecsInput = (workoutSecsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (currentTimer.restTime > 0) {
                    timerState.value = currentTimer.copy(restTime = currentTimer.restTime - 1)
                    //prepSecsInput = (prepSecsInput.toInt() - 1).toString()
                }
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Rest")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    readOnly = true,
                    value = currentTimer.restTime.toString(),
                    onValueChange = { })
            }
            IconButton(onClick = {
                timerState.value = currentTimer.copy(restTime = currentTimer.restTime + 1)
                // restSecsInput = (restSecsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (currentTimer.repeats > 0) {
                    timerState.value = currentTimer.copy(repeats = currentTimer.repeats - 1)
                    // repeatsInput = (repeatsInput.toInt() - 1).toString()
            } }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    contentDescription = null)
            }
            Column() {
                Text(text = "Repeats")
                OutlinedTextField(modifier = Modifier.width(70.dp),
                    readOnly = true,
                    value = currentTimer.repeats.toString(),
                    onValueChange = { })
            }
            IconButton(onClick = {
                timerState.value = currentTimer.copy(repeats = currentTimer.repeats + 1)
                //repeatsInput = (repeatsInput.toInt() + 1).toString()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
//                val timer = Timer(0,
//                    nameInput,
//                    prepSecsInput.toInt(),
//                    workoutSecsInput.toInt(),
//                    restSecsInput.toInt(),
//                    restSecsInput.toInt()
//                )
                onSaveButtonClicked(timerState.value)
            }) {
                Icon(modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_save_24),
                    contentDescription = null)
                Text(text = "Save")
            }
        }
    }
}


//@Preview
//@Composable
//fun EditTimerPreview() {
//    ModifyTimerScreen(Timer(0, "", 1, 2, 3, 4))
//}