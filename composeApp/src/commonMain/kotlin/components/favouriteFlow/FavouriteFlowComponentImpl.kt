package components.favouriteFlow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import components.details.DetailsComponent
import components.details.DetailsComponentImpl
import components.favourites.FavouritesComponent
import components.favourites.FavouritesComponentImpl
import components.homeFlow.HomeFlowComponent
import components.list.ListComponent
import components.list.ListComponentImpl
import kotlinx.serialization.Serializable
import models.Article

class FavouriteFlowComponentImpl(
    componentContext: ComponentContext
): FavouriteFlowComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, FavouriteFlowComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Favourites,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): FavouriteFlowComponent.Child =
        when (config) {
            is Config.Favourites -> FavouriteFlowComponent.Child.Favourites(favouritesListComponent(childComponentContext))
            is Config.Details -> FavouriteFlowComponent.Child.Details(detailsComponent(config, childComponentContext))
        }

    private fun favouritesListComponent(
        componentContext: ComponentContext
    ): FavouritesComponent =
        FavouritesComponentImpl(
            componentContext = componentContext,
            onListItemClicked = {article ->
                navigation.push(Config.Details(article = article))
            },
        )

    private fun detailsComponent(
        config: Config.Details,
        componentContext: ComponentContext
    ): DetailsComponent =
        DetailsComponentImpl(
            componentContext = componentContext,
            article = config.article,
            onBackButtonClicked = navigation::pop,
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Favourites : Config

        @Serializable
        data class Details(val article: Article) : Config
    }
}
