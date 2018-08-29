package pt.joaomneto.titancompanion.adventure.aod

import pt.joaomneto.titancompanion.adventure.aod.AODCombatState
import pt.joaomneto.titancompanion.adventure.aod.AODMainState
import pt.joaomneto.titancompanion.adventure.state.bean.AdventureState
import pt.joaomneto.titancompanion.adventure.state.bean.CombatState
import pt.joaomneto.titancompanion.adventure.state.bean.MainState

data class AODAdventureState(
    override val mainState: MainState,
    override val combatState: CombatState = CombatState(),
    val aodState: AODMainState,
    val aodCombatState: AODCombatState = AODCombatState()
): AdventureState



