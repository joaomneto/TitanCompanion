package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import junit.framework.Assert
import org.junit.Test
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey
import kotlin.reflect.KClass

abstract class AdventureNotesTest<T : Adventure<*>, U : AdventureNotesFragment>(
    adventureClass: KClass<T>,
    fragmentClass: KClass<U>,
    savegame: String
) : TCAdventureBaseTest<T, U>(
    adventureClass, fragmentClass, savegame
) {

    @Test
    fun `when clicking the add note button it adds an note to the list via a dialog`() {
        fragment.findComponent<Button>(R.id.buttonAddNote).performClick()

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        val inputField = dialog.findViewById<EditText>(R.id.alert_editText_field)

        inputField.setText("n1")
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        val listView = fragment.findComponent<ListView>(R.id.noteList)

        val shadowListView = Shadows.shadowOf(listView)

        Assert.assertTrue(shadowListView.findIndexOfItemContainingText("n1") >= 0)
    }

    @Test
    fun `when long pressing an note item it removes an item from the list via a confirmation dialog`() {
        properties[DefaultStateKey.NOTES.saveFileKey] = "n1#n2"
        adventure.loadSavegame(properties)
        fragment.refreshScreen()

        val listView = fragment.findComponent<ListView>(R.id.noteList)
        val shadowListView = Shadows.shadowOf(listView)

        listView.onItemLongClickListener.onItemLongClick(
            null,
            null,
            shadowListView.findIndexOfItemContainingText("n1"),
            -1
        )

        val dialog = ShadowDialog.getLatestDialog() as AlertDialog
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()

        Assert.assertTrue(shadowListView.findIndexOfItemContainingText("n1") < 0)
    }
}
