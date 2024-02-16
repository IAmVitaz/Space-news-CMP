import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import org.example.project.ApplicationContextProvider

actual fun openUrlInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    ContextCompat.startActivity(ApplicationContextProvider.context, intent, null)
}
