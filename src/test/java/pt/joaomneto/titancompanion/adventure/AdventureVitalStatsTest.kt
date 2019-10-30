package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.junit.Assert
import org.junit.Test
import org.robolectric.shadows.ShadowDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import java.util.Properties
import kotlin.reflect.KClass

abstract class AdventureVitalStatsTest<T : Adventure, U : AdventureVitalStatsFragment>(
    adventureClass: KClass<T>,
    fragmentClass: KClass<U>,
    savegame: Properties
) : TCAdventureBaseTest<T, U>(
    adventureClass, fragmentClass, savegame
) {

    @Test
    fun `when clicking the minus stamina button it decreases the stamina in the state`() {

        fragment.findComponent<Button>(R.id.minusStaminaButton).performClick()

        Assert.assertEquals(23, adventure.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(23.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the minus stamina button and the stamina is zero it does nothing`() {
        loadSpecificvaluesToState("currentStamina" to "0")

        fragment.findComponent<Button>(R.id.minusStaminaButton).performClick()

        Assert.assertEquals(0, adventure.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(0.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the plus stamina button it increases the stamina in the state`() {
        loadSpecificvaluesToState("currentStamina" to "23")

        Assert.assertEquals(23, adventure.currentStamina)

        fragment.findComponent<Button>(R.id.plusStaminaButton).performClick()

        Assert.assertEquals(24, adventure.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(24.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the plus stamina button and the stamina is at max it does nothing`() {
        fragment.findComponent<Button>(R.id.plusStaminaButton).performClick()

        Assert.assertEquals(24, adventure.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(24.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the minus skill button it decreases the skill in the state`() {

        fragment.findComponent<Button>(R.id.minusSkillButton).performClick()

        Assert.assertEquals(11, adventure.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(11.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the minus skill button and the skill is zero it does nothing`() {
        loadSpecificvaluesToState("currentSkill" to "0")

        fragment.findComponent<Button>(R.id.minusSkillButton).performClick()

        Assert.assertEquals(0, adventure.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(0.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the plus skill button it increases the skill in the state`() {
        loadSpecificvaluesToState("currentSkill" to "11")

        Assert.assertEquals(11, adventure.currentSkill)

        fragment.findComponent<Button>(R.id.plusSkillButton).performClick()

        Assert.assertEquals(12, adventure.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(12.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the plus skill button and the skill is at max it does nothing`() {
        fragment.findComponent<Button>(R.id.plusSkillButton).performClick()

        Assert.assertEquals(12, adventure.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(12.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the minus luck button it decreases the luck in the state`() {

        fragment.findComponent<Button>(R.id.minusSkillButton).performClick()

        Assert.assertEquals(11, adventure.currentSkill)

        val luckValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(11.toString(), luckValue.text)
    }

    @Test
    fun `when clicking the minus luck button and the luck is zero it does nothing`() {
        loadSpecificvaluesToState("currentLuck" to "0")

        fragment.findComponent<Button>(R.id.minusLuckButton).performClick()

        Assert.assertEquals(0, adventure.currentLuck)

        val luckValue = fragment.findComponent<Button>(R.id.statsLuckValue)

        Assert.assertEquals(0.toString(), luckValue.text)
    }

    @Test
    fun `when clicking the plus luck button it increases the luck in the state`() {
        loadSpecificvaluesToState("currentLuck" to "11")

        Assert.assertEquals(11, adventure.currentLuck)

        fragment.findComponent<Button>(R.id.plusLuckButton).performClick()

        Assert.assertEquals(12, adventure.currentLuck)

        val luckValue = fragment.findComponent<Button>(R.id.statsLuckValue)

        Assert.assertEquals(12.toString(), luckValue.text)
    }

    @Test
    fun `when clicking the plus luck button and the luck is at max it does nothing`() {
        fragment.findComponent<Button>(R.id.plusLuckButton).performClick()

        Assert.assertEquals(12, adventure.currentLuck)

        val luckValue = fragment.findComponent<Button>(R.id.statsLuckValue)

        Assert.assertEquals(12.toString(), luckValue.text)
    }

    @Test
    fun `when clicking the stamina value it changes the stamina max value via dialog`() {
        fragment.findComponent<Button>(R.id.statsStaminaValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertEquals(50, adventure.initialStamina)
    }

    @Test
    fun `when clicking the stamina value it does nothing when canceling the dialog`() {
        fragment.findComponent<Button>(R.id.statsStaminaValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()

        Assert.assertEquals(24, adventure.initialStamina)
    }

    @Test
    fun `when clicking the skill value it changes the skill max value via dialog`() {
        fragment.findComponent<Button>(R.id.statsSkillValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertEquals(50, adventure.initialSkill)
    }

    @Test
    fun `when clicking the skill value it does nothing when canceling the dialog`() {
        fragment.findComponent<Button>(R.id.statsSkillValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()

        Assert.assertEquals(12, adventure.initialSkill)
    }

    @Test
    fun `when clicking the luck value it changes the luck max value via dialog`() {
        fragment.findComponent<Button>(R.id.statsLuckValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertEquals(50, adventure.initialLuck)
    }

    @Test
    fun `when clicking the luck value it does nothing when canceling the dialog`() {
        fragment.findComponent<Button>(R.id.statsLuckValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()

        Assert.assertEquals(12, adventure.initialLuck)
    }

    @Test
    fun `when clicking the minus provisions button it decreases the provisions in the state`() {
        fragment.findComponent<Button>(R.id.minusProvisionsButton).performClick()

        Assert.assertEquals(9, adventure.provisions)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)

        Assert.assertEquals(9.toString(), provisionsValue.text)
    }

    @Test
    fun `when clicking the minus provisions button and the provisions is zero it does nothing`() {
        loadSpecificvaluesToState("provisions" to "0")

        fragment.findComponent<Button>(R.id.minusProvisionsButton).performClick()

        Assert.assertEquals(0, adventure.provisions)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)

        Assert.assertEquals(0.toString(), provisionsValue.text)
    }

    @Test
    fun `when clicking the plus provisions button it increases the provisions in the state`() {
        fragment.findComponent<Button>(R.id.plusProvisionsButton).performClick()

        Assert.assertEquals(11, adventure.provisions)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)

        Assert.assertEquals(11.toString(), provisionsValue.text)
    }

    @Test
    fun `when clicking consume provisions it increases the current stamina by the provisions value and decreases the provisions`() {
        loadSpecificvaluesToState("currentStamina" to "10")

        fragment.findComponent<Button>(R.id.buttonConsumeProvisions).performClick()

        Assert.assertEquals(9, adventure.provisions)
        Assert.assertEquals(10 + adventure.provisionsValue, adventure.currentStamina)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)
        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(9.toString(), provisionsValue.text)
        Assert.assertEquals(14.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking use Strenght potion, the stamina is restored to max level`() {
        loadSpecificvaluesToState(
            "currentStamina" to "10",
            "standardPotion" to "1",
            "standardPotionValue" to "2"
        )

        fragment.findComponent<Button>(R.id.usePotionButton).performClick()

        Assert.assertEquals(24, adventure.currentStamina)
        Assert.assertEquals(1, adventure.standardPotionValue)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(24.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking use Skill potion, the skill is restored to max level`() {
        loadSpecificvaluesToState(
            "currentSkill" to "10",
            "standardPotion" to "0",
            "standardPotionValue" to "2"
        )

        fragment.findComponent<Button>(R.id.usePotionButton).performClick()

        Assert.assertEquals(12, adventure.currentSkill)
        Assert.assertEquals(1, adventure.standardPotionValue)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(12.toString(), skillValue.text)
    }

    @Test
    fun `when clicking use Fortune potion, the initial luck is increased by 1 and the current luck is restored to max level`() {
        loadSpecificvaluesToState(
            "currentLuck" to "10",
            "standardPotion" to "2",
            "standardPotionValue" to "2"
        )

        fragment.findComponent<Button>(R.id.usePotionButton).performClick()

        Assert.assertEquals(13, adventure.currentLuck)
        Assert.assertEquals(13, adventure.initialLuck)
        Assert.assertEquals(1, adventure.standardPotionValue)

        val luckValue = fragment.findComponent<Button>(R.id.statsLuckValue)

        Assert.assertEquals(13.toString(), luckValue.text)
    }
}
