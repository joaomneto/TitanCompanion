package pt.joaomneto.titancompanion.adventure.state.reducer

import net.attilaszabo.redux.Reducer

abstract class AdventureReducer<STATE>: Reducer<STATE> {
    val mainStateReducer = MainStateReducer()
    val combatStateReducer = CombatStateReducer()
}
