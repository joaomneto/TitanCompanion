package pt.joaomneto.titancompanion.adventure.impl.fragments.god

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_64god_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.GODAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class GODAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(
                R.layout.fragment_64god_adventure_vitalstats, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val godAdventure = activity as GODAdventure

        minusSmokeOilButton.setOnClickListener {
            godAdventure.smokeOil = Math.max(0, godAdventure.smokeOil-1)
            refreshScreensFromResume()
        }

        plusSmokeOilButton.setOnClickListener {
            godAdventure.smokeOil++
            refreshScreensFromResume()
        }
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        smokeOilValue?.text = "${(activity as GODAdventure).smokeOil}"
    }

}