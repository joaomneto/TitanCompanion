package pt.joaomneto.titancompanion.adventure.state.reducer

import net.attilaszabo.redux.Action
import pt.joaomneto.titancompanion.adventure.state.bean.StandardAdventureState

open class AdventureStateReducer : AdventureReducer<StandardAdventureState>() {

    override fun reduce(
        state: StandardAdventureState,
        action: Action
    ) = StandardAdventureState(
        mainStateReducer.reduce(state.mainState, action),
        combatStateReducer.reduce(state.combatState, action)
    )
}
