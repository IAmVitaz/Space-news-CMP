package org.example.project

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent
import components.root.RootComponentImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            RootComponentImpl(it)
        }

        setContent {
            App(root)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}