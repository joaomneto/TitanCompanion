package pt.joaomneto.titancompanion.adventure.impl.fragments.sl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_33sl_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.SLAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class SLAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)


        return inflater!!.inflate(
            R.layout.fragment_33sl_adventure_vitalstats, container, false
        )
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        val adv = activity as SLAdventure

        minusOxygenButton.setOnClickListener {
            adv.oxygen = maxOf(0, adv.oxygen - 1)
            refreshScreensFromResume()
        }

        plusOxygenButton.setOnClickListener {
            adv.oxygen = minOf(adv.oxygen + 1, 10)
            refreshScreensFromResume()
        }
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as SLAdventure
        statsOxygenValue?.text = adv.oxygen.toString()
    }
}