package pt.joaomneto.titancompanion.adventure.aod

import net.attilaszabo.redux.Action
import net.attilaszabo.redux.Reducer
import pt.joaomneto.titancompanion.adventure.state.reducer.AdventureReducer

class AODAdventureStateReducer : AdventureReducer<AODAdventureState>() {

    private val aodMainStateReducer = AODMainStateReducer()
    private val aodCombatStateReducer = AODCombatStateReducer()

    override fun reduce(
        state: AODAdventureState,
        action: Action
    ) = AODAdventureState(
        mainStateReducer.reduce(state.mainState, action),
        combatStateReducer.reduce(state.combatState, action),
        aodMainStateReducer.reduce(state.aodState, action),
        aodCombatStateReducer.reduce(state.aodCombatState, action)
    )
}

class AODMainStateReducer : Reducer<AODMainState> {

    override fun reduce(state: AODMainState, action: Action) = when (action) {
        is AODMainStateActions.Recalculate -> recalculate(state, action.skirmishArmy)
        is AODMainStateActions.ResetDivisionsToInitialValues -> state.copy(soldiers = Army(
            state.soldiers.divisions.map { it.resetToInitialValues() })
        )
        is AODMainStateActions.ModifyDivision -> state.copy(soldiers = Army(
            state.soldiers.divisions.map {
                if (it.category == action.division.category)
                    action.division
                else
                    it
            })
        )
        else -> state
    }

    fun recalculate(state: AODMainState, skirmishArmy: Map<String, Int>): AODMainState =
        state.copy(
            soldiers =
            Army(divisions = state.soldiers.divisions
                .map {
                    val qt = skirmishArmy[it.category]
                    when (it) {
                        is CustomSoldiersDivision -> {
                            it.copy(
                                quantity = it.quantity + (qt ?: 0)
                            )
                        }
                        is StandardSoldiersDivision ->
                            it.copy(
                                quantity = it.quantity + (qt ?: 0)
                            )
                        else -> throw IllegalStateException("Not Possible")
                    }
                })
        )
}

object AODMainStateActions {
    data class Recalculate(val skirmishArmy: Map<String, Int>) : Action
    class ResetDivisionsToInitialValues : Action
    data class ModifyDivision(val division: SoldiersDivision) : Action
}

class AODCombatStateReducer : Reducer<AODCombatState> {

    override fun reduce(state: AODCombatState, action: Action) = when (action) {
        is AODCombatStateActions.DecreaseEnemyForces -> state.copy(
            enemyForces = Math.max(
                0,
                state.enemyForces - action.value
            )
        )
        is AODCombatStateActions.IncreaseEnemyForces -> state.copy(enemyForces = state.enemyForces + 5)
        is AODCombatStateActions.ChangeBattleState -> state.copy(battleState = action.state)
        is AODCombatStateActions.ChangeBattleBalance -> state.copy(battleBalance = action.balance)
        is AODCombatStateActions.ChangeTargetLosses -> state.copy(targetLosses = action.value)
        is AODCombatStateActions.IncrementTurnArmyLosses -> state.copy(turnArmyLosses = state.turnArmyLosses + 5)
        is AODCombatStateActions.ChangeTurnArmyLosses -> state.copy(turnArmyLosses = action.value)
        is AODCombatStateActions.ClearSkirmishArmy -> state.copy(skirmishArmy = emptyMap())
        is AODCombatStateActions.ResetEnemyForces -> state.copy(enemyForces = 0)
        is AODCombatStateActions.SetSkirmishDivision -> state.copy(skirmishArmy = state.skirmishArmy.plus(action.division.category to action.quantity))
        else -> state
    }
}

object AODCombatStateActions {
    class IncreaseEnemyForces : Action
    data class DecreaseEnemyForces(val value: Int = 5) : Action
    data class ChangeBattleState(val state: AODAdventureBattleState) : Action
    data class ChangeBattleBalance(val balance: AODAdventureBattleBalance) : Action
    data class ChangeTargetLosses(val value: Int) : Action
    data class ChangeTurnArmyLosses(val value: Int) : Action
    data class SetSkirmishDivision(val division: SoldiersDivision, val quantity: Int) : Action
    class ClearSkirmishArmy : Action
    class ResetEnemyForces : Action
    class IncrementTurnArmyLosses : Action
}




