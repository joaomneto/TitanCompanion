package pt.joaomneto.titancompanion.adventure.impl.fragments.strider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_27strider_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.STRIDERAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class STRIDERAdventureVitalStatsFragment : AdventureVitalStatsFragment() {
    internal var fearValue: TextView? = null

    internal var increaseFearButton: Button? = null

    internal var decreaseFearButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(
            R.layout.fragment_27strider_adventure_vitalstats, container, false
        )

        //CHECKTHIS	initialize(rootView);

        fearValue = rootView.findViewById(R.id.statsFearValue)

        refreshScreensFromResume()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTestFear.setOnClickListener({
                (this.activity as STRIDERAdventure).testFear()
        })
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as STRIDERAdventure?
        fearValue!!.text = "" + adv!!.currentFear
    }
}
