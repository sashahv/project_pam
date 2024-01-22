package com.plcoding.project

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
                onEvent = onEvent
            )
        }
        composable(route = AddBookScreenDestination.route) {
            AddBookScreen(
                state = state,
                context = context,
                onEvent = onEvent
            )
        }
    }
}