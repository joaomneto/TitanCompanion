package pt.joaomneto.titancompanion.adventure.impl.fragments.mr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_adventure_spells_chooser.*
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment

/**
 * Created by 962633 on 21-08-2017.
 */
class MRAdventureSpellsFragment : AdventureSpellsFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        spellChooserLayout?.visibility = View.GONE
        spellChooserSeparator?.visibility = View.GONE
        spellList?.onItemClickListener = null

        return view
    }
}