package pt.joaomneto.titancompanion.adventure.impl.fragments.s

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_53s_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.SAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class SAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(
                R.layout.fragment_53s_adventure_vitalstats, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = activity as SAdventure

        minusFaithButton.setOnClickListener {
            if (adv.currentFaith > 0)
                adv.currentFaith = adv.currentFaith - 1
            refreshScreensFromResume()
        }

        plusFaithButton.setOnClickListener {
            adv.currentFaith = adv.currentFaith + 1
            refreshScreensFromResume()
        }


        minusInfectionButton.setOnClickListener {
            if (adv.currentInfection > 0)
                adv.currentInfection = adv.currentInfection - 1
            refreshScreensFromResume()
        }

        plusInfectionButton.setOnClickListener {
            adv.currentInfection = Math.min(adv.currentInfection + 1, 15)
            if(adv.currentInfection == 15){
                Adventure.showAlert(getString(R.string.goToParagraph, 13), adv)
            }
            refreshScreensFromResume()
        }

        refreshScreensFromResume()
    }


    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as SAdventure
        statsFaithValue.text = adv.currentFaith.toString()
        statsInfectionValue.text = adv.currentInfection.toString()
    }
}
