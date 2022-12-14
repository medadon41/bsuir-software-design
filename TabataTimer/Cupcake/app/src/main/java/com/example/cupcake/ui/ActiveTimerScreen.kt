package com.example.cupcake.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake.R
import com.example.cupcake.database.Timer
import com.example.cupcake.model.TimerStage
import com.example.cupcake.service.CurrentTimerState
import com.example.cupcake.service.TimerNotificationService
import kotlinx.coroutines.delay
import java.util.*
import kotlin.time.Duration.Companion.seconds

@Composable
fun ActiveTimerScreen(
    activeTimer: Timer
) {
    val context = LocalContext.current
    val service = TimerNotificationService(context)

    //var timerState by remember { mutableStateOf(CurrentTimerState) }

    var isRunning by remember { mutableStateOf(CurrentTimerState.isRunning) }
    var ticks by remember { mutableStateOf(0) }

    //isRunning = CurrentTimerState.isRunning

    var stages = getListItems(activeTimer)
    var currentStage by remember { mutableStateOf(TimerStage("", "", 0)
        .copy(StageId = stages.first.StageId, StageName = stages.first.StageName, StageDuration = stages.first.StageDuration)) }

    val progress = if (currentStage.StageDuration != 0 ) ticks / currentStage.StageDuration.toFloat() else 0f
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    val isAnyPrev = !currentStage.equals(stages.first)
    val isAnyNext = !currentStage.equals(stages.last)

    var abc = CurrentTimerState.isRunning

    if(isRunning && abc)
        LaunchedEffect(Unit) {
            //createNotificationChannel(channelId, context)
            isRunning = CurrentTimerState.isRunning
            while(true) {
                delay(1.seconds)
                if(ticks == currentStage.StageDuration) {
                    if(!currentStage.equals(stages.last)) {
                        currentStage = stages[stages.indexOf(currentStage) + 1]
                    }
                    else {
                        isRunning = false
                        break
                    }
                    ticks = 0
                }
                service.showNotification(
                    isRunning,
                    currentStage.StageName,
                    activeTimer.timerName,
                    ticks)
                ticks++
            }
        }

    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = ticks.toString(), fontSize = 70.sp)
            if(!isRunning) {
                CircularProgressIndicator(
                    modifier = Modifier.then(Modifier.size(150.dp))
                )
            }
            else {
                CircularProgressIndicator(
                    modifier = Modifier.then(Modifier.size(150.dp)),
                    progress = animatedProgress
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                ticks = 0
                isRunning = false
                currentStage = stages.first
            }) {
                Icon(modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_restore_24),
                    contentDescription = null)
                Text(text = "Reset")
            }
            if(isRunning) {
                Button(onClick = {
                    isRunning = false
                    CurrentTimerState.isRunning = false
                }) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_pause_24),
                        contentDescription = null
                    )
                    Text(text = "Pause")
                }
            }
            else {
                Button(onClick = {
                    CurrentTimerState.isRunning = true
                    isRunning = true
                    if (currentStage.equals(stages.last)) {
                        ticks = 0
                        currentStage = stages.first
                    }
                }) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = null
                    )
                    Text(text = "Start")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = activeTimer.timerName, style = MaterialTheme.typography.h4)
        Text(text = "Stage: ${currentStage.StageName}")
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { currentStage = stages[stages.indexOf(currentStage) - 1] },
            enabled = isAnyPrev) {
                Icon(modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_skip_previous_24),
                    contentDescription = null)
            }
            Button(onClick = { currentStage = stages[stages.indexOf(currentStage) + 1] },
            enabled = isAnyNext) {
                Icon(modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_skip_next_24),
                    contentDescription = null)
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        //servicesUI(context = LocalContext.current)

        DisplayList(items = getListItems(activeTimer), currentStage.StageId)
    }
}

@Composable
fun DisplayList(items: List<TimerStage>, stageId: String) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items) { item ->
            SequenceItem(item = item, item.StageId == stageId)
        }
    }
}

@Composable
fun SequenceItem(item: TimerStage, isActive: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, Color(0xFFD81B60)), shape = RoundedCornerShape(8.dp))
            .background(
                color = if (isActive) Color(0xFFAB47BC) else Color.Transparent
            )
            .height(60.dp),
    )
    {
        Text(text = "${item.StageName}: ${item.StageDuration} s")
    }
}

private fun getListItems(timer: Timer) : LinkedList<TimerStage> {
   var itemList = LinkedList<TimerStage>()
    var i = 0
    do {
        itemList.add(TimerStage("p$i", "Preparation", timer.prepTime))
        itemList.add(TimerStage("w$i","Workout", timer.workoutTime))
        itemList.add(TimerStage("r$i","Rest", timer.restTime))
        i++
    } while(i < timer.repeats)
    return itemList
}

//private fun createNotificationChannel(channelId: String, context: Context) {
//    // Create the NotificationChannel, but only on API 26+ because
//    // the NotificationChannel class is new and not in the support library
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "Timer"
//        val descriptionText = "Used for the timer state"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(TimerNotificationService.TIMER_CHANNEL_ID,
//            name,
//            importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//}
//
//fun showSimpleNotification(
//    context: Context,
//    channelId: String,
//    notificationId: Int,
//    textTitle: String,
//    textContent: String,
//    priority: Int = NotificationCompat.PRIORITY_DEFAULT
//) {
//    val intent = Intent(context, MainActivity::class.java).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    }
//    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_MUTABLE)
//    //val sPendingIntent: PendingIntent = PendingIntent.
//
//    val builder = NotificationCompat.Builder(context, channelId)
//        .setSmallIcon(R.drawable.ic_baseline_play_arrow_24)
//        .setContentTitle(textTitle)
//        .setContentText(textContent)
//        //.setContentIntent(pendingIntent)
//        //.addAction(R.drawable.ic_baseline_play_arrow_24, "Start", )
//        .setPriority(priority)
//
//    with(NotificationManagerCompat.from(context)) {
//        notify(notificationId, builder.build())
//    }
//}


@Preview
@Composable
fun TimerPreview() {
    ActiveTimerScreen(Timer(0, "", 0, 0, 0, 0))
}
