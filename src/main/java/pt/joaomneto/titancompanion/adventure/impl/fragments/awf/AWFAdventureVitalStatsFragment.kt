package pt.joaomneto.titancompanion.adventure.impl.fragments.awf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.AWFAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class AWFAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    internal var heroPoints: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(
            R.layout.fragment_17awf_adventure_vitalstats, container, false
        )

        //CHECKTHIS	initialize(rootView);

        heroPoints = rootView.findViewById<View>(R.id.statsHeroPointsValue) as TextView
        val adv = activity as AWFAdventure

        setupIncDecButton(
            rootView,
            R.id.plusHeroPointsButton,
            R.id.minusHeroPointsButton,
            adv,
            AWFAdventure::heroPoints,
            999
        )

        refreshScreensFromResume()

        return rootView
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as AWFAdventure
        heroPoints!!.text = "" + adv.heroPoints
    }
}
