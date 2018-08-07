package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import io.mockk.every
import io.mockk.mockkObject
import junit.framework.Assert
import org.junit.Test
import org.robolectric.shadows.ShadowDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.state.AdventureState
import pt.joaomneto.titancompanion.adventure.state.CombatMode
import pt.joaomneto.titancompanion.adventure.twofm.TWOFMAdventureStateTest
import pt.joaomneto.titancompanion.util.DiceRoller
import kotlin.reflect.KClass

abstract class AdventureCombatTest<T : Adventure<*>, U : AdventureCombatFragment>(
    adventureClass: KClass<T>,
    fragmentClass: KClass<U>,
    savegame: String
) : TCAdventureBaseTest<T, U>(
    adventureClass, fragmentClass, savegame
) {

    private val combatState
        get() = adventure.state.combat

    private val dialog
        get() = ShadowDialog.getLatestDialog() as AlertDialog

    @Test
    fun `when clicking the start combat without combatants it does nothing`() {

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertEquals(false, combatState.combatStarted)

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        Assert.assertTrue(combatResult.text.isBlank())
    }

    @Test
    fun `when clicking the add opponent button it adds an enemy to the list via a dialog`() {
        fragment.findComponent<Button>(R.id.addCombatButton).performClick()

        val enemySkillInput = dialog.findViewById<EditText>(R.id.enemySkillValue)
        val enemyStaminaInput = dialog.findViewById<EditText>(R.id.enemyStaminaValue)
        val handicapInput = dialog.findViewById<EditText>(R.id.handicapValue)

        enemySkillInput.setText("8")
        enemyStaminaInput.setText("12")
        handicapInput.setText("1")

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertEquals(
            AdventureCombatFragment.Combatant(12, 8, 1, false, "2", true, 0, "FAKE_ID"),
            combatState.combatPositions[0].copy(id = "FAKE_ID")
        )

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = getViewByPosition(0, listView)

        Assert.assertEquals("8", firstCombatantView.findViewById<TextView>(R.id.combatTextSkillValue).text)
        Assert.assertEquals("12", firstCombatantView.findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when adding two opponents it adds two enemies to the list via a dialog and keeps the first one as active`() {
        initializeCombatWithTwoEnemies(enemyHandicap1 = 1, enemyHandicap2 = -1)

        Assert.assertEquals(
            AdventureCombatFragment.Combatant(12, 8, 1, false, "2", true, 0, "FAKE_ID"),
            combatState.combatPositions[0].copy(id = "FAKE_ID")
        )

        Assert.assertEquals(
            AdventureCombatFragment.Combatant(13, 9, -1, true, "2", false, 0, "FAKE_ID"),
            combatState.combatPositions[1].copy(id = "FAKE_ID")
        )

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = getViewByPosition(0, listView)
        val secondCombatantView = getViewByPosition(1, listView)

        Assert.assertEquals("8", firstCombatantView.findViewById<TextView>(R.id.combatTextSkillValue).text)
        Assert.assertEquals("12", firstCombatantView.findViewById<TextView>(R.id.combatTextStaminaValue).text)
        Assert.assertEquals("9", secondCombatantView.findViewById<TextView>(R.id.combatTextSkillValue).text)
        Assert.assertEquals("13", secondCombatantView.findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when clicking the plus stamina button for an opponent in increases it's stamina`() {
        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()

        Assert.assertEquals(13, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(14, combatState.combatPositions[1].currentStamina)

        Assert.assertEquals("13", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        Assert.assertEquals("14", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)

        firstCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()

        Assert.assertEquals(14, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(15, combatState.combatPositions[1].currentStamina)

        Assert.assertEquals("14", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        Assert.assertEquals("15", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when clicking the minus stamina button for an opponent in increases it's stamina`() {
        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()

        Assert.assertEquals(11, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(12, combatState.combatPositions[1].currentStamina)

        Assert.assertEquals("11", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        Assert.assertEquals("12", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)

        firstCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()

        Assert.assertEquals(10, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(11, combatState.combatPositions[1].currentStamina)

        Assert.assertEquals("10", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        Assert.assertEquals("11", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when clicking the plus skill button for an opponent in increases it's skill`() {
        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()

        Assert.assertEquals("9", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        Assert.assertEquals("10", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)

        firstCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()

        Assert.assertEquals("10", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        Assert.assertEquals("11", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
    }

    @Test
    fun `when clicking the minus skill button for an opponent in increases it's skill`() {
        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()

        Assert.assertEquals(7, combatState.combatPositions[0].currentSkill)
        Assert.assertEquals(8, combatState.combatPositions[1].currentSkill)

        Assert.assertEquals("7", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        Assert.assertEquals("8", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)

        firstCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()

        Assert.assertEquals(6, combatState.combatPositions[0].currentSkill)
        Assert.assertEquals(7, combatState.combatPositions[1].currentSkill)

        Assert.assertEquals("6", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        Assert.assertEquals("7", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
    }

    private fun initializeCombatWithTwoEnemies(
        enemySkill1: Int = 8,
        enemyStamina1: Int = 12,
        enemyHandicap1: Int = 0,
        enemySkill2: Int = 9,
        enemyStamina2: Int = 13,
        enemyHandicap2: Int = 0
    ) {
        fragment.findComponent<Button>(R.id.addCombatButton).performClick()

        val enemySkillInput = { dialog.findViewById<EditText>(R.id.enemySkillValue) }
        val enemyStaminaInput = { dialog.findViewById<EditText>(R.id.enemyStaminaValue) }
        val handicapInput = { dialog.findViewById<EditText>(R.id.handicapValue) }

        enemySkillInput().setText(enemySkill1.toString())
        enemyStaminaInput().setText(enemyStamina1.toString())
        handicapInput().setText(enemyHandicap1.toString())

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        fragment.findComponent<Button>(R.id.addCombatButton).performClick()

        enemySkillInput().setText(enemySkill2.toString())
        enemyStaminaInput().setText(enemyStamina2.toString())
        handicapInput().setText(enemyHandicap2.toString())

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()
    }

    private fun initializeCombatWithThreeEnemies(
        enemySkill1: Int = 8,
        enemyStamina1: Int = 12,
        enemyHandicap1: Int = 1,
        enemySkill2: Int = 9,
        enemyStamina2: Int = 13,
        enemyHandicap2: Int = -1,
        enemySkill3: Int = 9,
        enemyStamina3: Int = 13,
        enemyHandicap3: Int = -1
    ) {
        initializeCombatWithTwoEnemies(enemySkill1, enemyStamina1, enemyHandicap1, enemySkill2, enemyStamina2, enemyHandicap2)
        fragment.findComponent<Button>(R.id.addCombatButton).performClick()

        val enemySkillInput = dialog.findViewById<EditText>(R.id.enemySkillValue)
        val enemyStaminaInput = dialog.findViewById<EditText>(R.id.enemyStaminaValue)
        val handicapInput = dialog.findViewById<EditText>(R.id.handicapValue)


        enemySkillInput.setText(enemySkill3.toString())
        enemyStaminaInput.setText(enemyStamina3.toString())
        handicapInput.setText(enemyHandicap3.toString())

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()
    }

    @Test
    fun `when clicking the combat mode switch it changes the combat mode`() {
        fragment.findComponent<Switch>(R.id.combatType).performClick()

        Assert.assertEquals(CombatMode.SEQUENCE, combatState.combatMode)

        fragment.findComponent<Switch>(R.id.combatType).performClick()

        Assert.assertEquals(CombatMode.NORMAL, combatState.combatMode)
    }

    @Test
    fun `when clicking start combat it starts the combat and changes the combat layout`() {
        initializeCombatWithTwoEnemies()
        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertEquals(GONE, fragment.findComponent<View>(R.id.addCombatButton).visibility)
        Assert.assertEquals(GONE, fragment.findComponent<View>(R.id.combatType).visibility)
        Assert.assertEquals(GONE, fragment.findComponent<View>(R.id.startCombat).visibility)
        Assert.assertEquals(GONE, fragment.findComponent<View>(R.id.resetCombat).visibility)
        Assert.assertEquals(VISIBLE, fragment.findComponent<View>(R.id.resetCombat2).visibility)
        Assert.assertEquals(VISIBLE, fragment.findComponent<View>(R.id.testLuckButton).visibility)
        Assert.assertEquals(VISIBLE, fragment.findComponent<View>(R.id.attackButton).visibility)

        Assert.assertEquals(true, combatState.combatStarted)
    }

    @Test
    fun `when resetting before starting combat the combat positions are emptied`() {
        initializeCombatWithTwoEnemies()
        fragment.findComponent<Button>(R.id.resetCombat).performClick()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        Assert.assertTrue(combatState.combatPositions.isEmpty())

        Assert.assertEquals(0, listView.childCount)
    }

    @Test
    fun `when resetting after starting combat the combat positions are emptied and layout is switched back`() {
        initializeCombatWithTwoEnemies()
        fragment.findComponent<Button>(R.id.startCombat).performClick()
        fragment.findComponent<Button>(R.id.resetCombat).performClick()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        Assert.assertTrue(combatState.combatPositions.isEmpty())
        Assert.assertEquals(0, listView.childCount)

        Assert.assertEquals(VISIBLE, fragment.findComponent<View>(R.id.addCombatButton).visibility)
        Assert.assertEquals(VISIBLE, fragment.findComponent<View>(R.id.combatType).visibility)
        Assert.assertEquals(VISIBLE, fragment.findComponent<View>(R.id.startCombat).visibility)
        Assert.assertEquals(VISIBLE, fragment.findComponent<View>(R.id.resetCombat).visibility)
        Assert.assertEquals(GONE, fragment.findComponent<View>(R.id.resetCombat2).visibility)
        Assert.assertEquals(GONE, fragment.findComponent<View>(R.id.testLuckButton).visibility)
        Assert.assertEquals(GONE, fragment.findComponent<View>(R.id.attackButton).visibility)
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player only combats an enemy at a time`() {
        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertTrue(combatState.combatPositions[0].isActive)
        Assert.assertFalse(combatState.combatPositions[1].isActive)

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        Assert.assertFalse(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertTrue(combatState.combatPositions[0].isActive)
        Assert.assertFalse(combatState.combatPositions[1].isActive)

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        Assert.assertFalse(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, the player combats the enemies sequentially`() {
        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        fragment.findComponent<Switch>(R.id.combatType).performClick()
        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertFalse(combatState.combatPositions[0].isActive)
        Assert.assertTrue(combatState.combatPositions[1].isActive)

        Assert.assertFalse(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        Assert.assertTrue(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertTrue(combatState.combatPositions[0].isActive)
        Assert.assertFalse(combatState.combatPositions[1].isActive)

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        Assert.assertFalse(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player will apply damage to the enemy if he wins the round`() {
        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        val startingStamina = combatState.combatPositions[0].currentStamina

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        val newStamina = startingStamina - adventure.damage()
        Assert.assertEquals(newStamina, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(
            newStamina.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        val newStamina2 = newStamina - adventure.damage()
        Assert.assertEquals(newStamina2, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(
            newStamina2.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player will apply bonus damage to the enemy if he wins the round and wins a luck roll`() {
        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        val startingStamina = combatState.combatPositions[0].currentStamina

        val firstCombatantStamina = {
            getViewByPosition(
                0,
                listView
            ).findViewById<TextView>(R.id.combatTextStaminaValue).text
        }

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        val newStamina = startingStamina - adventure.damage()
        Assert.assertEquals(newStamina, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(newStamina.toString(), firstCombatantStamina())

        fragment.findComponent<Button>(R.id.testLuckButton).performClick()

        val newStamina2 = newStamina - 1
        Assert.assertEquals(newStamina2, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(newStamina2.toString(), firstCombatantStamina())
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player will apply diminished damage to the enemy if he wins the round and loses a luck roll`() {
        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        val startingStamina = combatState.combatPositions[0].currentStamina

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        val newStamina = startingStamina - adventure.damage()
        Assert.assertEquals(newStamina, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(
            newStamina.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )

        for (i in 1 until 12) {
            adventure.performDecreaseLuck(fragment)
        }

        fragment.findComponent<Button>(R.id.testLuckButton).performClick()

        val newStamina2 = newStamina + 1
        Assert.assertEquals(newStamina2, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals(
            newStamina2.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )
    }

    @Test
    fun `when doing combat turns in normal combat mode, and you win the rounds, the combat status displays the turn result`() {
        initializeCombatWithTwoEnemies()
        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertEquals("You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertEquals("You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, and you win the rounds, the combat status displays the turn result`() {
        initializeCombatWithTwoEnemies()
        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertEquals("You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertEquals("You have blocked the enemy attack! (12 + 12 + 0) vs (12 + 9)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in normal combat mode, and you lose the rounds, the combat status displays the turn result`() {
        initializeCombatWithTwoEnemies()
        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        for (i in 1 until 12) {
            adventure.performDecreaseSkill(fragment as AdventureCombatFragment)
        }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 8). (-2St)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, and you lose the rounds, the combat status displays the turn result`() {
        initializeCombatWithTwoEnemies()
        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        for (i in 1 until 12) {
            adventure.performDecreaseSkill(fragment as AdventureCombatFragment)
        }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 9). (-2St)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in normal combat mode, the active enemy is the same until it's defeated`() {
        initializeCombatWithTwoEnemies()

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6


        Assert.assertTrue(combatState.combatPositions.first().isActive)
        Assert.assertTrue(combatState.combatPositions.filter { it.isActive }.size == 1)

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertTrue(combatState.combatPositions.first().isActive)
        Assert.assertTrue(combatState.combatPositions.filter { it.isActive }.size == 1)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertTrue(combatState.combatPositions.first().isActive)
        Assert.assertTrue(combatState.combatPositions.filter { it.isActive }.size == 1)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, the active enemy is the same until it's defeated`() {
        initializeCombatWithTwoEnemies()
        fragment.findComponent<TextView>(R.id.combatType).performClick()

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6


        Assert.assertTrue(combatState.combatPositions.first().isActive)

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        Assert.assertTrue(combatState.combatPositions[1].isActive)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertTrue(combatState.combatPositions.first().isActive)
    }

    @Test
    fun `when advancing combat the following combatant is activated`() {
        initializeCombatWithThreeEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val firstCombatantView = {getViewByPosition(0, listView)}
        val secondCombatantView = {getViewByPosition(1, listView)}
        val thirdCombatantView = {getViewByPosition(2, listView)}

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertTrue(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertTrue(thirdCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
    }

    @Test
    fun `when reducing enemy's stamina to zero, the active enemy is removed from the state and the following combatant is activated`() {
        initializeCombatWithTwoEnemies(enemyStamina1 = 2)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val firstCombatantView = {getViewByPosition(0, listView)}

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        Assert.assertEquals(1, listView.count)
    }

    @Test
    fun `when reducing enemy's stamina to zero, the victory message is displayed`() {
        initializeCombatWithTwoEnemies(enemyStamina1 = 2)

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val firstCombatantView = {getViewByPosition(0, listView)}

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        Assert.assertEquals("You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)\nYou have defeated an enemy!", combatResult.text)
    }

    @Test
    fun `when the combat round ends as a draw, neither the combatant nor the player lose stamina`(){
        initializeCombatWithTwoEnemies(enemySkill1 = 12 , enemyStamina1 = 12)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = {getViewByPosition(0, listView)}

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()


        Assert.assertEquals(12, combatState.combatPositions[0].currentStamina)
        Assert.assertEquals("12", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)

        Assert.assertEquals(24, adventure.state.currentStamina)
    }

    @Test
    fun `when the combat round ends as a draw, it displays the draw status message`(){
        initializeCombatWithTwoEnemies(enemySkill1 = 12 , enemyStamina1 = 12)
        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = {getViewByPosition(0, listView)}

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()


        Assert.assertEquals("Both you and the enemy have missed", combatResult.text)
    }

    @Test
    fun `when the player's stamina reaches zero it displays a defeat message`(){
        initializeCombatWithTwoEnemies(enemySkill1 = 12 , enemyStamina1 = 12)

        adventure.performDecreaseStamina(fragment, 23)
        adventure.performDecreaseSkill(fragment, 11)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = {getViewByPosition(0, listView)}

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        Assert.assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()


        Assert.assertEquals("You're dead…", dialog.findViewById<TextView>(android.R.id.message).text)
    }
}
