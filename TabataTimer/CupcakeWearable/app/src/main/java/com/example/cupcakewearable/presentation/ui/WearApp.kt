import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.cupcakewearable.presentation.misc.TimerStage
import com.example.cupcakewearable.presentation.theme.CupcakeWearableTheme
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.time.Duration.Companion.seconds

@Composable
fun WearApp() {
    CupcakeWearableTheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {
    var ticks by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    var stages = getListItems()
    var currentStage by remember { mutableStateOf(TimerStage("", "", 0)
        .copy(StageId = stages.first.StageId, StageName = stages.first.StageName, StageDuration = stages.first.StageDuration)) }

    val progress = if (currentStage.StageDuration != 0 ) ticks / currentStage.StageDuration.toFloat() else 0f
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    if(isRunning)
        LaunchedEffect(Unit) {
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
                ticks++
            }
        }

    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                text = "Wearable Timer",
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                text = "Stage: ${currentStage.StageName}",
                fontSize = 10.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                text = ticks.toString(),
                fontSize = 70.sp
            )
            Button(
                onClick = { isRunning = !isRunning },
            ) {
                if(!isRunning) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Start")
                }
                else {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Stop")
                }
            }
        }
        if(!isRunning) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        else {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize(),
            progress = animatedProgress)
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    WearApp()
}

private fun getListItems() : LinkedList<TimerStage> {
    var itemList = LinkedList<TimerStage>()
    var i = 0
    do {
        itemList.add(TimerStage("p$i", "Preparation", 6))
        itemList.add(TimerStage("w$i","Workout", 5))
        itemList.add(TimerStage("r$i","Rest", 4))
        i++
    } while(i < 3)
    return itemList
}