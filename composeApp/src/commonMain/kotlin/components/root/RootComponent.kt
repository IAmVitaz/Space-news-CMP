package components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import components.favourites.FavouritesComponent
import components.homeFlow.HomeFlowComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onHomeTabClicked()
    fun onFavouritesTabClicked()

    sealed class Child {
        class HomeFlowChild(val component: HomeFlowComponent) : Child()
        class FavouritesChild(val component: FavouritesComponent) : Child()
    }
}
