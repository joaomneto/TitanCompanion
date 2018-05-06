package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tpop

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.TPOPAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment

class TPOPAdventureEquipmentFragment : AdventureEquipmentFragment() {

    internal var minusCopperButton: Button? = null
    internal var plusCopperButton: Button? = null
    internal var copperValue: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_63tpop_adventure_equipment, container, false)
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        val adv = activity as TPOPAdventure

        minusCopperButton = rootView.findViewById(R.id.minusCopperButton)
        plusCopperButton = rootView.findViewById(R.id.plusCopperButton)


        copperValue = rootView.findViewById(R.id.copperValue)
        copperValue!!.text = adv.copper.toString()

        copperValue!!.setOnClickListener {
            val alert = AlertDialog.Builder(adv)

            alert.setTitle(R.string.setValue)

            // Set an EditText view to get user input
            val input = EditText(adv)
            val imm = adv
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            input.inputType = InputType.TYPE_CLASS_NUMBER
            input.requestFocus()
            alert.setView(input)

            alert.setPositiveButton(R.string.ok) { dialog, whichButton ->
                val value = Integer.parseInt(input.text.toString())
                adv.copper = value
                copperValue!!.text = "" + value
            }

            alert.setNegativeButton(
                R.string.cancel
            ) { dialog, whichButton -> imm.hideSoftInputFromWindow(input.windowToken, 0) }

            alert.show()
        }


        minusCopperButton!!.setOnClickListener {
            adv.copper = Math.max(adv.copper - 1, 0)
            refreshScreensFromResume()
        }

        plusCopperButton!!.setOnClickListener {
            adv.copper = adv.copper + 1
            refreshScreensFromResume()
        }
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as TPOPAdventure?
        copperValue!!.text = "" + adv!!.copper
    }
}
