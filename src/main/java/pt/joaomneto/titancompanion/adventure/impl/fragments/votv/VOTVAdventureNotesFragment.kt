package pt.joaomneto.titancompanion.adventure.impl.fragments.votv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_38votv_adventure_notes.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.VOTVAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

class VOTVAdventureNotesFragment : AdventureNotesFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_38votv_adventure_notes, container, false
        )
    }

    val adventure
        get() = this.activity as VOTVAdventure

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        afflictionList.adapter = ArrayAdapter(
            adventure,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            adventure.afflictions
        )

        buttonAddAffliction.setOnClickListener(adventure.addAfflictionListener(afflictionList))
        afflictionList.setOnItemLongClickListener(adventure.removeAfflictionListener(afflictionList))
    }
}
