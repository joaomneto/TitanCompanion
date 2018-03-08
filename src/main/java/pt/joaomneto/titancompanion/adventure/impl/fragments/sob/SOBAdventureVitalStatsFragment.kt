package pt.joaomneto.titancompanion.adventure.impl.fragments.sob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.SOBAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.DiceRoller

class SOBAdventureVitalStatsFragment : AdventureVitalStatsFragment() {

    internal var logValue: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(R.layout.fragment_16sob_adventure_vitalstats, container, false)

        //CHECKTHIS	initialize(rootView);
        val adv = activity as SOBAdventure

        val incTrigger = Runnable { adv.setCurrentStamina(Math.min(adv.initialStamina, adv.getCurrentStamina() + 1)) }

        setupIncDecButton(
            rootView,
            R.id.plusLogButton,
            R.id.minusLogButton,
            adv,
            SOBAdventure::log,
            50,
            incTrigger,
            null
        )

        logValue = rootView.findViewById<View>(R.id.statsLogValue) as TextView

        val testCrewStrengthButton = rootView.findViewById<View>(R.id.testCrewStrengthButton) as Button

        testCrewStrengthButton.setOnClickListener {
            val result = DiceRoller.roll3D6() < adv.currentCrewStrength

            val message = if (result) R.string.success else R.string.failed
            Adventure.showAlert(message, adv)
        }

        refreshScreensFromResume()

        return rootView
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        val adv = activity as SOBAdventure
        logValue!!.text = "" + adv.log
    }
}
