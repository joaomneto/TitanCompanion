package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert.*
import org.junit.Test
import org.robolectric.shadows.ShadowDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.util.DiceRoller
import java.util.Properties
import kotlin.reflect.KClass

abstract class AdventureCombatTest<T : Adventure, U : AdventureCombatFragment>(
    adventureClass: KClass<T>,
    fragmentClass: KClass<U>,
    savegame: Properties
) : TCAdventureBaseTest<T, U>(
    adventureClass, fragmentClass, savegame
) {

    private val dialog
        get() = ShadowDialog.getLatestDialog() as AlertDialog

    @Test
    fun `when clicking the start combat without combatants it does nothing`() {
        loadActivity()

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertEquals(false, fragment.combatStarted)

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        assertTrue(combatResult.text.isBlank())
    }

    @Test
    fun `when clicking the add opponent button it adds an enemy to the list via a dialog`() {
        loadActivity()

        fragment.findComponent<Button>(R.id.addCombatButton).performClick()

        val enemySkillInput = dialog.findViewById<EditText>(R.id.enemySkillValue)
        val enemyStaminaInput = dialog.findViewById<EditText>(R.id.enemyStaminaValue)
        val handicapInput = dialog.findViewById<EditText>(R.id.handicapValue)

        enemySkillInput.setText("8")
        enemyStaminaInput.setText("12")
        handicapInput.setText("1")

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        assertEquals(
            AdventureCombatFragment.Combatant(
                currentStamina = 12,
                currentSkill = 8,
                handicap = 1,
                isDefenseOnly = false,
                damage = "2",
                isActive = true
            ),
            fragment.combatPositions[0]
        )

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = getViewByPosition(0, listView)

        assertEquals("8", firstCombatantView.findViewById<TextView>(R.id.combatTextSkillValue).text)
        assertEquals("12", firstCombatantView.findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when adding two opponents it adds two enemies to the list via a dialog and keeps the first one as active`() {
        loadActivity()

        initializeCombatWithTwoEnemies(enemyHandicap1 = 1, enemyHandicap2 = -1)

        assertEquals(
            AdventureCombatFragment.Combatant(
                currentStamina = 12,
                currentSkill = 8,
                handicap = 1,
                isDefenseOnly = false,
                damage = "2",
                isActive = true
            ),
            fragment.combatPositions[0]
        )

        assertEquals(
            AdventureCombatFragment.Combatant(
                currentStamina = 13,
                currentSkill = 9,
                handicap = -1,
                isDefenseOnly = true,
                damage = "2",
                isActive = false
            ),
            fragment.combatPositions[1]
        )

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = getViewByPosition(0, listView)
        val secondCombatantView = getViewByPosition(1, listView)

        assertEquals("8", firstCombatantView.findViewById<TextView>(R.id.combatTextSkillValue).text)
        assertEquals("12", firstCombatantView.findViewById<TextView>(R.id.combatTextStaminaValue).text)
        assertEquals("9", secondCombatantView.findViewById<TextView>(R.id.combatTextSkillValue).text)
        assertEquals("13", secondCombatantView.findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when clicking the plus stamina button for an opponent in increases it's stamina`() {
        loadActivity()

        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()

        assertEquals(13, fragment.combatPositions[0].currentStamina)
        assertEquals(14, fragment.combatPositions[1].currentStamina)

        assertEquals("13", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        assertEquals("14", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)

        firstCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantStaminaButton).performClick()

        assertEquals(14, fragment.combatPositions[0].currentStamina)
        assertEquals(15, fragment.combatPositions[1].currentStamina)

        assertEquals("14", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        assertEquals("15", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when clicking the minus stamina button for an opponent in increases it's stamina`() {
        loadActivity()

        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()

        assertEquals(11, fragment.combatPositions[0].currentStamina)
        assertEquals(12, fragment.combatPositions[1].currentStamina)

        assertEquals("11", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        assertEquals("12", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)

        firstCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantStaminaButton).performClick()

        assertEquals(10, fragment.combatPositions[0].currentStamina)
        assertEquals(11, fragment.combatPositions[1].currentStamina)

        assertEquals("10", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
        assertEquals("11", secondCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)
    }

    @Test
    fun `when clicking the plus skill button for an opponent in increases it's skill`() {
        loadActivity()

        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()

        assertEquals("9", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        assertEquals("10", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)

        firstCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.plusCombatantSkillButton).performClick()

        assertEquals("10", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        assertEquals("11", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
    }

    @Test
    fun `when clicking the minus skill button for an opponent in increases it's skill`() {
        loadActivity()

        initializeCombatWithTwoEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        firstCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()

        assertEquals(7, fragment.combatPositions[0].currentSkill)
        assertEquals(8, fragment.combatPositions[1].currentSkill)

        assertEquals("7", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        assertEquals("8", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)

        firstCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()
        secondCombatantView().findViewById<Button>(R.id.minusCombatantSkillButton).performClick()

        assertEquals(6, fragment.combatPositions[0].currentSkill)
        assertEquals(7, fragment.combatPositions[1].currentSkill)

        assertEquals("6", firstCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
        assertEquals("7", secondCombatantView().findViewById<TextView>(R.id.combatTextSkillValue).text)
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
        initializeCombatWithTwoEnemies(
            enemySkill1,
            enemyStamina1,
            enemyHandicap1,
            enemySkill2,
            enemyStamina2,
            enemyHandicap2
        )
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
        loadActivity()

        fragment.findComponent<Switch>(R.id.combatType).performClick()

        assertEquals(AdventureCombatFragment.SEQUENCE, fragment.combatMode)

        fragment.findComponent<Switch>(R.id.combatType).performClick()

        assertEquals(AdventureCombatFragment.NORMAL, fragment.combatMode)
    }

    @Test
    fun `when clicking start combat it starts the combat and changes the combat layout`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertEquals(GONE, fragment.findComponent<View>(R.id.addCombatButton).visibility)
        assertEquals(GONE, fragment.findComponent<View>(R.id.combatType).visibility)
        assertEquals(GONE, fragment.findComponent<View>(R.id.startCombat).visibility)
        assertEquals(GONE, fragment.findComponent<View>(R.id.resetCombat).visibility)
        assertEquals(VISIBLE, fragment.findComponent<View>(R.id.resetCombat2).visibility)
        assertEquals(VISIBLE, fragment.findComponent<View>(R.id.testLuckButton).visibility)
        assertEquals(VISIBLE, fragment.findComponent<View>(R.id.attackButton).visibility)

        assertEquals(true, fragment.combatStarted)
    }

    @Test
    fun `when resetting before starting combat the combat positions are emptied`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        fragment.findComponent<Button>(R.id.resetCombat).performClick()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        assertTrue(fragment.combatPositions.isEmpty())

        assertEquals(0, listView.childCount)
    }

    @Test
    fun `when resetting after starting combat the combat positions are emptied and layout is switched back`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        fragment.findComponent<Button>(R.id.startCombat).performClick()
        fragment.findComponent<Button>(R.id.resetCombat).performClick()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        assertTrue(fragment.combatPositions.isEmpty())
        assertEquals(0, listView.childCount)

        assertEquals(VISIBLE, fragment.findComponent<View>(R.id.addCombatButton).visibility)
        assertEquals(VISIBLE, fragment.findComponent<View>(R.id.combatType).visibility)
        assertEquals(VISIBLE, fragment.findComponent<View>(R.id.startCombat).visibility)
        assertEquals(GONE, fragment.findComponent<View>(R.id.resetCombat).visibility)
        assertEquals(GONE, fragment.findComponent<View>(R.id.resetCombat2).visibility)
        assertEquals(GONE, fragment.findComponent<View>(R.id.testLuckButton).visibility)
        assertEquals(GONE, fragment.findComponent<View>(R.id.attackButton).visibility)
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player only combats an enemy at a time`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertTrue(fragment.combatPositions[0].isActive)
        assertFalse(fragment.combatPositions[1].isActive)

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        assertFalse(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertTrue(fragment.combatPositions[0].isActive)
        assertFalse(fragment.combatPositions[1].isActive)

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        assertFalse(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, the player combats the enemies sequentially`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }

        fragment.findComponent<Switch>(R.id.combatType).performClick()
        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertFalse(fragment.combatPositions[0].isActive)
        assertTrue(fragment.combatPositions[1].isActive)

        assertFalse(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        assertTrue(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertTrue(fragment.combatPositions[0].isActive)
        assertFalse(fragment.combatPositions[1].isActive)

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        assertFalse(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player will apply damage to the enemy if he wins the round`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        val startingStamina = fragment.combatPositions[0].currentStamina

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        val newStamina = startingStamina - fragment.damage()
        assertEquals(newStamina, fragment.combatPositions[0].currentStamina)
        assertEquals(
            newStamina.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        val newStamina2 = newStamina - fragment.damage()
        assertEquals(newStamina2, fragment.combatPositions[0].currentStamina)
        assertEquals(
            newStamina2.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player will apply bonus damage to the enemy if he wins the round and wins a luck roll`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        val firstCombatantStamina = {
            getViewByPosition(
                0,
                listView
            ).findViewById<TextView>(R.id.combatTextStaminaValue).text
        }

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertEquals(10, fragment.combatPositions[0].currentStamina)
        assertEquals("10", firstCombatantStamina())

        fragment.findComponent<Button>(R.id.testLuckButton).performClick()

        assertEquals(9, fragment.combatPositions[0].currentStamina)
        assertEquals("9", firstCombatantStamina())
    }

    @Test
    fun `when doing combat turns in normal combat mode, the player will apply diminished damage to the enemy if he wins the round and loses a luck roll`() {
        loadActivity("currentLuck" to "0")

        initializeCombatWithTwoEnemies()
        val listView = fragment.findComponent<ListView>(R.id.combatants)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        val startingStamina = fragment.combatPositions[0].currentStamina

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        val newStamina = startingStamina - fragment.damage()
        assertEquals(newStamina, fragment.combatPositions[0].currentStamina)
        assertEquals(
            newStamina.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )

        fragment.findComponent<Button>(R.id.testLuckButton).performClick()

        val newStamina2 = newStamina + 1
        assertEquals(newStamina2, fragment.combatPositions[0].currentStamina)
        assertEquals(
            newStamina2.toString(),
            getViewByPosition(0, listView).findViewById<TextView>(R.id.combatTextStaminaValue).text
        )
    }

    @Test
    fun `when doing combat turns in normal combat mode, and you win the rounds, the combat status displays the turn result`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertEquals("You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertEquals("You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, and you win the rounds, the combat status displays the turn result`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)


        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertEquals("You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertEquals("You have blocked the enemy attack! (12 + 12 + 0) vs (12 + 9)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in normal combat mode, and you lose the rounds, the combat status displays the turn result`() {
        loadActivity("currentSkill" to "1")

        initializeCombatWithTwoEnemies()
        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 8). (-2St)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, and you lose the rounds, the combat status displays the turn result`() {
        loadActivity("currentSkill" to "1")

        initializeCombatWithTwoEnemies()
        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 8). (-2St)", combatResult.text)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertEquals("You've been hit… (12 + 1 + 0) vs (12 + 9). (-2St)", combatResult.text)
    }

    @Test
    fun `when doing combat turns in normal combat mode, the active enemy is the same until it's defeated`() {
        loadActivity()

        initializeCombatWithTwoEnemies()

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6


        assertTrue(fragment.combatPositions.first().isActive)
        assertTrue(fragment.combatPositions.filter { it.isActive }.size == 1)

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertTrue(fragment.combatPositions.first().isActive)
        assertTrue(fragment.combatPositions.filter { it.isActive }.size == 1)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertTrue(fragment.combatPositions.first().isActive)
        assertTrue(fragment.combatPositions.filter { it.isActive }.size == 1)
    }

    @Test
    fun `when doing combat turns in sequence combat mode, the active enemy is the same until it's defeated`() {
        loadActivity()

        initializeCombatWithTwoEnemies()
        fragment.findComponent<TextView>(R.id.combatType).performClick()

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6


        assertTrue(fragment.combatPositions.first().isActive)

        fragment.findComponent<Button>(R.id.startCombat).performClick()

        assertTrue(fragment.combatPositions[1].isActive)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertTrue(fragment.combatPositions.first().isActive)
    }

    @Test
    fun `when advancing combat the following combatant is activated`() {
        loadActivity()

        initializeCombatWithThreeEnemies()

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val firstCombatantView = { getViewByPosition(0, listView) }
        val secondCombatantView = { getViewByPosition(1, listView) }
        val thirdCombatantView = { getViewByPosition(2, listView) }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertTrue(secondCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertTrue(thirdCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
    }

    @Test
    fun `when reducing enemy's stamina to zero, the active enemy is removed from the state and the following combatant is activated`() {
        loadActivity()

        initializeCombatWithTwoEnemies(enemyStamina1 = 2)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val firstCombatantView = { getViewByPosition(0, listView) }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)
        assertEquals(1, listView.count)
    }

    @Test
    fun `when reducing enemy's stamina to zero, the victory message is displayed`() {
        loadActivity()

        initializeCombatWithTwoEnemies(enemyStamina1 = 2)

        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        fragment.findComponent<TextView>(R.id.combatType).performClick()

        val firstCombatantView = { getViewByPosition(0, listView) }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()

        assertEquals(
            "You have hit the enemy! (12 + 12 + 0) vs (12 + 8). (-2St)\nYou have defeated an enemy!",
            combatResult.text
        )
    }

    @Test
    fun `when the combat round ends as a draw, neither the combatant nor the player lose stamina`() {
        loadActivity()

        initializeCombatWithTwoEnemies(enemySkill1 = 12, enemyStamina1 = 12)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()


        assertEquals(12, fragment.combatPositions[0].currentStamina)
        assertEquals("12", firstCombatantView().findViewById<TextView>(R.id.combatTextStaminaValue).text)

        assertEquals(24, adventure.currentStamina)
    }

    @Test
    fun `when the combat round ends as a draw, it displays the draw status message`() {
        loadActivity()

        initializeCombatWithTwoEnemies(enemySkill1 = 12, enemyStamina1 = 12)
        val combatResult = fragment.findComponent<TextView>(R.id.combatResult)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()


        assertEquals("Both you and the enemy have missed", combatResult.text)
    }

    @Test
    fun `when the player's stamina reaches zero it displays a defeat message`() {
        loadActivity("currentSkill" to "11", "currentStamina" to "2")

        initializeCombatWithTwoEnemies(enemySkill1 = 12, enemyStamina1 = 12)

        val listView = fragment.findComponent<ListView>(R.id.combatants)

        val firstCombatantView = { getViewByPosition(0, listView) }

        mockkObject(DiceRoller)
        every { DiceRoller.rollD6() } returns 6

        assertTrue(firstCombatantView().findViewById<RadioButton>(R.id.combatSelected).isChecked)

        fragment.findComponent<Button>(R.id.attackButton).performClick()


        assertEquals("You're dead…", dialog.findViewById<TextView>(android.R.id.message).text)
    }
}
