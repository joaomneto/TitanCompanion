package pt.joaomneto.titancompanion.adventure.twofm

import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert
import org.junit.Test
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll
import pt.joaomneto.titancompanion.adventure.state.AdventureState
import pt.joaomneto.titancompanion.adventure.state.CombatMode
import pt.joaomneto.titancompanion.adventure.state.CombatStateKey.*
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.*
import pt.joaomneto.titancompanion.util.DiceRoller

class TWOFMAdventureStateTest {

    private var adventure = TWOFMAdventure(emptyArray())

    @Test
    fun `when increasing stamina it returns a state with the stamina incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 6))
            val newState = AdventureState(mapOf(CURRENT_STAMINA to 6, INITIAL_STAMINA to 6))
            Assert.assertEquals(newState, state.increaseStamina())
        }
    }

    @Test
    fun `when increasing stamina with value it returns a state with the stamina incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 10))
            val newState = AdventureState(mapOf(CURRENT_STAMINA to 10, INITIAL_STAMINA to 10))
            Assert.assertEquals(newState, state.increaseStamina(5))
        }
    }

    @Test
    fun `does nothing increasing stamina over the initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 5))
            val newState = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 5))
            Assert.assertEquals(newState, state.increaseStamina())
        }
    }

    @Test
    fun `when decreasing stamina it returns a state with the stamina decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5))
            val newState = AdventureState(mapOf(CURRENT_STAMINA to 4))
            Assert.assertEquals(newState, state.decreaseStamina())
        }
    }

    @Test
    fun `when decreasing stamina with value it returns a state with the stamina decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5))
            val newState = AdventureState(mapOf(CURRENT_STAMINA to 0))
            Assert.assertEquals(newState, state.decreaseStamina(5))
        }
    }

    @Test
    fun `does nothing decreasing stamina under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 0))
            val newState = AdventureState(mapOf(CURRENT_STAMINA to 0))
            Assert.assertEquals(newState, state.decreaseStamina())
        }
    }

    @Test
    fun `when changing initial stamina it returns a state with the initial stamina with the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_STAMINA to 15))
            val newState = AdventureState(mapOf(INITIAL_STAMINA to 5))
            Assert.assertEquals(newState, state.changeInitialStamina(5))
        }
    }

    @Test
    fun `when setting the initial stamina value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_STAMINA to 0))
            val newState = AdventureState(mapOf(INITIAL_STAMINA to 0))
            Assert.assertEquals(newState, state.changeInitialStamina(-5))
        }
    }

    @Test
    fun `when increasing skill it returns a state with the skill incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 6))
            val newState = AdventureState(mapOf(CURRENT_SKILL to 6, INITIAL_SKILL to 6))
            Assert.assertEquals(newState, state.increaseSkill())
        }
    }

    @Test
    fun `when increasing skill with value it returns a state with the skill incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 10))
            val newState = AdventureState(mapOf(CURRENT_SKILL to 10, INITIAL_SKILL to 10))
            Assert.assertEquals(newState, state.increaseSkill(10))
        }
    }

    @Test
    fun `does nothing increasing skill over the initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 5))
            val newState = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 5))
            Assert.assertEquals(newState, state.increaseSkill())
        }
    }

    @Test
    fun `when decreasing skill it returns a state with the skill decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5))
            val newState = AdventureState(mapOf(CURRENT_SKILL to 4))
            Assert.assertEquals(newState, state.decreaseSkill())
        }
    }

    @Test
    fun `when decreasing skill with value it returns a state with the skill decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5))
            val newState = AdventureState(mapOf(CURRENT_SKILL to 0))
            Assert.assertEquals(newState, state.decreaseSkill(5))
        }
    }

    @Test
    fun `does nothing decreasing skill under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 0))
            val newState = AdventureState(mapOf(CURRENT_SKILL to 0))
            Assert.assertEquals(newState, state.decreaseSkill())
        }
    }

    @Test
    fun `when changing initial skill it returns a state with the initial skill with the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_SKILL to 15))
            val newState = AdventureState(mapOf(INITIAL_SKILL to 5))
            Assert.assertEquals(newState, state.changeInitialSkill(5))
        }
    }

    @Test
    fun `when setting the initial skill value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_SKILL to 1))
            val newState = AdventureState(mapOf(INITIAL_SKILL to 0))
            Assert.assertEquals(newState, state.changeInitialSkill(-5))
        }
    }

    @Test
    fun `when increasing luck it returns a state with the luck incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 6))
            val newState = AdventureState(mapOf(CURRENT_LUCK to 6, INITIAL_LUCK to 6))
            Assert.assertEquals(newState, state.increaseLuck())
        }
    }

    @Test
    fun `when increasing luck with value it returns a state with the luck incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 10))
            val newState = AdventureState(mapOf(CURRENT_LUCK to 10, INITIAL_LUCK to 10))
            Assert.assertEquals(newState, state.increaseLuck(5))
        }
    }

    @Test
    fun `does nothing increasing luck over the initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 5))
            val newState = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 5))
            Assert.assertEquals(newState, state.increaseLuck())
        }
    }

    @Test
    fun `when decreasing luck it returns a state with the luck decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5))
            val newState = AdventureState(mapOf(CURRENT_LUCK to 4))
            Assert.assertEquals(newState, state.decreaseLuck())
        }
    }

    @Test
    fun `when decreasing luck with value it returns a state with the luck decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5))
            val newState = AdventureState(mapOf(CURRENT_LUCK to 0))
            Assert.assertEquals(newState, state.decreaseLuck(5))
        }
    }

    @Test
    fun `does nothing decreasing luck under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 0))
            val newState = AdventureState(mapOf(CURRENT_LUCK to 0))
            Assert.assertEquals(newState, state.decreaseLuck())
        }
    }

    @Test
    fun `when changing initial luck it returns a state with the initial luck with the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_LUCK to 15))
            val newState = AdventureState(mapOf(INITIAL_LUCK to 5))
            Assert.assertEquals(newState, state.changeInitialLuck(5))
        }
    }

    @Test
    fun `when setting the initial luck value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_LUCK to 1))
            val newState = AdventureState(mapOf(INITIAL_LUCK to 0))
            Assert.assertEquals(newState, state.changeInitialLuck(-5))
        }
    }

    @Test
    fun `when increasing provisions it returns a state with the provisions incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            val newState = AdventureState(mapOf(PROVISIONS to 6))
            Assert.assertEquals(newState, state.increaseProvisions())
        }
    }

    @Test
    fun `when increasing provisions with value it returns a state with the provisions incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            val newState = AdventureState(mapOf(PROVISIONS to 10))
            Assert.assertEquals(newState, state.increaseProvisions(5))
        }
    }

    @Test
    fun `when decreasing provisions it returns a state with the provisions decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            val newState = AdventureState(mapOf(PROVISIONS to 4))
            Assert.assertEquals(newState, state.decreaseProvisions())
        }
    }

    @Test
    fun `when decreasing provisions with value it returns a state with the provisions decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            val newState = AdventureState(mapOf(PROVISIONS to 0))
            Assert.assertEquals(newState, state.decreaseProvisions(5))
        }
    }

    @Test
    fun `does nothing decreasing provisions under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 0))
            val newState = AdventureState(mapOf(PROVISIONS to 0))
            Assert.assertEquals(newState, state.decreaseProvisions())
        }
    }

    @Test
    fun `when increasing gold it returns a state with the gold incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            val newState = AdventureState(mapOf(GOLD to 6))
            Assert.assertEquals(newState, state.increaseGold())
        }
    }

    @Test
    fun `when increasing gold with value it returns a state with the gold incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            val newState = AdventureState(mapOf(GOLD to 10))
            Assert.assertEquals(newState, state.increaseGold(5))
        }
    }

    @Test
    fun `when decreasing gold it returns a state with the gold decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            val newState = AdventureState(mapOf(GOLD to 4))
            Assert.assertEquals(newState, state.decreaseGold())
        }
    }

    @Test
    fun `when decreasing gold with value it returns a state with the gold decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            val newState = AdventureState(mapOf(GOLD to 0))
            Assert.assertEquals(newState, state.decreaseGold(5))
        }
    }

    @Test
    fun `does nothing decreasing gold under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 0))
            val newState = AdventureState(mapOf(GOLD to 0))
            Assert.assertEquals(newState, state.decreaseGold())
        }
    }

    @Test
    fun `when decreasing potion it returns a state with the potion decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(STANDARD_POTION_VALUE to 5))
            val newState = AdventureState(mapOf(STANDARD_POTION_VALUE to 4))
            Assert.assertEquals(newState, state.decreasePotion())
        }
    }

    @Test
    fun `when decreasing potion with value it returns a state with the potion decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(STANDARD_POTION_VALUE to 5))
            val newState = AdventureState(mapOf(STANDARD_POTION_VALUE to 0))
            Assert.assertEquals(newState, state.decreasePotion(5))
        }
    }

    @Test
    fun `does nothing decreasing potion under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(STANDARD_POTION_VALUE to 0))
            val newState = AdventureState(mapOf(STANDARD_POTION_VALUE to 0))
            Assert.assertEquals(newState, state.decreasePotion())
        }
    }

    @Test
    fun `when resetting stamina current stamina is changed to initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 10))
            val newState = AdventureState(mapOf(CURRENT_STAMINA to 10, INITIAL_STAMINA to 10))
            Assert.assertEquals(newState, state.resetStamina())
        }
    }

    @Test
    fun `when resetting skill current skill is changed to initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 10))
            val newState = AdventureState(mapOf(CURRENT_SKILL to 10, INITIAL_SKILL to 10))
            Assert.assertEquals(newState, state.resetSkill())
        }
    }

    @Test
    fun `when resetting luck current luck is changed to initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 10))
            val newState = AdventureState(mapOf(CURRENT_LUCK to 10, INITIAL_LUCK to 10))
            Assert.assertEquals(newState, state.resetLuck())
        }
    }

    @Test
    fun `when changing the gold value it returns a state with gold as the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            val newState = AdventureState(mapOf(GOLD to 10))
            Assert.assertEquals(newState, state.changeGold(10))
        }
    }

    @Test
    fun `when setting the gold value value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 0))
            val newState = AdventureState(mapOf(GOLD to 0))
            Assert.assertEquals(newState, state.changeGold(-5))
        }
    }

    @Test
    fun `when adding equipment it returns a state with the equipment added`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1")))
            val newState = AdventureState(mapOf(EQUIPMENT to listOf("eq1", "eq2")))
            Assert.assertEquals(newState, state.addEquipment("eq2"))
        }
    }

    @Test
    fun `does nothing when adding empty string equipment`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1")))
            val newState = AdventureState(mapOf(EQUIPMENT to listOf("eq1")))
            Assert.assertEquals(newState, state.addEquipment(""))
        }
    }

    @Test
    fun `when removing equipment it returns a state with the equipment removed`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1", "eq2", "eq3")))
            val newState = AdventureState(mapOf(EQUIPMENT to listOf("eq1", "eq3")))
            Assert.assertEquals(newState, state.removeEquipment("eq2"))
        }
    }

    @Test
    fun `does nothing when removing equipment that doesn't exist in the list`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1", "eq2", "eq3")))
            val newState = AdventureState(mapOf(EQUIPMENT to listOf("eq1", "eq2", "eq3")))
            Assert.assertEquals(newState, state.removeEquipment("eq4"))
        }
    }

    @Test
    fun `when adding notes it returns a state with the note added`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1")))
            val newState = AdventureState(mapOf(NOTES to listOf("n1", "n2")))
            Assert.assertEquals(newState, state.addNote("n2"))
        }
    }

    @Test
    fun `does nothing when adding empty string notes`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1")))
            val newState = AdventureState(mapOf(NOTES to listOf("n1")))
            Assert.assertEquals(newState, state.addNote(""))
        }
    }

    @Test
    fun `when removing notes it returns a state with the notes removed`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1", "n2", "n3")))
            val newState = AdventureState(mapOf(NOTES to listOf("n1", "n3")))
            Assert.assertEquals(newState, state.removeNote("n2"))
        }
    }

    @Test
    fun `does nothing when removing notes that doesn't exist in the list`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1", "n2", "n3")))
            val newState = AdventureState(mapOf(NOTES to listOf("n1", "n2", "n3")))
            Assert.assertEquals(newState, state.removeNote("n4"))
        }
    }

    @Test
    fun `when starting combat it returns a state with combat started`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_STARTED to false))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_STARTED to true))))
            Assert.assertEquals(newState, state.startCombat())
        }
    }

    @Test
    fun `when stopping combat it returns a state without combat started`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_STARTED to true))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_STARTED to false))))
            Assert.assertEquals(newState, state.stopCombat())
        }
    }

    @Test
    fun `when resetting combat stamina loss it returns a state with zero stamina loss`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 8))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 0))))
            Assert.assertEquals(newState, state.resetStaminaLoss())
        }
    }

    @Test
    fun `when clearing combatants it returns a state without combatants`() {
        with(adventure) {
            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(
                                                    AdventureCombatFragment.Combatant(1, 2, 3, true, "2", true),
                                                    AdventureCombatFragment.Combatant(2, 3, 1, true, "2", false)
                                            )
                                    )
                            )
                    )
            )
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_POSITIONS to emptyList<AdventureCombatFragment.Combatant>()))))
            Assert.assertEquals(newState, state.clearCombatants())
        }
    }

    @Test
    fun `when resetting combat mode loss it returns a state with the default combat mode`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_MODE to CombatMode.SEQUENCE))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_MODE to CombatMode.NORMAL))))
            Assert.assertEquals(newState, state.resetCombatMode())
        }
    }

    @Test
    fun `when changing combat luck test result it returns a state with the new luck test result`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(LUCK_TEST to false))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(LUCK_TEST to true))))
            Assert.assertEquals(newState, state.changeCombatLuckTest(true))
        }
    }

    @Test
    fun `when increasing combat stamina loss it returns a state with stamina losse increased`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 0))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 1))))
            Assert.assertEquals(newState, state.increaseCombatStaminaLoss())
        }
    }

    @Test
    fun `when increasing combat stamina loss with value it returns a state with stamina losse increased`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 0))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 5))))
            Assert.assertEquals(newState, state.increaseCombatStaminaLoss(5))
        }
    }

    @Test
    fun `when decreasing combat stamina loss it returns a state with stamina losse increased`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 1))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 0))))
            Assert.assertEquals(newState, state.decreaseCombatStaminaLoss())
        }
    }

    @Test
    fun `when decreasing combat stamina loss with value it returns a state with stamina loss decreased`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 5))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(STAMINA_LOSS to 0))))
            Assert.assertEquals(newState, state.decreaseCombatStaminaLoss(5))
        }
    }

    @Test
    fun `when setting the combat result it returns a state with combat result text`() {
        with(adventure) {
            val state = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_RESULT to ""))))
            val newState = AdventureState(mapOf(COMBAT to AdventureState.CombatState(mapOf(COMBAT_RESULT to "result"))))
            Assert.assertEquals(newState, state.combatResult("result"))
        }
    }

    @Test
    fun `when removing a combatant it returns a state without the combatant`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)
            val c3 = AdventureCombatFragment.Combatant(3, 3, 0, false, "2", false, 0)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_POSITIONS to listOf(c1, c2, c3))
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_POSITIONS to listOf(c1, c3.copy(isDefenseOnly = true)))
                            )
                    )
            )
            Assert.assertEquals(newState, state.removeCombatant(c2))
        }
    }

    @Test
    fun `when changing the active a combatant it returns a state with the new active combatant`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)
            val c3 = AdventureCombatFragment.Combatant(3, 3, 0, false, "2", false, 0)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_POSITIONS to listOf(c1, c2, c3))
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_POSITIONS to listOf(c1.copy(isActive = false), c2.copy(isActive = true), c3))
                            )
                    )
            )
            Assert.assertEquals(newState, state.changeActiveCombatant(1))
        }
    }

    @Test
    fun `when modifying a combatant it returns a state with the changed combatant`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)
            val c3 = AdventureCombatFragment.Combatant(3, 3, 0, false, "2", false, 0)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_POSITIONS to listOf(c1, c2, c3))
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_POSITIONS to listOf(c1, c2, c3.copy(currentStamina = 0, staminaLoss = 4)))
                            )
                    )
            )
            Assert.assertEquals(newState, state.modifyCombatant(c3.copy(currentStamina = 0, staminaLoss = 4)))
        }
    }

    @Test
    fun `when setting a combat draw it returns a state with the combat draw enabled`() {
        with(adventure) {
            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(DRAW to false)
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(DRAW to true)
                            )
                    )
            )
            Assert.assertEquals(newState, state.combatDraw())
        }
    }

    @Test
    fun `when setting a combat hit it returns a state with the combat hit enabled`() {
        with(adventure) {
            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(HIT to false)
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(HIT to true)
                            )
                    )
            )
            Assert.assertEquals(newState, state.combatHit())
        }
    }

    @Test
    fun `when changing the combat mode it returns a state with the combat mode altered`() {
        with(adventure) {
            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_MODE to CombatMode.NORMAL)
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(COMBAT_MODE to CombatMode.SEQUENCE)
                            )
                    )
            )
            Assert.assertEquals(newState, state.combatMode(CombatMode.SEQUENCE))
        }
    }

    @Test
    fun `when changing the combat luck test it returns a state with the last combat luck result`() {
        with(adventure) {
            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(LUCK_TEST to true)
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(LUCK_TEST to false)
                            )
                    )
            )
            Assert.assertEquals(newState, state.combatLuckTest(false))
        }
    }

    @Test
    fun `when changing the combat attack roll it returns a state with the last combat attack roll result`() {
        with(adventure) {
            mockkObject(DiceRoller)
            every { DiceRoller.rollD6() } returns 3

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(ATTACK_DICE_ROLL to DiceRoll(1, 8))
                            )
                    )
            )
            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(ATTACK_DICE_ROLL to DiceRoll(3, 3))
                            )
                    )
            )
            Assert.assertEquals(newState, state.combatDiceRoll())
        }
    }

    @Test
    fun `when resetting combat attack roll it returns a state with zero stamina loss, combat stopped, no combattants and combat mode set to standard`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", true, 0)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            STAMINA_LOSS to 3,
                                            COMBAT_STARTED to true,
                                            COMBAT_POSITIONS to listOf(c1, c2),
                                            COMBAT_MODE to CombatMode.SEQUENCE,
                                            COMBAT_RESULT to ""
                                    )
                            )
                    )
            )

            Assert.assertEquals(
                    state.resetStaminaLoss()
                            .stopCombat()
                            .clearCombatants()
                            .resetCombatMode(),
                    state.resetCombat()
            )
        }
    }

    @Test
    fun `when advancing combat it returns a state with the following combatant as the active enemy`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, true, "2", true, 0)
            val c2 = AdventureCombatFragment.Combatant(2, 2, 0, false, "2", false, 0)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2),
                                            COMBAT_MODE to CombatMode.SEQUENCE
                                    )
                            )
                    )
            )

            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1.copy(isActive = false, isDefenseOnly = false), c2.copy(isActive = true, isDefenseOnly = true)),
                                            COMBAT_MODE to CombatMode.SEQUENCE
                                    )
                            )
                    )
            )

            val newStateAdvanced = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1.copy(isDefenseOnly = false), c2.copy(isDefenseOnly = true)),
                                            COMBAT_MODE to CombatMode.SEQUENCE
                                    )
                            )
                    )
            )

            Assert.assertEquals(
                    newState,
                    state.advanceCombat()
            )

            Assert.assertEquals(
                    newStateAdvanced,
                    newState.advanceCombat()
            )
        }
    }

    @Test
    fun `when removing combatant and advancing combat it returns a state without the combatand and the following combatant as the active enemy`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)
            val c3 = AdventureCombatFragment.Combatant(2, 2, 0, true, "2", false, 0)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2, c3),
                                            COMBAT_MODE to CombatMode.SEQUENCE
                                    )
                            )
                    )
            )

            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c3.copy(isActive = true, isDefenseOnly = true)),
                                            COMBAT_MODE to CombatMode.SEQUENCE
                                    )
                            )
                    )
            )


            Assert.assertEquals(
                    newState,
                    state.removeCombatantAndAdvanceCombat()
            )


        }
    }

    @Test
    fun `when adding a combatant it returns a state with the new enemy and in the last position`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)
            val c3 = AdventureCombatFragment.Combatant(2, 2, 0, true, "2", false, 0)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2)
                                    )
                            )
                    )
            )

            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2, c3)
                                    )
                            )
                    )
            )

            Assert.assertEquals(
                    newState,
                    state.addCombatant(c3)
            )

        }
    }

    @Test
    fun `when decreasing a combatant stamina it returns a state with the enemy with updated stamina`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2)
                                    )
                            )
                    )
            )

            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2.copy(currentStamina = 2))
                                    )
                            )
                    )
            )

            Assert.assertEquals(
                    newState,
                    state.decreaseCombatantStamina(c2)
            )

        }
    }

    @Test
    fun `when increasing a combatant stamina it returns a state with the enemy with updated stamina`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2)
                                    )
                            )
                    )
            )

            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2.copy(currentStamina = 4))
                                    )
                            )
                    )
            )

            Assert.assertEquals(
                    newState,
                    state.increaseCombatantStamina(c2)
            )

        }
    }

    @Test
    fun `when decreasing a combatant skill it returns a state with the enemy with updated skill`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2)
                                    )
                            )
                    )
            )

            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2.copy(currentSkill = 3))
                                    )
                            )
                    )
            )

            Assert.assertEquals(
                    newState,
                    state.decreaseCombatantSkill(c2)
            )

        }
    }

    @Test
    fun `when increasing a combatant skill it returns a state with the enemy with updated skill`() {
        with(adventure) {
            val c1 = AdventureCombatFragment.Combatant(1, 1, 0, false, "2", false, 0)
            val c2 = AdventureCombatFragment.Combatant(3, 4, 1, true, "2", true, 1)

            val state = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2)
                                    )
                            )
                    )
            )

            val newState = AdventureState(
                    mapOf(
                            COMBAT to AdventureState.CombatState(
                                    mapOf(
                                            COMBAT_POSITIONS to listOf(c1, c2.copy(currentSkill = 5))
                                    )
                            )
                    )
            )

            Assert.assertEquals(
                    newState,
                    state.increaseCombatantSkill(c2)
            )

        }
    }
}
