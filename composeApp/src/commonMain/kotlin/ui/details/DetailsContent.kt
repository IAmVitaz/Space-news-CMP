package ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import components.details.DetailsComponent
import openUrlInBrowser
import ui.list.ArticleImage

@Composable
fun DetailsContent(
    component: DetailsComponent,
    modifier: Modifier = Modifier
) {
    val uiState by component.uiState.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Space news") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            component.onBackClicked()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                )
        },
    ) {
        AnimatedVisibility(visible = uiState.article != null) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 5.dp)
            ) {
                ArticleImage(
                    uiState.article!!,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 5.dp)
                )
                if (uiState.article != null) {
                    Text(
                        uiState.article!!.title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        uiState.article!!.summary,
                        fontSize = 18.sp
                    )
                    Text(
                        "Read more...",
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .clickable {
                            openUrlInBrowser(url = uiState.article!!.url)
                        },
                        fontSize = 18.sp,
                        color = Color.Blue,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}