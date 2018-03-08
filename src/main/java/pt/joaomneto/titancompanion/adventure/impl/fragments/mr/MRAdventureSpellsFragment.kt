package pt.joaomneto.titancompanion.adventure.impl.fragments.mr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment

/**
 * Created by 962633 on 21-08-2017.
 */
class MRAdventureSpellsFragment : AdventureSpellsFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        view?.findViewById<LinearLayout>(R.id.spellChooserLayout)?.visibility = View.GONE
        view?.findViewById<View>(R.id.spellChooserSeparator)?.visibility = View.GONE
        view?.findViewById<ListView>(R.id.spellList)?.onItemClickListener = null

        return view
    }
}