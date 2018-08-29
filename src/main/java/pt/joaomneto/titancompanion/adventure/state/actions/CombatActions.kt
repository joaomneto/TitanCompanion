package pt.joaomneto.titancompanion.adventure.state.actions

import net.attilaszabo.redux.Action
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

object CombatActions {
    class StartCombat: Action
    class StopCombat: Action
    class ResetStaminaLoss: Action
    class ClearCombatants: Action
    class ResetCombatMode: Action
    data class ChangeCombatLuckTest(val value: Boolean): Action
    data class DecreaseCombatStaminaLoss(val value: Int = 1): Action
    data class IncreaseCombatStaminaLoss(val value: Int = 1): Action
    data class RemoveCombatant(val combatant: AdventureCombatFragment.Combatant): Action
    data class ChangeActiveCombatant(val activeIndex: Int): Action
    data class ModifyCombatant(val combatant: AdventureCombatFragment.Combatant): Action
    data class CombatDraw(val draw: Boolean = true): Action
    data class CombatHit(val hit: Boolean = true): Action
    data class CombatMode(val mode: pt.joaomneto.titancompanion.adventure.values.CombatMode): Action
    data class CombatResult(val result: String): Action
    data class CombatLuckTest(val luckTest: Boolean): Action
    class CombatDiceRoll: Action
    class ResetCombat: Action
    class RemoveCombatantAndAdvanceCombat: Action
    class AdvanceCombat: Action
    data class AddCombatant(val combatant: AdventureCombatFragment.Combatant): Action
    data class DecreaseCombatantStamina(val combatant: AdventureCombatFragment.Combatant): Action
    data class IncreaseCombatantStamina(val combatant: AdventureCombatFragment.Combatant): Action
    data class DecreaseCombatantSkill(val combatant: AdventureCombatFragment.Combatant): Action
    data class IncreaseCombatantSkill(val combatant: AdventureCombatFragment.Combatant): Action
}

