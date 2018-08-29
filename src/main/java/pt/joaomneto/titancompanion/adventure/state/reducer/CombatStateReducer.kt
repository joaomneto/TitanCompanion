package pt.joaomneto.titancompanion.adventure.state.reducer

import net.attilaszabo.redux.Action
import net.attilaszabo.redux.Reducer
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.state.actions.CombatActions
import pt.joaomneto.titancompanion.adventure.state.bean.CombatState
import pt.joaomneto.titancompanion.adventure.values.CombatMode
import pt.joaomneto.titancompanion.util.DiceRoller

class CombatStateReducer : Reducer<CombatState> {
    override fun reduce(state: CombatState, action: Action): CombatState = when (action) {
        is CombatActions.StartCombat -> state.copy(combatStarted = true)
        is CombatActions.StopCombat -> stopCombat(state)
        is CombatActions.ResetStaminaLoss -> resetStaminaLoss(state)
        is CombatActions.ClearCombatants -> clearCombatants(state)
        is CombatActions.ResetCombatMode -> resetCombatMode(state)
        is CombatActions.ChangeCombatLuckTest -> state.copy(luckTest = action.value)
        is CombatActions.DecreaseCombatStaminaLoss -> state.copy(staminaLoss = state.staminaLoss - action.value)
        is CombatActions.IncreaseCombatStaminaLoss -> state.copy(staminaLoss = state.staminaLoss + action.value)
        is CombatActions.RemoveCombatant -> removeCombatant(state, action.combatant)
        is CombatActions.ChangeActiveCombatant -> changeActiveCombatant(state, action.activeIndex)
        is CombatActions.ModifyCombatant -> modifyCombatant(state, action.combatant)
        is CombatActions.CombatDraw -> state.copy(draw = action.draw)
        is CombatActions.CombatHit -> state.copy(hit = action.hit)
        is CombatActions.CombatMode -> state.copy(combatMode = action.mode)
        is CombatActions.CombatResult -> combatResult(state, action.result)
        is CombatActions.CombatLuckTest -> state.copy(luckTest = action.luckTest)
        is CombatActions.CombatDiceRoll -> state.copy(attackDiceRoll = DiceRoller.roll2D6())
        is CombatActions.ResetCombat -> resetCombat(state)
        is CombatActions.RemoveCombatantAndAdvanceCombat -> removeCombatantAndAdvanceCombat(state)
        is CombatActions.AdvanceCombat -> advanceCombat(state)
        is CombatActions.AddCombatant -> addCombatant(state, action.combatant)
        is CombatActions.DecreaseCombatantStamina -> decreaseCombatantStamina(state, action.combatant)
        is CombatActions.IncreaseCombatantStamina -> increaseCombatantStamina(state, action.combatant)
        is CombatActions.DecreaseCombatantSkill -> decreaseCombatantSkill(state, action.combatant)
        is CombatActions.IncreaseCombatantSkill -> increaseCombatantSkill(state, action.combatant)
        else -> state
    }

    fun resetStaminaLoss(state: CombatState) = state.copy(staminaLoss = 0)

    fun stopCombat(state: CombatState) = state.copy(combatStarted = false)

    fun clearCombatants(state: CombatState) = state.copy(
        combatPositions = emptyList()
    )

    fun resetCombatMode(state: CombatState) = state.copy(combatMode = CombatMode.NORMAL)

    fun combatResult(state: CombatState, result: String) = state.copy(combatResult = result)

    fun resetCombat(state: CombatState): CombatState =
        resetStaminaLoss(
            stopCombat(
                clearCombatants(
                    resetCombatMode(
                        combatResult(state, "")
                    )
                )
            )
        )

    fun removeCombatant(state: CombatState, combatant: AdventureCombatFragment.Combatant): CombatState {
        var newState = state.copy(
            combatPositions = state.combatPositions.filter { it.id != combatant.id })

        newState.combatPositions.forEachIndexed { index, combatant ->
            newState = modifyCombatant(newState, combatant.copy(isDefenseOnly = index != 0))
        }

        return newState
    }

    fun changeActiveCombatant(state: CombatState, activeIndex: Int) =
        state.copy(
            combatPositions = state.combatPositions
                .mapIndexed { index,
                              combatant ->
                    combatant.copy(isActive = index == activeIndex)
                })

    fun modifyCombatant(state: CombatState, combatant: AdventureCombatFragment.Combatant) =
        state.copy(
            combatPositions = state.combatPositions
                .map {
                    if (it.id == combatant.id)
                        combatant
                    else
                        it
                })

    fun removeCombatantAndAdvanceCombat(state: CombatState): CombatState {

        val enemyToRemove = state.currentEnemy

        val newState = advanceCombat(state)
        return removeCombatant(newState, enemyToRemove)
    }

    fun addCombatant(state: CombatState, combatant: AdventureCombatFragment.Combatant) = state.copy(
        combatPositions = state.combatPositions.plus(combatant)
    )

    fun advanceCombat(
        state: CombatState
    ): CombatState {

        var newState = state

        val previousCombatant = newState.currentEnemy
        if (newState.combatPositions.isNotEmpty()) {
            val newCombatantIndex: Int
            newState = if (previousCombatant == newState.combatPositions.last()) {
                changeActiveCombatant(newState, 0)
            } else {
                newCombatantIndex = newState.combatPositions.indexOf(previousCombatant) + 1
                changeActiveCombatant(newState, newCombatantIndex)
            }
            newState.combatPositions.forEachIndexed { index, combatant ->
                newState = modifyCombatant(newState, combatant.copy(isDefenseOnly = index != 0))
            }
        } else {
            newState = resetCombat(newState)
        }

        return newState
    }

    open fun decreaseCombatantStamina(state: CombatState, combatant: AdventureCombatFragment.Combatant) = state.copy(
        combatPositions = state.combatPositions.map {
            if (it.id == combatant.id)
                it.copy(currentStamina = combatant.currentStamina - 1)
            else it
        }
    )

    open fun increaseCombatantStamina(state: CombatState, combatant: AdventureCombatFragment.Combatant) = state.copy(
        combatPositions = state.combatPositions.map {
            if (it.id == combatant.id)
                it.copy(currentStamina = combatant.currentStamina + 1)
            else it
        }
    )

    open fun decreaseCombatantSkill(state: CombatState, combatant: AdventureCombatFragment.Combatant) = state.copy(
        combatPositions = state.combatPositions.map {
            if (it.id == combatant.id)
                it.copy(currentSkill = combatant.currentSkill - 1)
            else it
        }
    )

    open fun increaseCombatantSkill(state: CombatState, combatant: AdventureCombatFragment.Combatant) = state.copy(
        combatPositions = state.combatPositions.map {
            if (it.id == combatant.id)
                it.copy(currentSkill = combatant.currentSkill + 1)
            else it
        }
    )
}
