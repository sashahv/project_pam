package com.plcoding.project

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plcoding.project.navigation.NavigationDestination

object PageCalculatorScreenDestination : NavigationDestination {
    override val route = "page_calculator"
    override val titleRes = R.string.page_calculator
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PageCalculatorScreen(
    navigateBack: () -> Unit
) {
    var pagesPerDay by remember { mutableStateOf(0) }
    var totalPages by remember { mutableStateOf(0) }
    var daysNeeded by remember { mutableStateOf(0) }

    var snackbarVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.page_calculator), color = Color.Black) },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,
                            tint = Color.Black)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.calculator_tip),
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = pagesPerDay.toString(),
                    onValueChange = {
                        pagesPerDay = it.toIntOrNull() ?: 0
                    },
                    label = { Text(text = stringResource(R.string.pages_per_day), color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black)
                )
                TextField(
                    value = totalPages.toString(),
                    onValueChange = {
                        totalPages = it.toIntOrNull() ?: 0
                    },
                    label = { Text(text = stringResource(R.string.total_pages), color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        daysNeeded = if (pagesPerDay > 0) totalPages / pagesPerDay else 0
                        snackbarVisible = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.calculate), color = Color.Black)
                }

                if (snackbarVisible) {
                    Snackbar(
                        action = {
                            Button(onClick = { snackbarVisible = false }) {
                                Text(text = "OK")
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "${stringResource(R.string.days_needed)} $daysNeeded")
                    }
                }
            }
        }
    )
}
