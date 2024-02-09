import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import components.root.RootComponentImpl

fun main() {
    val lifecycle = LifecycleRegistry()

    // Always create the root component outside Compose on the UI thread
    val root =
        runOnUiThread {
            RootComponentImpl(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
            )
        }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "New App"
        ) {
            App(root)
        }
    }

}

//@Preview
//@Composable
//fun AppDesktopPreview() {
//    App()
//}