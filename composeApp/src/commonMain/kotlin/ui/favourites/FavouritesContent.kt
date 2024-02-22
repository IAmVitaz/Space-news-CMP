package ui.favourites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import components.favourites.FavouriteArticlesState
import components.favourites.FavouritesComponent
import models.Article
import ui.list.ArticleCard

@Composable
fun FavouritesContent(
    component: FavouritesComponent,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.subscribeAsState()

    LaunchedEffect(component) {
        if (uiState.articles.isEmpty()) {
            component.getFavouriteArticles()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Favourite news") },
            )
        },
    ) {
        NewsList(
            uiState = uiState,
            onNewSelected = {
                component.onItemClicked(it)
            },
            onEndOfListReach =  {}
        )
    }
}

@Composable
fun NewsList(
    uiState: FavouriteArticlesState,
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
