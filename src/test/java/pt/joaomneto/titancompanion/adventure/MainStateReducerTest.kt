package pt.joaomneto.titancompanion.adventure

import net.attilaszabo.redux.implementation.java.BaseStore
import org.junit.Assert
import org.junit.Test
import pt.joaomneto.titancompanion.adventure.twofm.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.state.actions.AdventureActions
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.adventure.state.bean.StandardAdventureState
import pt.joaomneto.titancompanion.adventure.state.reducer.AdventureStateReducer
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

open class MainStateReducerTest {

    open val adventure: Adventure<*, *, *>
        get() = TWOFMAdventure(emptyArray())

    private class MainStateWrapper(
        gamebook: FightingFantasyGamebook = FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN,
        name: String = "name",
        initialSkill: Int = 12,
        currentSkill: Int = 12,
        initialLuck: Int = 12,
        currentLuck: Int = 12,
        initialStamina: Int = 24,
        currentStamina: Int = 24,
        equipment: List<String> = emptyList(),
        notes: List<String> = emptyList(),
        gold: Int = 0,
        provisions: Int = 0,
        provisionsValue: Int = 4,
        currentReference: Int = 1,
        standardPotion: Int = 0,
        standardPotionValue: Int = 0
    ) {
        val mainState = MainState(
            gamebook = gamebook,
            name = name,
            initialSkill = initialSkill,
            currentSkill = currentSkill,
            initialLuck = initialLuck,
            currentLuck = currentLuck,
            initialStamina = initialStamina,
            currentStamina = currentStamina,
            equipment = equipment,
            notes = notes,
            gold = gold,
            provisions = provisions,
            provisionsValue = provisionsValue,
            currentReference = currentReference,
            standardPotion = standardPotion,
            standardPotionValue = standardPotionValue
        )
    }

    private fun setupStore(mainState: MainStateWrapper): BaseStore<StandardAdventureState> {
        return BaseStore.Creator<StandardAdventureState>().create(
            StandardAdventureState(mainState.mainState),
            AdventureStateReducer()
        )
    }

