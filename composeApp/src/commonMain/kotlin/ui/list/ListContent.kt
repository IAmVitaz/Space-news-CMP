package ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import components.list.ArticlesState
import components.list.ListComponent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import models.Article


@Composable
internal fun ListContent(
    component: ListComponent,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.subscribeAsState()

    LaunchedEffect(component) {
        if (uiState.articles.isEmpty()) {
            component.loadMoreArticles()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Space news") },
            )
        },
    ) {
        NewsList(
            uiState = uiState,
            onNewSelected = {
                component.onItemClicked(it)
            },
            onEndOfListReach =  {
                component.loadMoreArticles()
            }
        )
    }
}

@Composable
fun NewsList(
    uiState: ArticlesState,
    onNewSelected: (Article) -> Unit,
    onEndOfListReach: () -> Unit
) {
    AnimatedVisibility(visible = uiState.articles.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            userScrollEnabled = true
        ) {

            items(uiState.articles) { article ->
                ArticleCard(article, onArticleSelect = onNewSelected)
            }
            item {
                LaunchedEffect(true) {
                    onEndOfListReach()
                }
            }
        }
    }
}

@Composable
fun ArticleCard(
    article: Article,
    onArticleSelect: (Article) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { onArticleSelect(article) }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxHeight()
        ) {
            ArticleImage(
                article = article,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 5.dp)
            )
            val density = LocalDensity.current.density
            var padding by remember { mutableStateOf(0.dp) }
            Text(
                text = article.title,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                maxLines = 3,
                onTextLayout = {
                    val lineCount = it.lineCount
                    val height = (it.size.height / density).dp
                    padding = when (lineCount) {
                        3 -> 0.dp
                        2 -> height / 2
                        1 -> height * 2
                        else -> {height}
                    }
                },
                modifier = Modifier
                    .padding(bottom = padding)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun ArticleImage(
    article: Article,
    modifier: Modifier = Modifier
) {
    KamelImage(
        resource = asyncPainterResource(article.imageUrl),
        contentDescription = "${article.title} id ${article.id}",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.0f)
    )
}
