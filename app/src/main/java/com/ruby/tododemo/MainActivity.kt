package com.ruby.tododemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.ruby.tododemo.ui.navigation.Route
import com.ruby.tododemo.ui.screens.AddEditTaskScreen
import com.ruby.tododemo.ui.screens.MainScreen
import com.ruby.tododemo.ui.theme.ToDoDemoTheme
import com.ruby.tododemo.ui.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoDemoTheme {
                val app = application as TodoApplication
                val todoViewModel: TodoViewModel = viewModel(
                    factory = TodoViewModel.Factory(app.repository)
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoNavHost(viewModel = todoViewModel)
                }
            }
        }
    }
}

@Composable
fun TodoNavHost(viewModel: TodoViewModel) {
    val backStack = rememberNavBackStack(Route.Main as NavKey)

    val entryProvider = entryProvider<NavKey> {
        entry<Route.Main> {
            MainScreen(
                viewModel = viewModel,
                onAddTask = { backStack.add(Route.AddEditTask()) },
                onEditTask = { taskId -> backStack.add(Route.AddEditTask(taskId)) }
            )
        }
        entry<Route.AddEditTask> { key ->
            AddEditTaskScreen(
                taskId = key.taskId,
                viewModel = viewModel,
                onBack = { backStack.removeAt(backStack.size - 1) }
            )
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeAt(backStack.size - 1) },
        entryProvider = entryProvider
    )
}
