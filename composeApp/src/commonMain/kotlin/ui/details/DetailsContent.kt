package ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import components.details.DetailsComponent
import ui.list.ArticleImage

@Composable
fun DetailsContent(component: DetailsComponent) {
    val uiState by component.uiState.subscribeAsState()

    AnimatedVisibility(visible = uiState.article != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            ArticleImage(uiState.article!!)
            if (uiState.article != null) {
                Row {
                    Text(uiState.article!!.title)
                }
                Row {
                    Text(uiState.article!!.summary)
                }
                Button(onClick = {
                    component.onBackClicked()
                }) {
                    Text("Go back")
                }

            }
        }
    }
}