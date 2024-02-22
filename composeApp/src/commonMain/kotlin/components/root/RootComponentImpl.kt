package components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import components.favouriteFlow.FavouriteFlowComponent
import components.favouriteFlow.FavouriteFlowComponentImpl
import components.favourites.FavouritesComponentImpl
import components.homeFlow.HomeFlowComponentImpl
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.HomeFlow,
            handleBackButton = true,
            childFactory = ::child,
        )

    override fun onHomeTabClicked() {
        navigation.bringToFront(Config.HomeFlow)
    }

    override fun onFavouritesTabClicked() {
        navigation.bringToFront(Config.FavouritesFlow)
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.HomeFlow -> RootComponent.Child.HomeFlowChild(HomeFlowComponentImpl(
                componentContext = componentContext
            ))
            is Config.FavouritesFlow -> RootComponent.Child.FavouritesFlowChild(FavouriteFlowComponentImpl(
                componentContext = componentContext,
            ))
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object HomeFlow : Config

        @Serializable
        data object FavouritesFlow : Config
    }
}
