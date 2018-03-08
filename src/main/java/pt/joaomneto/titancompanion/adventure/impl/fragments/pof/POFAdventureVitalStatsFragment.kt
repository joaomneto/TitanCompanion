package pt.joaomneto.titancompanion.adventure.impl.fragments.pof

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_28pof_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.POFAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class POFAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    private var powerValue: TextView? = null

    private var increasePowerButton: Button? = null

    private var decreasePowerButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(
            R.layout.fragment_28pof_adventure_vitalstats, container, false
        )

        //CHECKTHIS	initialize(rootView);

        decreasePowerButton = rootView
            .findViewById(R.id.minusPowerButton)
        increasePowerButton = rootView
            .findViewById(R.id.plusPowerButton)
        powerValue = rootView.findViewById(R.id.statsPowerValue)
        val adv = activity as POFAdventure

        powerValue!!.setOnClickListener {

            val alert = createAlertForInitialStatModification(R.string.setInitialPower) { dialog, _ ->

                val input = (dialog as AlertDialog).findViewById<EditText>(R.id.alert_editText_field)

                val value = Integer.parseInt(input.text.toString())
                adv.initialPower = value
            }


            alert.show()
        }

        decreasePowerButton!!.setOnClickListener {
            if (adv.currentPower > 0)
                adv.currentPower = adv.currentPower - 1
            refreshScreensFromResume()
        }

        increasePowerButton!!.setOnClickListener {
            if (adv.currentPower < adv.initialPower)
                adv.currentPower = adv.currentPower + 1
            refreshScreensFromResume()
        }

        refreshScreensFromResume()

        return rootView
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as POFAdventure
        powerValue?.text = adv.currentPower.toString()
    }
}
