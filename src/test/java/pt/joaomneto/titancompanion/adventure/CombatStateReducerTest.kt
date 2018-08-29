package pt.joaomneto.titancompanion.adventure

import io.mockk.every
import io.mockk.mockkObject
import net.attilaszabo.redux.implementation.java.BaseStore
import org.junit.Assert
import org.junit.Test
import pt.joaomneto.titancompanion.adventure.twofm.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll
import pt.joaomneto.titancompanion.adventure.state.actions.CombatActions
import pt.joaomneto.titancompanion.adventure.state.bean.CombatState
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.adventure.state.bean.StandardAdventureState
import pt.joaomneto.titancompanion.adventure.state.reducer.AdventureStateReducer
import pt.joaomneto.titancompanion.adventure.values.CombatMode
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.DiceRoller

open class CombatStateReducerTest {

    open val adventure: Adventure<*, *, *>
        get() = TWOFMAdventure(emptyArray())

    val mockMainState = MainState(
        gamebook = FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN,
        name = "name",
        initialSkill = 12,
        currentSkill = 12,
        initialLuck = 12,
        currentLuck = 12,
        initialStamina = 24,
        currentStamina = 24,
        equipment = emptyList(),
        notes = emptyList(),
        gold = 0,
        provisions = 0,
        provisionsValue = 4,
        currentReference = 1,
        standardPotion = 0,
        standardPotionValue = 0
    )

    private fun setupStore(
        combatState: CombatState
    ): BaseStore<StandardAdventureState> {
        return BaseStore.Creator<StandardAdventureState>().create(
            StandardAdventureState(mockMainState, combatState),
            AdventureStateReducer()
        )
    }

