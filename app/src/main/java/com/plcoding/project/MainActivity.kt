package com.plcoding.project

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.plcoding.project.ui.theme.BooksTheme
import com.plcoding.project.ui.theme.LightBlue
import com.plcoding.project.ui.theme.LightOrange
import com.plcoding.project.ui.theme.WarningRed

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            "books.db"
        ).build()
    }

    private val viewModel by viewModels<BookViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BookViewModel(db.dao, applicationContext) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksTheme {
                val state by viewModel.state.collectAsState()
                BookApp(
                    state = state,
                    context = applicationContext,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

@SuppressLint("RtlHardcoded")
fun showToast(context: Context, message: String, bgColor: Color) {
    val toast = Toast(context)

    val textView = TextView(context)
    textView.text = message
    textView.textSize = 16f
    textView.setTextColor(Color.Black.toArgb())

    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.cornerRadius = 16f
    shape.setColor(bgColor.toArgb())
    textView.background = shape

    val paddingInDp = 8
    val scale: Float = context.resources.displayMetrics.density
    val paddingInPx = (paddingInDp * scale + 0.5f).toInt()
    textView.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx)

    if (bgColor == WarningRed) {
        toast.setGravity(Gravity.TOP or Gravity.CENTER, 0, 450)
    } else {
        toast.setGravity(Gravity.TOP or Gravity.RIGHT, 55, 35)
    }

    toast.duration = Toast.LENGTH_LONG

    toast.view = textView

    toast.show()
}