package pt.joaomneto.titancompanion.adventure.impl.fragments.com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_30com_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.COMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

/**
 * Created by joao.neto on 10/17/17.
 */

class COMAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(
            R.layout.fragment_30com_adventure_vitalstats, container, false)

        initialize(rootView)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.context as COMAdventure

        buttonConsumeProvisions.setOnClickListener { v -> adv.consumeProvision(v) }
        buttonTestLuck.setOnClickListener { v -> adv.testLuck(v) }
        buttonTestSkill.setOnClickListener { v -> adv.testSkill(v) }
        buttonSavePoint.setOnClickListener { v -> adv.savepoint(v) }
        plusFuelButton.setOnClickListener({
            adv.fuel = adv.fuel + 1
            refreshScreensFromResume()
        })
        minusFuelButton.setOnClickListener({
            adv.fuel = Math.max(0, adv.fuel - 1)
            refreshScreensFromResume()
        })
        plusTabashaButton.setOnClickListener {
            adv.tabasha = Math.min(9, adv.tabasha + 1)
            refreshScreensFromResume()
        }
        minusTabashaButton.setOnClickListener {
            adv.tabasha = Math.max(0, adv.tabasha - 1)
            refreshScreensFromResume()
        }
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()

        val adv = this.context as COMAdventure

        fuelValue.text = "${adv.fuel}"
        tabashaValue.text = "${adv.tabasha}"

    }

}