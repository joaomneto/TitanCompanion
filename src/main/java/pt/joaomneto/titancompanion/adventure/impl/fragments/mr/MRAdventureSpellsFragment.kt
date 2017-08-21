package pt.joaomneto.titancompanion.adventure.impl.fragments.mr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_08ss_adventure_spells.*
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment

/**
 * Created by 962633 on 21-08-2017.
 */
class MRAdventureSpellsFragment : AdventureSpellsFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        addSpellButton?.visibility = View.GONE
        chooseSpellSpinner?.visibility = View.GONE
        separator?.visibility = View.GONE
        spellList?.onItemClickListener = null

        return view
    }
}