    @Test
    fun `when increasing stamina it returns a state with the stamina incremented`() {
        val store = setupStore(
            MainStateWrapper(
                currentStamina = 5,
                initialStamina = 6
            )
        )
        val newState = MainStateWrapper(
            currentStamina = 6,
            initialStamina = 6
        )
        store.dispatch(AdventureActions.IncreaseStamina())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing stamina with value it returns a state with the stamina incremented`() {
        val store = setupStore(
            MainStateWrapper(
                currentStamina = 5,
                initialStamina = 10
            )
        )
        val newState = MainStateWrapper(
            currentStamina = 10,
            initialStamina = 10
        )
        store.dispatch(AdventureActions.IncreaseStamina(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing increasing stamina over the initial value`() {
        val store = setupStore(
            MainStateWrapper(
                currentStamina = 5,
                initialStamina = 5
            )
        )
        val newState = MainStateWrapper(
            currentStamina = 5,
            initialStamina = 5
        )
        store.dispatch(AdventureActions.IncreaseStamina())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing stamina it returns a state with the stamina decremented`() {
        val store = setupStore(MainStateWrapper(currentStamina = 5))
        val newState = MainStateWrapper(currentStamina = 4)
        store.dispatch(AdventureActions.DecreaseStamina())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing stamina with value it returns a state with the stamina decremented`() {
        val store = setupStore(MainStateWrapper(currentStamina = 5))
        val newState = MainStateWrapper(currentStamina = 0)
        store.dispatch(AdventureActions.DecreaseStamina(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing decreasing stamina under zero`() {
        val store = setupStore(MainStateWrapper(currentStamina = 0))
        val newState = MainStateWrapper(currentStamina = 0)
        store.dispatch(AdventureActions.DecreaseStamina())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when changing initial stamina it returns a state with the initial stamina with the new value`() {
        val store = setupStore(MainStateWrapper(initialStamina = 15))
        val newState = MainStateWrapper(initialStamina = 5)
        store.dispatch(AdventureActions.ChangeInitialStamina(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when setting the initial stamina value under zero it sets to zero`() {
        val store = setupStore(MainStateWrapper(initialStamina = 0))
        val newState = MainStateWrapper(initialStamina = 0)
        store.dispatch(AdventureActions.ChangeInitialStamina(-5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing skill it returns a state with the skill incremented`() {
        val store = setupStore(
            MainStateWrapper(
                currentSkill = 5,
                initialSkill = 6
            )
        )
        val newState = MainStateWrapper(
            currentSkill = 6,
            initialSkill = 6
        )
        store.dispatch(AdventureActions.IncreaseSkill())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing skill with value it returns a state with the skill incremented`() {
        val store = setupStore(
            MainStateWrapper(
                currentSkill = 5,
                initialSkill = 10
            )
        )
        val newState = MainStateWrapper(
            currentSkill = 10,
            initialSkill = 10
        )
        store.dispatch(AdventureActions.IncreaseSkill(10))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing increasing skill over the initial value`() {
        val store = setupStore(
            MainStateWrapper(
                currentSkill = 5,
                initialSkill = 5
            )
        )
        val newState = MainStateWrapper(
            currentSkill = 5,
            initialSkill = 5
        )
        store.dispatch(AdventureActions.IncreaseSkill())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing skill it returns a state with the skill decremented`() {
        val store = setupStore(MainStateWrapper(currentSkill = 5))
        val newState = MainStateWrapper(currentSkill = 4)
        store.dispatch(AdventureActions.DecreaseSkill())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing skill with value it returns a state with the skill decremented`() {
        val store = setupStore(MainStateWrapper(currentSkill = 5))
        val newState = MainStateWrapper(currentSkill = 0)
        store.dispatch(AdventureActions.DecreaseSkill(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing decreasing skill under zero`() {
        val store = setupStore(MainStateWrapper(currentSkill = 0))
        val newState = MainStateWrapper(currentSkill = 0)
        store.dispatch(AdventureActions.DecreaseSkill())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when changing initial skill it returns a state with the initial skill with the new value`() {
        val store = setupStore(MainStateWrapper(initialSkill = 15))
        val newState = MainStateWrapper(initialSkill = 5)
        store.dispatch(AdventureActions.ChangeInitialSkill(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when setting the initial skill value under zero it sets to zero`() {
        val store = setupStore(MainStateWrapper(initialSkill = 1))
        val newState = MainStateWrapper(initialSkill = 0)
        store.dispatch(AdventureActions.ChangeInitialSkill(-5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing luck it returns a state with the luck incremented`() {
        val store = setupStore(
            MainStateWrapper(
                currentLuck = 5,
                initialLuck = 6
            )
        )
        val newState = MainStateWrapper(
            currentLuck = 6,
            initialLuck = 6
        )
        store.dispatch(AdventureActions.IncreaseLuck())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing luck with value it returns a state with the luck incremented`() {
        val store = setupStore(
            MainStateWrapper(
                currentLuck = 5,
                initialLuck = 10
            )
        )
        val newState = MainStateWrapper(
            currentLuck = 10,
            initialLuck = 10
        )
        store.dispatch(AdventureActions.IncreaseLuck(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing increasing luck over the initial value`() {
        val store = setupStore(
            MainStateWrapper(
                currentLuck = 5,
                initialLuck = 5
            )
        )
        val newState = MainStateWrapper(
            currentLuck = 5,
            initialLuck = 5
        )
        store.dispatch(AdventureActions.IncreaseLuck())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing luck it returns a state with the luck decremented`() {
        val store = setupStore(MainStateWrapper(currentLuck = 5))
        val newState = MainStateWrapper(currentLuck = 4)
        store.dispatch(AdventureActions.DecreaseLuck())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing luck with value it returns a state with the luck decremented`() {
        val store = setupStore(MainStateWrapper(currentLuck = 5))
        val newState = MainStateWrapper(currentLuck = 0)
        store.dispatch(AdventureActions.DecreaseLuck(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing decreasing luck under zero`() {
        val store = setupStore(MainStateWrapper(currentLuck = 0))
        val newState = MainStateWrapper(currentLuck = 0)
        store.dispatch(AdventureActions.DecreaseLuck())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when changing initial luck it returns a state with the initial luck with the new value`() {
        val store = setupStore(MainStateWrapper(initialLuck = 15))
        val newState = MainStateWrapper(initialLuck = 5)
        store.dispatch(AdventureActions.ChangeInitialLuck(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when setting the initial luck value under zero it sets to zero`() {
        val store = setupStore(MainStateWrapper(initialLuck = 1))
        val newState = MainStateWrapper(initialLuck = 0)
        store.dispatch(AdventureActions.ChangeInitialLuck(-5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing provisions it returns a state with the provisions incremented`() {
        val store = setupStore(MainStateWrapper(provisions = 5))
        val newState = MainStateWrapper(provisions = 6)
        store.dispatch(AdventureActions.IncreaseProvisions())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing provisions with value it returns a state with the provisions incremented`() {
        val store = setupStore(MainStateWrapper(provisions = 5))
        val newState = MainStateWrapper(provisions = 10)
        store.dispatch(AdventureActions.IncreaseProvisions(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing provisions it returns a state with the provisions decremented`() {
        val store = setupStore(MainStateWrapper(provisions = 5))
        val newState = MainStateWrapper(provisions = 4)
        store.dispatch(AdventureActions.DecreaseProvisions())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing provisions with value it returns a state with the provisions decremented`() {
        val store = setupStore(MainStateWrapper(provisions = 5))
        val newState = MainStateWrapper(provisions = 0)
        store.dispatch(AdventureActions.DecreaseProvisions(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing decreasing provisions under zero`() {
        val store = setupStore(MainStateWrapper(provisions = 0))
        val newState = MainStateWrapper(provisions = 0)
        store.dispatch(AdventureActions.DecreaseProvisions())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing gold it returns a state with the gold incremented`() {
        val store = setupStore(MainStateWrapper(gold = 5))
        val newState = MainStateWrapper(gold = 6)
        store.dispatch(AdventureActions.IncreaseGold())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when increasing gold with value it returns a state with the gold incremented`() {
        val store = setupStore(MainStateWrapper(gold = 5))
        val newState = MainStateWrapper(gold = 10)
        store.dispatch(AdventureActions.IncreaseGold(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing gold it returns a state with the gold decremented`() {
        val store = setupStore(MainStateWrapper(gold = 5))
        val newState = MainStateWrapper(gold = 4)
        store.dispatch(AdventureActions.DecreaseGold())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing gold with value it returns a state with the gold decremented`() {
        val store = setupStore(MainStateWrapper(gold = 5))
        val newState = MainStateWrapper(gold = 0)
        store.dispatch(AdventureActions.DecreaseGold(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing decreasing gold under zero`() {
        val store = setupStore(MainStateWrapper(gold = 0))
        val newState = MainStateWrapper(gold = 0)
        store.dispatch(AdventureActions.DecreaseGold())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing potion it returns a state with the potion decremented`() {
        val store = setupStore(
            MainStateWrapper(
                standardPotionValue = 5
            )
        )
        val newState = MainStateWrapper(standardPotionValue = 4)
        store.dispatch(AdventureActions.DecreasePotion())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when decreasing potion with value it returns a state with the potion decremented`() {
        val store = setupStore(
            MainStateWrapper(
                standardPotionValue = 5
            )
        )
        val newState = MainStateWrapper(standardPotionValue = 0)
        store.dispatch(AdventureActions.DecreasePotion(5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing decreasing potion under zero`() {
        val store = setupStore(
            MainStateWrapper(
                standardPotionValue = 0
            )
        )
        val newState = MainStateWrapper(standardPotionValue = 0)
        store.dispatch(AdventureActions.DecreasePotion())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when resetting stamina current stamina is changed to initial value`() {
        val store = setupStore(
            MainStateWrapper(
                currentStamina = 5,
                initialStamina = 10
            )
        )
        val newState = MainStateWrapper(
            currentStamina = 10,
            initialStamina = 10
        )
        store.dispatch(AdventureActions.ResetStamina())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when resetting skill current skill is changed to initial value`() {
        val store = setupStore(
            MainStateWrapper(
                currentSkill = 5,
                initialSkill = 10
            )
        )
        val newState = MainStateWrapper(
            currentSkill = 10,
            initialSkill = 10
        )
        store.dispatch(AdventureActions.ResetSkill())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when resetting luck current luck is changed to initial value`() {
        val store = setupStore(
            MainStateWrapper(
                currentLuck = 5,
                initialLuck = 10
            )
        )
        val newState = MainStateWrapper(
            currentLuck = 10,
            initialLuck = 10
        )
        store.dispatch(AdventureActions.ResetLuck())
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when changing the gold value it returns a state with gold as the new value`() {
        val store = setupStore(MainStateWrapper(gold = 5))
        val newState = MainStateWrapper(gold = 10)
        store.dispatch(AdventureActions.ChangeGold(10))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when setting the gold value value under zero it sets to zero`() {
        val store = setupStore(MainStateWrapper(gold = 0))
        val newState = MainStateWrapper(gold = 0)
        store.dispatch(AdventureActions.ChangeGold(-5))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when adding equipment it returns a state with the equipment added`() {
        val store = setupStore(
            MainStateWrapper(
                equipment = listOf(
                    "eq1"
                )
            )
        )
        val newState = MainStateWrapper(
            equipment = listOf(
                "eq1",
                "eq2"
            )
        )
        store.dispatch(AdventureActions.AddEquipment("eq2"))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing when adding empty string equipment`() {
        val store = setupStore(
            MainStateWrapper(
                equipment = listOf(
                    "eq1"
                )
            )
        )
        val newState = MainStateWrapper(equipment = listOf("eq1"))
        store.dispatch(AdventureActions.AddEquipment(""))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when removing equipment it returns a state with the equipment removed`() {
        val store = setupStore(
            MainStateWrapper(
                equipment = listOf(
                    "eq1",
                    "eq2",
                    "eq3"
                )
            )
        )
        val newState = MainStateWrapper(
            equipment = listOf(
                "eq1",
                "eq3"
            )
        )
        store.dispatch(AdventureActions.RemoveEquipment("eq2"))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing when removing equipment that doesn't exist in the list`() {
        val store = setupStore(
            MainStateWrapper(
                equipment = listOf(
                    "eq1",
                    "eq2",
                    "eq3"
                )
            )
        )
        val newState = MainStateWrapper(
            equipment = listOf(
                "eq1",
                "eq2",
                "eq3"
            )
        )
        store.dispatch(AdventureActions.RemoveEquipment("eq4"))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when adding notes it returns a state with the note added`() {
        val store = setupStore(
            MainStateWrapper(
                notes = listOf(
                    "n1"
                )
            )
        )
        val newState = MainStateWrapper(
            notes = listOf(
                "n1",
                "n2"
            )
        )
        store.dispatch(AdventureActions.AddNote("n2"))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing when adding empty string notes`() {
        val store = setupStore(
            MainStateWrapper(
                notes = listOf(
                    "n1"
                )
            )
        )
        val newState = MainStateWrapper(notes = listOf("n1"))
        store.dispatch(AdventureActions.AddNote(""))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `when removing notes it returns a state with the notes removed`() {
        val store = setupStore(
            MainStateWrapper(
                notes = listOf(
                    "n1",
                    "n2",
                    "n3"
                )
            )
        )
        val newState = MainStateWrapper(
            notes = listOf(
                "n1",
                "n3"
            )
        )
        store.dispatch(AdventureActions.RemoveNote("n2"))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }

    @Test
    fun `does nothing when removing notes that doesn't exist in the list`() {
        val store = setupStore(
            MainStateWrapper(
                notes = listOf(
                    "n1",
                    "n2",
                    "n3"
                )
            )
        )
        val newState = MainStateWrapper(
            notes = listOf(
                "n1",
                "n2",
                "n3"
            )
        )
        store.dispatch(AdventureActions.RemoveNote("n4"))
        Assert.assertEquals(newState.mainState, store.getState().mainState)
    }
}
