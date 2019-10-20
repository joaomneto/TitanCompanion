package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment

open class AdventureNotesFragment : AdventureFragment() {

    internal var noteList: ListView? = null

    init {
        baseLayout = R.layout.fragment_adventure_notes
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(
            baseLayout,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = activity as Adventure?

        noteList = view.findViewById(R.id.noteList)
        val buttonAddNote = view
            .findViewById<Button>(R.id.buttonAddNote)


        buttonAddNote.setOnClickListener {
            synchronized(adv!!.notes) {
                val alert = AlertDialog.Builder(adv)

                alert.setTitle(R.string.note)

                // Set an EditText view to get user input
                val input = EditText(adv)
                val imm = adv.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
                input.requestFocus()
                alert.setView(input)

                alert.setPositiveButton(
                    R.string.ok
                ) { dialog, whichButton ->
                    synchronized(adv.notes) {
                        val value = input.text.toString()
                        if (value.isEmpty())
                            return@alert.setPositiveButton
                        adv.notes.add(value.trim { it <= ' ' })
                        (noteList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                    }
                }

                alert.setNegativeButton(
                    R.string.cancel
                ) { dialog, whichButton ->
                    // Canceled.
                }

                alert.show()
            }
        }

        val adapter = ArrayAdapter(
            adv!!,
            android.R.layout.simple_list_item_1, android.R.id.text1, adv.notes
        )
        noteList!!.adapter = adapter

        noteList!!.setOnItemLongClickListener { arg0, arg1, position, arg3 ->
            synchronized(adv.notes) {
                val builder = AlertDialog.Builder(adv)
                builder.setTitle(R.string.deleteNote)
                    .setCancelable(false)
                    .setNegativeButton(
                        R.string.close
                    ) { dialog, id -> dialog.cancel() }
                builder.setPositiveButton(R.string.ok) { dialog, which ->
                    if (adv.notes.size > position) {
                        adv.notes.removeAt(position)
                    }
                    (noteList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                }

                val alert = builder.create()
                alert.show()
                return@noteList.setOnItemLongClickListener true
            }

        }
    }

    override fun refreshScreensFromResume() {

        (noteList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }
}
