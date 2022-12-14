package com.example.cupcake

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cupcake.database.Timer
import com.example.cupcake.ui.*
import kotlinx.coroutines.flow.MutableStateFlow

enum class TimerScreen() {
    Start,
    Create,
    Edit,
    Timer,
    Settings
}

@Composable
fun TimerAppBar(
    canNavigateBack: Boolean,
    navigateUp:() -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { "Interval Timer" },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun TimerApp(modifier: Modifier = Modifier, viewModel: TimerViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    //val backStackEntry by navController.currentBackStackEntryAsState()
    val toastContext = LocalContext.current
    viewModel.getTimers()

    Scaffold(
        topBar = {
            TimerAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    ) { innerPadding ->
        val timersState by viewModel.timersState.collectAsState()
        val timerState by viewModel.timerState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = TimerScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = TimerScreen.Start.name) {
                StartListScreen(
                    timers = timersState,
                    onAddButtonClicked = {
                        navController.navigate(TimerScreen.Create.name)
                    },
                    onEditButtonClicked = {
                        navController.navigate("${TimerScreen.Edit.name}/$it")
                    },
                    onDeleteButtonClicked = {
                        viewModel.deleteTimer(it)
                        Toast.makeText(toastContext, "Timer deleted successfully!", Toast.LENGTH_SHORT).show()
                    },
                    onViewButtonClicked = {
                        navController.navigate("${TimerScreen.Timer.name}/$it")
                    },
                    onSettingsButtonClicked = {
                        navController.navigate(TimerScreen.Settings.name)
                    }
                )
            }
            composable(route = TimerScreen.Create.name) {
                CreateTimerScreen(
                    onCreateButtonClicked = { timer: Timer ->
                        viewModel.addTimer(timer)
                        navController.navigate(TimerScreen.Start.name)
                    },
                    onCreateAndRunButtonClicked = { timer: Timer ->
                        viewModel.addTimer(timer)
                        navController.navigate(TimerScreen.Timer.name)
                    }
                )
            }

            composable(route = "${TimerScreen.Edit.name}/{timerId}",
                arguments = listOf(navArgument("timerId") {type = NavType.IntType})
            ) {
                val id = it.arguments?.getInt("timerId") ?: 0
                viewModel.getTimer(id)
                EditTimerScreen(timerState = MutableStateFlow(timerState),
                    onSaveButtonClicked = {
                        viewModel.updateTimer(it)
                        navController.navigate("${TimerScreen.Timer.name}/$id")
                    })
            }

            composable(route = "${TimerScreen.Timer.name}/{timerId}",
                    arguments = listOf(navArgument("timerId") {type = NavType.IntType})
            ) {
                val id = it.arguments?.getInt("timerId") ?: 0
                viewModel.getTimer(id)
                ActiveTimerScreen(activeTimer = timerState)
            }
            composable(route = TimerScreen.Settings.name) {
                SettingsScreen(
                    onDeleteAllClicked = {
                        viewModel.deleteAllTimers()
                        Toast.makeText(toastContext, "Deleted all successfully!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}