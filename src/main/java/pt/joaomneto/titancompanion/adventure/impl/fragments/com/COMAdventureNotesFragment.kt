package pt.joaomneto.titancompanion.adventure.impl.fragments.com

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_30com_adventure_notes.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.COMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

class COMAdventureNotesFragment : AdventureNotesFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        baseLayout = R.layout.fragment_30com_adventure_notes

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.activity as COMAdventure

        buttonAddCypher.setOnClickListener(OnClickListener {
            val alert = AlertDialog.Builder(adv)

            alert.setTitle(R.string.note)

            // Set an EditText view to get user input
            val input = EditText(adv)
            val imm = adv.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
            input.requestFocus()
            alert.setView(input)

            alert.setPositiveButton(R.string.ok,
                { _, _ ->
                    val value = input.text.toString()
                    if (value.isNotBlank()) {
                        adv.cyphers = adv.cyphers.plus(value.trim { it <= ' ' })
                        (cypherList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                    }
                })

            alert.setNegativeButton(
                R.string.cancel
            ) { _, _ ->
                // Canceled.
            }

            alert.show()
        })

        val adapter = ArrayAdapter(
            adv,
            android.R.layout.simple_list_item_1, android.R.id.text1, adv.cyphers
        )
        cypherList.adapter = adapter

        cypherList.onItemLongClickListener = OnItemLongClickListener { _, _, arg2, _ ->
            val builder = AlertDialog.Builder(adv)
            builder.setTitle(R.string.deleteKeyword)
                .setCancelable(false)
                .setNegativeButton(
                    R.string.close
                ) { dialog, _ -> dialog.cancel() }
            builder.setPositiveButton(R.string.ok) { _, _ ->
                adv.cyphers = adv.cyphers.minus(adv.cyphers[arg2])
                (cypherList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
            }

            val alert = builder.create()
            alert.show()
            true
        }
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        (cypherList.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }
}
