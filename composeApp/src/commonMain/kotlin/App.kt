import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import components.root.RootComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.root.RootContent

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        RootContent(component = root)
    }
}