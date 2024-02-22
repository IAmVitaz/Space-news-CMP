package components.favourites

import com.arkivanov.decompose.value.Value
import models.Article

interface FavouritesComponent {
    val uiState: Value<FavouriteArticlesState>
    fun getFavouriteArticles()
    fun onItemClicked(article: Article)
}
