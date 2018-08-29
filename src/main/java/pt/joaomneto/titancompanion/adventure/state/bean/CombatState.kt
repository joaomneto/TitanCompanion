package pt.joaomneto.titancompanion.adventure.state.bean

import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll
import pt.joaomneto.titancompanion.adventure.values.CombatMode
import java.util.Properties

data class CombatState(
    val combatPositions: List<AdventureCombatFragment.Combatant> = emptyList(),
    val combatMode: CombatMode = CombatMode.NORMAL,
    val draw: Boolean = false,
    val luckTest: Boolean = false,
    val hit: Boolean = false,
    val combatStarted: Boolean = false,
    val staminaLoss: Int = 0,
    val handicap: Int = 0,
    val attackDiceRoll: DiceRoll = DiceRoll(-1, -1),
    val combatResult: String = ""
){
    val currentEnemy: AdventureCombatFragment.Combatant
        get() = combatPositions.find { it.isActive }
            ?: throw IllegalStateException("No active enemy combatant found.")
}


