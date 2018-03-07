package pt.joaomneto.titancompanion.adventure.impl.fragments.bnc

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_25bnc_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.BNCAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class BNCAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    private var willpowerValue: TextView? = null

    private var increaseWillpowerButton: Button? = null

    private var decreaseWillpowerButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(
            R.layout.fragment_25bnc_adventure_vitalstats, container, false
        )

        //CHECKTHIS	initialize(rootView);

        decreaseWillpowerButton = rootView
            .findViewById(R.id.minusWillpowerButton)
        increaseWillpowerButton = rootView
            .findViewById(R.id.plusWillpowerButton)
        willpowerValue = rootView.findViewById(R.id.statsWillpowerValue)
        val adv = activity as BNCAdventure

        willpowerValue = rootView.findViewById(R.id.statsWillpowerValue)
        willpowerValue!!.setOnClickListener {

            val alert = createAlertForInitialStatModification(R.string.setInitialWillpower) { dialog, _ ->

                val input = (dialog as AlertDialog).findViewById<EditText>(R.id.alert_editText_field)

                val value = Integer.parseInt(input.text.toString())
                adv.initialWillpower = value
            }


            alert.show()
        }

        decreaseWillpowerButton!!.setOnClickListener {
            if (adv.currentWillpower > 0)
                adv.currentWillpower = adv.currentWillpower - 1
            refreshScreensFromResume()
        }

        increaseWillpowerButton!!.setOnClickListener {
            if (adv.currentWillpower < adv.initialWillpower)
                adv.currentWillpower = adv.currentWillpower + 1
            refreshScreensFromResume()
        }

        refreshScreensFromResume()

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTestWillpower.setOnClickListener({
            (this.activity as BNCAdventure).testWillpower()
        })
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as BNCAdventure
        willpowerValue?.text = adv.currentWillpower.toString()
    }
}
