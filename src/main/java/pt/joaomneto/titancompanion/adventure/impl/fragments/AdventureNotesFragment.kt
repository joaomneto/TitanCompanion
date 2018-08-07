package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_adventure_notes.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.adapter.ArrayGeneratorAdapter

class AdventureNotesFragment : AdventureFragment() {

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

        val adv = activity as Adventure<*>


        buttonAddNote.setOnClickListener {
            adv.performAddNoteWithAlert(this)
        }

        noteList.adapter = ArrayGeneratorAdapter(
            adv,
            android.R.layout.simple_list_item_1, android.R.id.text1, { adv.state.notes }
        )

        noteList.setOnItemLongClickListener { _, _, position, _ ->
            adv.performRemoveNoteWithAlert(this, position)
        }
    }

    override fun refreshScreen() {
        getAdapter().notifyDataSetChanged()
    }

    private fun getAdapter() = (noteList.adapter as ArrayGeneratorAdapter<String>)
}
