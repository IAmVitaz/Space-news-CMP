package components.favouriteFlow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import components.details.DetailsComponent
import components.favourites.FavouritesComponent
import components.list.ListComponent

interface FavouriteFlowComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Favourites(val component: FavouritesComponent) : Child()
        class Details(val component: DetailsComponent) : Child()
    }
}
