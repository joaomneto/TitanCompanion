package pt.joaomneto.titancompanion.adventure.impl.fragments.ff

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
import pt.joaomneto.titancompanion.adventure.impl.FFAdventure
import pt.joaomneto.titancompanion.util.DiceRoller

class FFVehicleCombatFragment : AdventureFragment() {

    internal var vehicleFirepowerValue: TextView? = null
    internal var vehicleArmourValue: TextView? = null
    internal var enemyFirepowerValue: TextView? = null
    internal var enemyArmourValue: TextView? = null
    internal var enemy2FirepowerValue: TextView? = null
    internal var enemy2ArmourValue: TextView? = null

    internal var attackButton: Button? = null

    var enemyFirepower = 0
    var enemyArmour = 0

    var enemy2Firepower = 0
    var enemy2Armour = 0
    internal var combatResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(
            R.layout.fragment_13ff_adventure_vehiclecombat, container,
            false
        )

        val adv = activity as FFAdventure

        vehicleFirepowerValue = rootView
            .findViewById<View>(R.id.vehiclefirepowerValue) as TextView
        vehicleArmourValue = rootView
            .findViewById<View>(R.id.vehiclearmorValue) as TextView
        enemyFirepowerValue = rootView
            .findViewById<View>(R.id.enemyfirepowerValue) as TextView
        enemyArmourValue = rootView
            .findViewById<View>(R.id.enemyarmorValue) as TextView
        enemy2FirepowerValue = rootView
            .findViewById<View>(R.id.enemy2firepowerValue) as TextView
        enemy2ArmourValue = rootView
            .findViewById<View>(R.id.enemy2armorValue) as TextView

        vehicleFirepowerValue!!.text = "" + adv.currentFirepower
        vehicleArmourValue!!.text = "" + adv.currentArmour

        combatResult = rootView.findViewById<View>(R.id.combatResult) as TextView

        setupIncDecButton(
            rootView, R.id.plusfirepowerButton,
            R.id.minusfirepowerButton, adv, FFAdventure::currentFirepower,
            adv.initialFirepower
        )
        setupIncDecButton(
            rootView, R.id.plusarmorButton,
            R.id.minusarmorButton, adv, FFAdventure::currentArmour, adv.initialArmour
        )

        setupIncDecButton(
            rootView, R.id.plusEnemyfirepowerButton,
            R.id.minusEnemyfirepowerButton, ::enemyFirepower, 100
        )
        setupIncDecButton(
            rootView, R.id.plusEnemyarmorButton,
            R.id.minusEnemyarmorButton, ::enemyArmour,
            100
        )

        setupIncDecButton(
            rootView, R.id.plusEnemy2firepowerButton,
            R.id.minusEnemy2firepowerButton, ::enemy2Firepower, 100
        )
        setupIncDecButton(
            rootView, R.id.plusEnemy2armorButton,
            R.id.minusEnemy2armorButton, ::enemy2Armour, 100
        )

        attackButton = rootView.findViewById<View>(R.id.buttonAttack) as Button

        attackButton!!.setOnClickListener(OnClickListener {
            if (enemyArmour == 0 && enemy2Armour == 0 || adv.currentArmour == 0)
                return@OnClickListener

            combatResult!!.text = ""

            var myAttack = DiceRoller.roll2D6().sum!! + adv.currentFirepower
            var enemyAttack = DiceRoller.roll2D6().sum!! + enemyFirepower

            if (myAttack > enemyAttack) {
                val damage = DiceRoller.rollD6()
                enemyArmour -= damage
                if (enemyArmour <= 0) {
                    enemyArmour = 0
                    Adventure.showAlert(getString(R.string.ffDirectHitDefeat), adv)
                } else {
                    combatResult!!.text = getString(R.string.ffDirectHit, damage)
                }
            } else if (enemyAttack > enemyFirepower) {
                val damage = DiceRoller.rollD6()
                adv.currentArmour = adv.currentArmour - damage
                if (adv.currentArmour <= 0) {
                    adv.currentArmour = 0
                    Adventure.showAlert(getString(R.string.ffEnemyDestroyedYourVehicle), adv)
                } else {
                    combatResult!!.text = getString(R.string.ffEnemyHit, damage)
                }
            } else {
                combatResult!!
                    .setText(R.string.bothMissed)
            }

            if (enemy2Armour > 0 && enemy2Firepower > 0) {
                myAttack = DiceRoller.roll2D6().sum!! + adv.currentFirepower
                enemyAttack = DiceRoller.roll2D6().sum!! + enemy2Firepower
                if (myAttack > enemyAttack) {
                    if (enemyArmour > 0) {
                        combatResult!!.text = (combatResult!!.text.toString()
                            + "\n" + getString(R.string.ffAvoiedSecondAttack))
                    } else {
                        val damage = DiceRoller.rollD6()
                        enemy2Armour -= damage
                        if (enemy2Armour <= 0) {
                            enemy2Armour = 0
                            Adventure.showAlert(R.string.ffDirectHitDefeat, adv)
                        } else {
                            combatResult!!.text = getString(R.string.ffDirectHit, damage)
                        }
                    }
                } else if (enemyAttack > enemyFirepower) {
                    val damage = DiceRoller.rollD6()
                    adv.currentArmour = adv.currentArmour - damage
                    if (adv.currentArmour <= 0) {
                        adv.currentArmour = 0
                        Adventure.showAlert(R.string.ffEnemyDestroyedYourVehicle, adv)
                    } else {
                        combatResult!!.text = (combatResult!!.text.toString()
                            + "\n" + getString(R.string.ffSecondEnemyHit, damage))
                    }
                } else {
                    if (enemyArmour > 0) {
                        combatResult!!.text = (combatResult!!.text.toString()
                            + "\n" + getString(R.string.ffavoidedSecondAttack))
                    } else {
                        combatResult!!.text = (combatResult!!.text.toString()
                            + "\n" + getString(R.string.bothMissed))
                    }
                }
            }
            refreshScreensFromResume()
        })

        refreshScreensFromResume()

        return rootView
    }

    override fun refreshScreensFromResume() {

        val adv = activity as FFAdventure

        enemyArmourValue!!.text = "" + enemyArmour
        enemyFirepowerValue!!.text = "" + enemyFirepower
        enemy2ArmourValue!!.text = "" + enemy2Armour
        enemy2FirepowerValue!!.text = "" + enemy2Firepower
        vehicleArmourValue!!.text = "" + adv.currentArmour
        vehicleFirepowerValue!!.text = "" + adv.currentFirepower
    }
}
