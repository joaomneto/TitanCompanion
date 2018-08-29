package pt.joaomneto.titancompanion.adventure.state.reducer

import net.attilaszabo.redux.Action
import net.attilaszabo.redux.Reducer
import pt.joaomneto.titancompanion.adventure.state.actions.AdventureActions
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import java.util.Properties
import kotlin.math.max
import kotlin.math.min

class MainStateReducer : Reducer<MainState> {
    override fun reduce(state: MainState, action: Action): MainState = when (action) {
        is AdventureActions.IncreaseStamina -> state.copy(
            currentStamina = min(state.currentStamina + action.value, state.initialStamina)
        )
        is AdventureActions.IncreaseSkill -> state.copy(
            currentSkill = java.lang.Math.min(state.currentSkill + action.value, state.initialSkill)
        )
        is AdventureActions.IncreaseLuck -> state.copy(
            currentLuck = java.lang.Math.min(state.currentLuck + action.value, state.initialLuck)
        )
        is AdventureActions.IncreaseProvisions -> state.copy(provisions = state.provisions + action.value)
        is AdventureActions.IncreaseGold -> state.copy(gold = state.gold + action.value)
        is AdventureActions.DecreaseStamina -> state.copy(currentStamina = max(state.currentStamina - action.value, 0))
        is AdventureActions.DecreaseSkill -> state.copy(currentSkill = max(state.currentSkill - action.value, 0))
        is AdventureActions.DecreaseLuck -> state.copy(currentLuck = max(state.currentLuck - action.value, 0))
        is AdventureActions.DecreaseProvisions -> state.copy(provisions = max(state.provisions - action.value, 0))
        is AdventureActions.DecreaseGold -> state.copy(gold = max(state.gold - action.value, 0))
        is AdventureActions.DecreasePotion -> state.copy(
            standardPotionValue = max(state.standardPotionValue - action.value, 0)
        )
        is AdventureActions.ResetStamina -> state.copy(currentStamina = state.initialStamina)
        is AdventureActions.ResetSkill -> state.copy(currentSkill = state.initialSkill)
        is AdventureActions.ResetLuck -> state.copy(currentLuck = state.initialLuck)
        is AdventureActions.ChangeInitialStamina -> state.copy(initialStamina = max(0, action.value))
        is AdventureActions.ChangeInitialSkill -> state.copy(initialSkill = max(0, action.value))
        is AdventureActions.ChangeInitialLuck -> state.copy(initialLuck = java.lang.Math.max(0, action.value))
        is AdventureActions.ChangeGold -> state.copy(gold = java.lang.Math.max(action.value, 0))
        is AdventureActions.AddEquipment -> if (action.value.isNotBlank()) {
            state.copy(equipment = state.equipment.plus(action.value))
        } else state
        is AdventureActions.AddNote -> if (action.value.isNotBlank()) {
            state.copy(notes = state.notes.plus(action.value))
        } else state
        is AdventureActions.RemoveEquipment -> state.copy(equipment = state.equipment.minus(action.value))
        is AdventureActions.RemoveNote -> state.copy(notes = state.notes.minus(action.value))
        else -> state
    }
}