    @Test
    fun `when starting combat it returns a state with combat started`() {
        val store = setupStore(combatState = CombatState(combatStarted = false))
        val newState = CombatState(combatStarted = true)
        store.dispatch(CombatActions.StartCombat())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when stopping combat it returns a state without combat started`() {
        val store = setupStore(combatState = CombatState(combatStarted = true))
        val newState = CombatState(combatStarted = false)
        store.dispatch(CombatActions.StopCombat())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when resetting combat stamina loss it returns a state with zero stamina loss`() {
        val store = setupStore(combatState = CombatState(staminaLoss = 8))
        val newState = CombatState(staminaLoss = 0)
        store.dispatch(CombatActions.ResetStaminaLoss())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when clearing combatants it returns a state without combatants`() {
        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(
                    AdventureCombatFragment.Combatant(1, 2, 3, true, "2", true),
                    AdventureCombatFragment.Combatant(2, 3, 1, true, "2", false)
                )
            )
        )
        val newState = CombatState(combatPositions = emptyList())
        store.dispatch(CombatActions.ClearCombatants())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when resetting combat mode loss it returns a state with the default combat mode`() {
        val store = setupStore(combatState = CombatState(combatMode = CombatMode.SEQUENCE))
        val newState = CombatState(combatMode = CombatMode.NORMAL)
        store.dispatch(CombatActions.ResetCombatMode())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when changing combat luck test result it returns a state with the new luck test result`() {
        val store = setupStore(combatState = CombatState(luckTest = false))
        val newState = CombatState(luckTest = true)
        store.dispatch(CombatActions.ChangeCombatLuckTest(true))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when increasing combat stamina loss it returns a state with stamina losse increased`() {
        val store = setupStore(combatState = CombatState(staminaLoss = 0))
        val newState = CombatState(staminaLoss = 1)
        store.dispatch(CombatActions.IncreaseCombatStaminaLoss())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when increasing combat stamina loss with value it returns a state with stamina losse increased`() {
        val store = setupStore(combatState = CombatState(staminaLoss = 0))
        val newState = CombatState(staminaLoss = 5)
        store.dispatch(CombatActions.IncreaseCombatStaminaLoss(5))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when decreasing combat stamina loss it returns a state with stamina losse increased`() {
        val store = setupStore(combatState = CombatState(staminaLoss = 1))
        val newState = CombatState(staminaLoss = 0)
        store.dispatch(CombatActions.DecreaseCombatStaminaLoss())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when decreasing combat stamina loss with value it returns a state with stamina loss decreased`() {
        val store = setupStore(combatState = CombatState(staminaLoss = 5))
        val newState = CombatState(staminaLoss = 0)
        store.dispatch(CombatActions.DecreaseCombatStaminaLoss(5))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when setting the combat result it returns a state with combat result text`() {
        val store = setupStore(combatState = CombatState(combatResult = ""))
        val newState = CombatState(combatResult = "result")
        store.dispatch(CombatActions.CombatResult("result"))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when removing a combatant it returns a state without the combatant`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)
        val c3 = AdventureCombatFragment.Combatant(3, 3, 0, false, "2", false, 0)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2, c3)
            )
        )
        val newState = CombatState(
            combatPositions = listOf(c1, c3.copy(isDefenseOnly = true))
        )
        store.dispatch(CombatActions.RemoveCombatant(c2))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when changing the active a combatant it returns a state with the new active combatant`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)
        val c3 = AdventureCombatFragment.Combatant(3, 3, 0, false, "2", false, 0)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2, c3)
            )
        )
        val newState = CombatState(
            combatPositions = listOf(c1.copy(isActive = false), c2.copy(isActive = true), c3)
        )
        store.dispatch(CombatActions.ChangeActiveCombatant(1))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when modifying a combatant it returns a state with the changed combatant`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)
        val c3 = AdventureCombatFragment.Combatant(3, 3, 0, false, "2", false, 0)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2, c3)
            )
        )
        val newState = CombatState(
            combatPositions = listOf(c1, c2, c3.copy(currentStamina = 0, staminaLoss = 4))
        )
        store.dispatch(
            CombatActions.ModifyCombatant(c3.copy(currentStamina = 0, staminaLoss = 4))

        )
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when setting a combat draw it returns a state with the combat draw enabled`() {
        val store = setupStore(
            combatState = CombatState(
                draw = false
            )
        )
        val newState = CombatState(
            draw = true
        )
        store.dispatch(CombatActions.CombatDraw())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when setting a combat hit it returns a state with the combat hit enabled`() {
        val store = setupStore(
            combatState = CombatState(
                hit = false
            )
        )
        val newState = CombatState(
            hit = true
        )
        store.dispatch(CombatActions.CombatHit())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when changing the combat mode it returns a state with the combat mode altered`() {
        val store = setupStore(
            combatState = CombatState(
                combatMode = CombatMode.NORMAL
            )
        )
        val newState = CombatState(
            combatMode = CombatMode.SEQUENCE
        )
        store.dispatch(CombatActions.CombatMode(CombatMode.SEQUENCE))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when changing the combat luck test it returns a state with the last combat luck result`() {
        val store = setupStore(
            combatState = CombatState(
                luckTest = true
            )
        )
        val newState = CombatState(
            luckTest = false
        )
        store.dispatch(CombatActions.CombatLuckTest(false))
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when changing the combat attack roll it returns a state with the last combat attack roll result`() {
        mockkObject(DiceRoller)

        every {
            DiceRoller.rollD6()
        } returns 3

        val store = setupStore(
            combatState = CombatState(
                attackDiceRoll = DiceRoll(1, 8)
            )
        )
        val newState = CombatState(
            attackDiceRoll = DiceRoll(3, 3)
        )
        store.dispatch(CombatActions.CombatDiceRoll())
        Assert.assertEquals(newState, store.getState().combatState)
    }

    @Test
    fun `when resetting combat attack roll it returns a state with zero stamina loss, combat stopped, no combattants and combat mode set to standard`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)

        val initialState = CombatState(
            staminaLoss = 3,
            combatStarted = true,
            combatPositions = listOf(c1, c2),
            combatMode = CombatMode.SEQUENCE,
            combatResult = ""
        )

        val storeReset = setupStore(
            combatState = initialState
        )

        storeReset.dispatch(CombatActions.ResetCombat())

        val storeSequenceActions = setupStore(
            combatState = initialState
        )

        storeSequenceActions.dispatch(CombatActions.ResetStaminaLoss())
        storeSequenceActions.dispatch(CombatActions.StopCombat())
        storeSequenceActions.dispatch(CombatActions.ClearCombatants())
        storeSequenceActions.dispatch(CombatActions.ResetCombatMode())

        Assert.assertEquals(
            storeSequenceActions.getState().combatState, storeSequenceActions.getState().combatState
        )
    }

    @Test
    fun `when advancing combat it returns a state with the following combatant as the active enemy`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, true, "2", true, 0)
        val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", false, 0)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2),
                combatMode = CombatMode.SEQUENCE
            )
        )

        val newState = CombatState(
            combatPositions = listOf(
                c1.copy(isActive = false, isDefenseOnly = false),
                c2.copy(isActive = true, isDefenseOnly = true)
            ),
            combatMode = CombatMode.SEQUENCE
        )

        val newStateAdvanced = CombatState(
            combatPositions = listOf(
                c1.copy(isDefenseOnly = false),
                c2.copy(isDefenseOnly = true)
            ),
            combatMode = CombatMode.SEQUENCE
        )

        store.dispatch(CombatActions.AdvanceCombat())

        Assert.assertEquals(
            newState,
            store.getState().combatState
        )

        store.dispatch(CombatActions.AdvanceCombat())

        Assert.assertEquals(
            newStateAdvanced,
            store.getState().combatState
        )
    }

    @Test
    fun `when removing combatant and advancing combat it returns a state without the combatand and the following combatant as the active enemy`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)
        val c3 = AdventureCombatFragment.Combatant(2, 2, 0, true, "2", false, 0)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2, c3),
                combatMode = CombatMode.SEQUENCE
            )
        )

        val newState = CombatState(
            combatPositions = listOf(c1, c3.copy(isActive = true, isDefenseOnly = true)),
            combatMode = CombatMode.SEQUENCE
        )

        store.dispatch(CombatActions.RemoveCombatantAndAdvanceCombat())
        Assert.assertEquals(
            newState,
            store.getState().combatState
        )
    }

    @Test
    fun `when adding a combatant it returns a state with the new enemy and in the last position`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)
        val c3 = AdventureCombatFragment.Combatant(2, 2, 0, true, "2", false, 0)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2)
            )
        )

        val newState = CombatState(
            combatPositions = listOf(c1, c2, c3)
        )

        store.dispatch(CombatActions.AddCombatant(c3))

        Assert.assertEquals(
            newState,
            store.getState().combatState
        )
    }

    @Test
    fun `when decreasing a combatant stamina it returns a state with the enemy with updated stamina`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2)
            )
        )

        val newState = CombatState(
            combatPositions = listOf(c1, c2.copy(currentStamina = 2))
        )

        store.dispatch(CombatActions.DecreaseCombatantStamina(c2))

        Assert.assertEquals(
            newState,
            store.getState().combatState
        )
    }

    @Test
    fun `when increasing a combatant stamina it returns a state with the enemy with updated stamina`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2)
            )
        )

        val newState = CombatState(
            combatPositions = listOf(c1, c2.copy(currentStamina = 4))
        )

        store.dispatch(CombatActions.IncreaseCombatantStamina(c2))

        Assert.assertEquals(
            newState,
            store.getState().combatState
        )
    }

    @Test
    fun `when decreasing a combatant skill it returns a state with the enemy with updated skill`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2)
            )
        )

        val newState = CombatState(
            combatPositions = listOf(c1, c2.copy(currentSkill = 3))
        )

        store.dispatch(CombatActions.DecreaseCombatantSkill(c2))

        Assert.assertEquals(
            newState,
            store.getState().combatState
        )
    }

    @Test
    fun `when increasing a combatant skill it returns a state with the enemy with updated skill`() {
        val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
        val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

        val store = setupStore(
            combatState = CombatState(
                combatPositions = listOf(c1, c2)
            )
        )

        val newState = CombatState(
            combatPositions = listOf(c1, c2.copy(currentSkill = 5))
        )

        store.dispatch(CombatActions.IncreaseCombatantSkill(c2))

        Assert.assertEquals(
            newState,
            store.getState().combatState
        )
    }
}
