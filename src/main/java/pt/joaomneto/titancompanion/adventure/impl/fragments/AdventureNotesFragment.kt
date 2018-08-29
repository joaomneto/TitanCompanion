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

class AdventureNotesFragment: AdventureFragment<Adventure<*, *, *>>() {

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

        buttonAddNote.setOnClickListener {
            adventure.performAddNoteWithAlert()
        }

        noteList.adapter = ArrayGeneratorAdapter(
            adventure,
            android.R.layout.simple_list_item_1, android.R.id.text1
        ) { adventure.state.notes }

        noteList.setOnItemLongClickListener { _, _, position, _ ->
            adventure.performRemoveNoteWithAlert(position)
        }
    }

    override fun refreshScreen() {
        getAdapter().notifyDataSetChanged()
    }

    private fun getAdapter() = (noteList.adapter as ArrayGeneratorAdapter<String>)
}
