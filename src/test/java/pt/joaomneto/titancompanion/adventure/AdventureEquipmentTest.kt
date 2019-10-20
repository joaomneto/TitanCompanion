package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import junit.framework.Assert
import org.junit.Test
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import java.util.Properties
import kotlin.reflect.KClass

abstract class AdventureEquipmentTest<T : Adventure, U : AdventureEquipmentFragment>(
    adventureClass: KClass<T>,
    fragmentClass: KClass<U>,
    private val savegame: Properties
) : TCAdventureBaseTest<T, U>(
    adventureClass, fragmentClass, savegame
) {

    @Test
    fun `when clicking the minus gold button it decreases the gold in the state`() {

        fragment.findComponent<Button>(R.id.minusGoldButton).performClick()

        Assert.assertEquals(9, adventure.gold)

        val goldValue = fragment.findComponent<TextView>(R.id.goldValue)

        Assert.assertEquals(9.toString(), goldValue.text)
    }

    @Test
    fun `when clicking the minus gold button and the gold is zero it does nothing`() {
        loadSpecificvaluesToState("gold" to "0")

        fragment.findComponent<Button>(R.id.minusGoldButton).performClick()

        Assert.assertEquals(0, adventure.gold)

        val goldValue = fragment.findComponent<TextView>(R.id.goldValue)

        Assert.assertEquals(0.toString(), goldValue.text)
    }

    @Test
    fun `when clicking the plus gold button it increases the gold in the state`() {
        loadSpecificvaluesToState("gold" to "23")

        Assert.assertEquals(23, adventure.gold)

        fragment.findComponent<Button>(R.id.plusGoldButton).performClick()

        Assert.assertEquals(24, adventure.gold)

        val goldValue = fragment.findComponent<TextView>(R.id.goldValue)

        Assert.assertEquals(24.toString(), goldValue.text)
    }

    @Test
    fun `when clicking the add equipment button it adds an equipment to the list via a dialog`() {
        fragment.findComponent<Button>(R.id.buttonAddEquipment).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("eq1")
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        val listView = fragment.findComponent<ListView>(R.id.equipmentList)

        val shadowListView = Shadows.shadowOf(listView)

        Assert.assertTrue(shadowListView.findIndexOfItemContainingText("eq1") >= 0)
    }

    @Test
    fun `when long pressing an equipment item it removes an item from the list via a confirmation dialog`() {
        loadSpecificvaluesToState("equipment" to "eq1#eq2")

        val listView = fragment.findComponent<ListView>(R.id.equipmentList)
        val shadowListView = Shadows.shadowOf(listView)

        listView.onItemLongClickListener.onItemLongClick(
            null,
            null,
            shadowListView.findIndexOfItemContainingText("eq1"),
            -1
        )

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertTrue(shadowListView.findIndexOfItemContainingText("eq1") < 0)
    }
}
