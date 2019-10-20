package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment

open class AdventureEquipmentFragment : AdventureFragment() {

    internal var equipmentList: ListView? = null
    internal var minusGoldButton: Button? = null
    internal var plusGoldButton: Button? = null
    internal var goldValue: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_adventure_equipment, container, false)
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        val adv = activity as Adventure?

        equipmentList = rootView.findViewById(R.id.equipmentList)
        minusGoldButton = rootView.findViewById(R.id.minusGoldButton)
        plusGoldButton = rootView.findViewById(R.id.plusGoldButton)

        val buttonAddNote = rootView.findViewById<Button>(R.id.buttonAddEquipment)

        val goldLabel = rootView.findViewById<TextView>(R.id.goldLabel)
        goldLabel.setText(adv!!.currencyName)

        goldValue = rootView.findViewById(R.id.goldValue)
        goldValue!!.text = adv.gold.toString() + ""

        goldValue!!.setOnClickListener {
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
                adv.gold = value
                goldValue!!.text = "" + value
            }

            alert.setNegativeButton(
                R.string.cancel
            ) { dialog, whichButton -> imm.hideSoftInputFromWindow(input.windowToken, 0) }

            alert.show()
        }

        buttonAddNote.setOnClickListener(OnClickListener {
            val alert = AlertDialog.Builder(adv)

            alert.setTitle(R.string.equipment2)

            // Set an EditText view to get user input
            val input = EditText(adv)
            val imm = adv.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
            input.requestFocus()
            alert.setView(input)

            alert.setPositiveButton(
                R.string.ok,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    val value = input.text.toString()
                    if (value.isEmpty())
                        return@OnClickListener
                    adv.equipment.add(value)
                    (equipmentList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                    refreshScreensFromResume()
                })

            alert.setNegativeButton(R.string.cancel) { dialog, whichButton ->
                // Canceled.
            }

            alert.show()
        })

        val adapter = ArrayAdapter(
            adv, android.R.layout.simple_list_item_1,
            android.R.id.text1, adv.equipment
        )
        equipmentList!!.adapter = adapter

        equipmentList!!.onItemLongClickListener =
            OnItemLongClickListener { arg0, arg1, arg2, arg3 ->
                val builder = AlertDialog.Builder(adv)
                builder.setTitle(R.string.deleteEquipmentQuestion).setCancelable(false)
                    .setNegativeButton(R.string.close) { dialog, id -> dialog.cancel() }
                builder.setPositiveButton(R.string.ok) { dialog, which ->
                    adv.equipment.removeAt(arg2)
                    (equipmentList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                }

                val alert = builder.create()
                alert.show()
                true
            }

        minusGoldButton!!.setOnClickListener {
            adv.gold = Math.max(adv.gold - 1, 0)
            refreshScreensFromResume()
        }

        plusGoldButton!!.setOnClickListener {
            adv.gold = adv.gold + 1
            refreshScreensFromResume()
        }
    }

    override fun refreshScreensFromResume() {
        val adv = activity as Adventure?
        (equipmentList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
        goldValue!!.text = "" + adv!!.gold
    }
}
