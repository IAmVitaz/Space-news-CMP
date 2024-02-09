import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import components.root.RootComponentImpl

fun MainViewController() = ComposeUIViewController {
    val root = remember {
        RootComponentImpl(DefaultComponentContext(LifecycleRegistry()))
    }

    App(root)
}
