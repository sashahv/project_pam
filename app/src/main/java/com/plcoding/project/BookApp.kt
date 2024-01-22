package com.plcoding.project

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.plcoding.project.navigation.BookNavHost

@Composable
fun BookApp(
    navController: NavHostController = rememberNavController(),
    state: BookState,
    context: Context,
    onEvent: (BookEvent) -> Unit
) {
    BookNavHost(navController = navController, state = state, context = context, onEvent = onEvent)
}