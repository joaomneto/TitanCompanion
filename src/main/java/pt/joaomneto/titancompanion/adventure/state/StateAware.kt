package pt.joaomneto.titancompanion.adventure.state

import net.attilaszabo.redux.Store
import pt.joaomneto.titancompanion.adventure.state.bean.CombatState
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.adventure.state.bean.State

import java.util.Properties

interface StateAware<STORE, CUSTOM_STATE : State, CUSTOM_COMBAT_STATE> {
    val store: Store<STORE>
    val state: MainState
    val customState: CUSTOM_STATE
    val combatState: CombatState
    val customCombatState: CUSTOM_COMBAT_STATE

    fun generateStoreFromSavegame(properties: Properties): Store<STORE>
    fun loadSavegame(properties: Properties)
    fun toSavegameProperties(): Properties
}
