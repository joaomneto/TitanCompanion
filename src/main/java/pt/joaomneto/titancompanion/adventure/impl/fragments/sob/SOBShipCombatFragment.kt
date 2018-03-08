package pt.joaomneto.titancompanion.adventure.impl.fragments.sob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.SOBAdventure
import pt.joaomneto.titancompanion.util.DiceRoller

class SOBShipCombatFragment : AdventureFragment() {

    internal var starshipCrewStrikeValue: TextView? = null
    internal var starshipCrewStrengthValue: TextView? = null
    internal var enemyCrewStrikeValue: TextView? = null
    internal var enemyCrewStrengthValue: TextView? = null

    internal var attackButton: Button? = null

    var enemyCrewStrike = 0
    var enemyCrewStrength = 0

    internal var combatResult: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(R.layout.fragment_16sob_adventure_shipcombat, container, false)

        val adv = activity as SOBAdventure

        starshipCrewStrikeValue = rootView.findViewById<View>(R.id.starshipCrewStrikeValue) as TextView
        starshipCrewStrengthValue = rootView.findViewById<View>(R.id.starshipCrewStrengthValue) as TextView

        enemyCrewStrikeValue = rootView.findViewById<View>(R.id.enemyCrewStrikeValue) as TextView
        enemyCrewStrengthValue = rootView.findViewById<View>(R.id.enemyCrewStrengthValue) as TextView

        starshipCrewStrikeValue!!.text = "" + adv.currentCrewStrike
        starshipCrewStrengthValue!!.text = "" + adv.currentCrewStrength

        combatResult = rootView.findViewById<View>(R.id.combatResult) as TextView

        setupIncDecButton(
            rootView,
            R.id.plusCrewStrikeButton,
            R.id.minusCrewStrikeButton,
            adv,
            SOBAdventure::currentCrewStrike,
            adv.initialCrewStrike
        )
        setupIncDecButton(
            rootView,
            R.id.plusCrewStrengthButton,
            R.id.minusCrewStrengthButton,
            adv,
            SOBAdventure::currentCrewStrength,
            adv.initialCrewStrength
        )

        setupIncDecButton(
            rootView,
            R.id.plusEnemyCrewStrikeButton,
            R.id.minusEnemyCrewStrikeButton,
            ::enemyCrewStrike,
            99
        )
        setupIncDecButton(
            rootView,
            R.id.plusEnemyCrewStrengthButton,
            R.id.minusEnemyCrewStrengthButton,
            ::enemyCrewStrength,
            99
        )


        attackButton = rootView.findViewById<View>(R.id.buttonAttack) as Button

        attackButton!!.setOnClickListener(OnClickListener {
            if (enemyCrewStrength == 0 || adv.currentCrewStrength == 0)
                return@OnClickListener

            combatResult!!.text = ""

            if (enemyCrewStrength > 0) {
                val attackStrength = DiceRoller.roll2D6().sum!! + adv.currentCrewStrike
                val enemyStrength = DiceRoller.roll2D6().sum!! + enemyCrewStrike

                if (attackStrength > enemyStrength) {
                    val damage = 2
                    enemyCrewStrength -= damage
                    if (enemyCrewStrength <= 0) {
                        enemyCrewStrength = 0
                        Adventure.showAlert(R.string.ffDirectHitDefeat, adv)
                    } else {
                        combatResult!!.setText(R.string.sobDirectHit)
                    }
                } else if (attackStrength < enemyStrength) {
                    val damage = 2
                    adv.currentCrewStrength = adv.currentCrewStrength - damage
                    if (adv.currentCrewStrength <= 0) {
                        adv.currentCrewStrength = 0
                        Adventure.showAlert(R.string.enemyDestroyedShip, adv)
                    } else {
                        combatResult!!.text = combatResult!!.text.toString() + "\n" + getString(R.string.sobEnemyHitYourShip)
                    }
                } else {
                    combatResult!!.setText(R.string.bothShipsMissed)
                }
            } else {
                return@OnClickListener
            }

            refreshScreensFromResume()
        })

        refreshScreensFromResume()

        return rootView
    }

    override fun refreshScreensFromResume() {

        val adv = activity as SOBAdventure

        enemyCrewStrengthValue!!.text = "" + enemyCrewStrength
        enemyCrewStrikeValue!!.text = "" + enemyCrewStrike
        starshipCrewStrengthValue!!.text = "" + adv.currentCrewStrength
        starshipCrewStrikeValue!!.text = "" + adv.currentCrewStrike
    }
}
