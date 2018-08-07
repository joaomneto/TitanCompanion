package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import junit.framework.Assert
import org.junit.Test
import org.robolectric.shadows.ShadowDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey
import kotlin.reflect.KClass

abstract class AdventureVitalStatsTest<T : Adventure<*>, U : AdventureVitalStatsFragment>(
    adventureClass: KClass<T>,
    fragmentClass: KClass<U>,
    savegame: String
) : TCAdventureBaseTest<T, U>(
    adventureClass, fragmentClass, savegame
) {

    @Test
    fun `when clicking the minus stamina button it decreases the stamina in the state`() {

        fragment.findComponent<Button>(R.id.minusStaminaButton).performClick()

        Assert.assertEquals(23, adventure.state.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(23.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the minus stamina button and the stamina is zero it does nothing`() {
        properties[DefaultStateKey.CURRENT_STAMINA.saveFileKey] = "0"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.minusStaminaButton).performClick()

        Assert.assertEquals(0, adventure.state.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(0.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the plus stamina button it increases the stamina in the state`() {
        properties[DefaultStateKey.CURRENT_STAMINA.saveFileKey] = "23"
        adventure.loadSavegame(properties)

        Assert.assertEquals(23, adventure.state.currentStamina)

        fragment.findComponent<Button>(R.id.plusStaminaButton).performClick()

        Assert.assertEquals(24, adventure.state.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(24.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the plus stamina button and the stamina is at max it does nothing`() {
        fragment.findComponent<Button>(R.id.plusStaminaButton).performClick()

        Assert.assertEquals(24, adventure.state.currentStamina)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(24.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking the minus skill button it decreases the skill in the state`() {

        fragment.findComponent<Button>(R.id.minusSkillButton).performClick()

        Assert.assertEquals(11, adventure.state.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(11.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the minus skill button and the skill is zero it does nothing`() {
        properties[DefaultStateKey.CURRENT_SKILL.saveFileKey] = "0"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.minusSkillButton).performClick()

        Assert.assertEquals(0, adventure.state.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(0.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the plus skill button it increases the skill in the state`() {
        properties[DefaultStateKey.CURRENT_SKILL.saveFileKey] = "11"
        adventure.loadSavegame(properties)

        Assert.assertEquals(11, adventure.state.currentSkill)

        fragment.findComponent<Button>(R.id.plusSkillButton).performClick()

        Assert.assertEquals(12, adventure.state.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(12.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the plus skill button and the skill is at max it does nothing`() {
        fragment.findComponent<Button>(R.id.plusSkillButton).performClick()

        Assert.assertEquals(12, adventure.state.currentSkill)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(12.toString(), skillValue.text)
    }

    @Test
    fun `when clicking the minus luck button it decreases the luck in the state`() {

        fragment.findComponent<Button>(R.id.minusSkillButton).performClick()

        Assert.assertEquals(11, adventure.state.currentSkill)

        val luckValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(11.toString(), luckValue.text)
    }

    @Test
    fun `when clicking the minus luck button and the luck is zero it does nothing`() {
        properties[DefaultStateKey.CURRENT_LUCK.saveFileKey] = "0"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.minusLuckButton).performClick()

        Assert.assertEquals(0, adventure.state.currentLuck)

        val luckValue = fragment.findComponent<Button>(R.id.statsLuckValue)

        Assert.assertEquals(0.toString(), luckValue.text)
    }

    @Test
    fun `when clicking the plus luck button it increases the luck in the state`() {
        properties[DefaultStateKey.CURRENT_LUCK.saveFileKey] = "11"
        adventure.loadSavegame(properties)

        Assert.assertEquals(11, adventure.state.currentLuck)

        fragment.findComponent<Button>(R.id.plusLuckButton).performClick()

        Assert.assertEquals(12, adventure.state.currentLuck)

        val luckValue = fragment.findComponent<Button>(R.id.statsLuckValue)

        Assert.assertEquals(12.toString(), luckValue.text)
    }

    @Test
    fun `when clicking the plus luck button and the luck is at max it does nothing`() {
        fragment.findComponent<Button>(R.id.plusLuckButton).performClick()

        Assert.assertEquals(12, adventure.state.currentLuck)

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

        Assert.assertEquals(50, adventure.state.initialStamina)
    }

    @Test
    fun `when clicking the stamina value it does nothing when canceling the dialog`() {
        fragment.findComponent<Button>(R.id.statsStaminaValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()

        Assert.assertEquals(24, adventure.state.initialStamina)
    }

    @Test
    fun `when clicking the skill value it changes the skill max value via dialog`() {
        fragment.findComponent<Button>(R.id.statsSkillValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertEquals(50, adventure.state.initialSkill)
    }

    @Test
    fun `when clicking the skill value it does nothing when canceling the dialog`() {
        fragment.findComponent<Button>(R.id.statsSkillValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()

        Assert.assertEquals(12, adventure.state.initialSkill)
    }

    @Test
    fun `when clicking the luck value it changes the luck max value via dialog`() {
        fragment.findComponent<Button>(R.id.statsLuckValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertEquals(50, adventure.state.initialLuck)
    }

    @Test
    fun `when clicking the luck value it does nothing when canceling the dialog`() {
        fragment.findComponent<Button>(R.id.statsLuckValue).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("50")
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()

        Assert.assertEquals(12, adventure.state.initialLuck)
    }

    @Test
    fun `when clicking the minus provisions button it decreases the provisions in the state`() {
        if (adventure.state.provisionsValue <= 0)
            return

        fragment.findComponent<Button>(R.id.minusProvisionsButton).performClick()

        Assert.assertEquals(9, adventure.state.provisions)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)

        Assert.assertEquals(9.toString(), provisionsValue.text)
    }

    @Test
    fun `when clicking the minus provisions button and the provisions is zero it does nothing`() {
        if (adventure.state.provisionsValue <= 0)
            return

        properties[DefaultStateKey.PROVISIONS.saveFileKey] = "0"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.minusProvisionsButton).performClick()

        Assert.assertEquals(0, adventure.state.provisions)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)

        Assert.assertEquals(0.toString(), provisionsValue.text)
    }

    @Test
    fun `when clicking the plus provisions button it increases the provisions in the state`() {
        if (adventure.state.provisionsValue <= 0)
            return

        fragment.findComponent<Button>(R.id.plusProvisionsButton).performClick()

        Assert.assertEquals(11, adventure.state.provisions)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)

        Assert.assertEquals(11.toString(), provisionsValue.text)
    }

    @Test
    fun `when clicking consume provisions it increases the current stamina by the provisions value and decreases the provisions`() {
        if (adventure.state.provisionsValue <= 0)
            return

        properties[DefaultStateKey.CURRENT_STAMINA.saveFileKey] = "10"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.buttonConsumeProvisions).performClick()

        Assert.assertEquals(9, adventure.state.provisions)
        Assert.assertEquals(10 + adventure.state.provisionsValue, adventure.state.currentStamina)

        val provisionsValue = fragment.findComponent<TextView>(R.id.provisionsValue)
        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(9.toString(), provisionsValue.text)
        Assert.assertEquals(14.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking use Strenght potion, the stamina is restored to max level`() {
        if (adventure.state.standardPotion <= 0)
            return

        properties[DefaultStateKey.CURRENT_STAMINA.saveFileKey] = "10"
        properties[DefaultStateKey.STANDARD_POTION.saveFileKey] = "1"
        properties[DefaultStateKey.STANDARD_POTION_VALUE.saveFileKey] = "2"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.usePotionButton).performClick()

        Assert.assertEquals(24, adventure.state.currentStamina)
        Assert.assertEquals(1, adventure.state.standardPotionValue)

        val staminaValue = fragment.findComponent<Button>(R.id.statsStaminaValue)

        Assert.assertEquals(24.toString(), staminaValue.text)
    }

    @Test
    fun `when clicking use Skill potion, the skill is restored to max level`() {
        if (adventure.state.standardPotion <= 0)
            return

        properties[DefaultStateKey.CURRENT_SKILL.saveFileKey] = "10"
        properties[DefaultStateKey.STANDARD_POTION.saveFileKey] = "0"
        properties[DefaultStateKey.STANDARD_POTION_VALUE.saveFileKey] = "2"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.usePotionButton).performClick()

        Assert.assertEquals(12, adventure.state.currentSkill)
        Assert.assertEquals(1, adventure.state.standardPotionValue)

        val skillValue = fragment.findComponent<Button>(R.id.statsSkillValue)

        Assert.assertEquals(12.toString(), skillValue.text)
    }

    @Test
    fun `when clicking use Fortune potion, the initial luck is increased by 1 and the current luck is restored to max level`() {
        if (adventure.state.standardPotion <= 0)
            return

        properties[DefaultStateKey.CURRENT_LUCK.saveFileKey] = "10"
        properties[DefaultStateKey.STANDARD_POTION.saveFileKey] = "2"
        properties[DefaultStateKey.STANDARD_POTION_VALUE.saveFileKey] = "2"
        adventure.loadSavegame(properties)

        fragment.findComponent<Button>(R.id.usePotionButton).performClick()

        Assert.assertEquals(13, adventure.state.currentLuck)
        Assert.assertEquals(13, adventure.state.initialLuck)
        Assert.assertEquals(1, adventure.state.standardPotionValue)

        val luckValue = fragment.findComponent<Button>(R.id.statsLuckValue)

        Assert.assertEquals(13.toString(), luckValue.text)
    }
}
