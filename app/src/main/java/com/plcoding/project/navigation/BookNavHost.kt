package com.plcoding.project.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plcoding.project.AddBookScreen
import com.plcoding.project.AddBookScreenDestination
import com.plcoding.project.BookEvent
import com.plcoding.project.BookScreen
import com.plcoding.project.BookScreenDestination
import com.plcoding.project.BookState
import com.plcoding.project.PageCalculatorScreen
import com.plcoding.project.PageCalculatorScreenDestination

@Composable
fun BookNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    context: Context,
    state: BookState,
    onEvent: (BookEvent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BookScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = BookScreenDestination.route) {
            BookScreen(
                state = state,
                context = context,
                navigateToAddBookScreen = {
                    navController.navigate(AddBookScreenDestination.route)
                },
                navigateToCalculatorScreen = {
                    navController.navigate(PageCalculatorScreenDestination.route)
                },
                onEvent = onEvent
            )
        }
        composable(route = AddBookScreenDestination.route) {
            AddBookScreen(
                navigateBack = {
                    navController.navigate(BookScreenDestination.route)
                }, onEvent = onEvent, state = state
            )
        }
        composable(route = PageCalculatorScreenDestination.route) {
            PageCalculatorScreen(
                navigateBack = {
                    navController.navigate(BookScreenDestination.route)
                }, context
            )
        }
    }
}