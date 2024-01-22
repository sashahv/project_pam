package com.plcoding.project

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
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
fun showToast(context: Context, message: String, bgColor: Color, empty: Boolean) {
    // Check if there is an active toast, cancel it using Handler
    currentToast?.let {
        Handler(Looper.getMainLooper()).postDelayed({
            it.cancel()
        }, 0)
    }
    if(empty){
        return
    }

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

    toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 400)

    toast.duration = Toast.LENGTH_LONG

    toast.view = textView

    // Set the current toast to the new one
    currentToast = toast

    toast.show()
}

// Declare a global variable to keep track of the current active toast
private var currentToast: Toast? = null
