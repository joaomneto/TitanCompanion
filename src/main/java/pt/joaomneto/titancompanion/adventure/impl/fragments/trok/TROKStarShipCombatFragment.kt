package pt.joaomneto.titancompanion.adventure.impl.fragments.trok

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
import pt.joaomneto.titancompanion.adventure.impl.TROKAdventure
import pt.joaomneto.titancompanion.util.DiceRoller

class TROKStarShipCombatFragment : AdventureFragment() {

    internal var starshipWeaponsValue: TextView? = null
    internal var starshipShieldsValue: TextView? = null
    internal var starshipMissilesValue: TextView? = null
    internal var enemyWeaponsValue: TextView? = null
    internal var enemyShieldsValue: TextView? = null
    internal var enemy2WeaponsValue: TextView? = null
    internal var enemy2ShieldsValue: TextView? = null

    internal var attackButton: Button? = null

    var enemyWeapons = 0
    var enemyShields = 0
    var enemy2Weapons = 0
    var enemy2Shields = 0

    internal var combatResult: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(R.layout.fragment_15trok_adventure_starshipcombat, container, false)

        val adv = activity as TROKAdventure

        starshipWeaponsValue = rootView.findViewById<View>(R.id.starshipWeaponsValue) as TextView
        starshipShieldsValue = rootView.findViewById<View>(R.id.starshipShieldsValue) as TextView
        starshipMissilesValue = rootView.findViewById<View>(R.id.starshipMissilesValue) as TextView

        enemyWeaponsValue = rootView.findViewById<View>(R.id.enemyWeaponsValue) as TextView
        enemyShieldsValue = rootView.findViewById<View>(R.id.enemyShieldsValue) as TextView
        enemy2WeaponsValue = rootView.findViewById<View>(R.id.enemy2WeaponsValue) as TextView
        enemy2ShieldsValue = rootView.findViewById<View>(R.id.enemy2ShieldsValue) as TextView

        starshipWeaponsValue!!.text = "" + adv.currentWeapons
        starshipShieldsValue!!.text = "" + adv.currentShields

        combatResult = rootView.findViewById<View>(R.id.combatResult) as TextView

        setupIncDecButton(
            rootView,
            R.id.plusWeaponsButton,
            R.id.minusWeaponsButton,
            adv,
            TROKAdventure::currentWeapons,
            1
        )
        setupIncDecButton(
            rootView,
            R.id.plusShieldsButton,
            R.id.minusShieldsButton,
            adv,
            TROKAdventure::currentShields,
            adv.initialShields
        )
        setupIncDecButton(rootView, R.id.plusMissilesButton, R.id.minusMissilesButton, adv, TROKAdventure::missiles, 99)

        setupIncDecButton(rootView, R.id.plusEnemyWeaponsButton, R.id.minusEnemyWeaponsButton, ::enemyWeapons, 99)
        setupIncDecButton(rootView, R.id.plusEnemyShieldsButton, R.id.minusEnemyShieldsButton, ::enemyShields, 99)
        setupIncDecButton(rootView, R.id.plusEnemy2WeaponsButton, R.id.minusEnemy2WeaponsButton, ::enemyWeapons, 99)
        setupIncDecButton(rootView, R.id.plusEnemy2ShieldsButton, R.id.minusEnemy2ShieldsButton, ::enemyShields, 99)

        setupIncDecButton(rootView, R.id.plusEnemy2WeaponsButton, R.id.minusEnemy2WeaponsButton, ::enemy2Weapons, 99)
        setupIncDecButton(rootView, R.id.plusEnemy2ShieldsButton, R.id.minusEnemy2ShieldsButton, ::enemy2Shields, 99)

        attackButton = rootView.findViewById<View>(R.id.buttonAttack) as Button

        attackButton!!.setOnClickListener(OnClickListener {
            if (enemyShields == 0 || adv.currentShields == 0)
                return@OnClickListener

            combatResult!!.text = ""

            if (enemyShields > 0) {
                if (DiceRoller.roll2D6().sum <= adv.currentWeapons) {
                    val damage = 1
                    enemyShields -= damage
                    if (enemyShields <= 0) {
                        enemyShields = 0
                        Adventure.showAlert(R.string.ffDirectHitDefeat, adv)
                    } else {
                        combatResult!!.text = getString(R.string.trokDirectHit, damage)
                    }
                } else {
                    combatResult!!.setText(R.string.missedTheEnemy)
                }
            } else if (enemy2Shields > 0) {
                if (DiceRoller.roll2D6().sum <= adv.currentWeapons) {
                    val damage = 1
                    enemy2Shields -= damage
                    if (enemy2Shields <= 0) {
                        enemy2Shields = 0
                        Adventure.showAlert(getString(R.string.directHitDefeatSecondEnemy), adv)
                    } else {
                        combatResult!!.text = getString(R.string.trokDirectHit, damage)
                    }
                } else {
                    combatResult!!.setText(R.string.missedTheEnemy)
                }
            } else {
                return@OnClickListener
            }
            if (enemyShields > 0) {
                if (DiceRoller.roll2D6().sum <= enemyWeapons) {
                    val damage = 1
                    adv.currentShields = adv.currentShields - damage
                    if (adv.currentShields <= 0) {
                        adv.currentShields = 0
                        Adventure.showAlert(getString(R.string.trokPlayerStarshipDestroyed), adv)
                    } else {
                        combatResult!!.text = combatResult!!.text.toString() + "\n" + getString(
                            R.string.trokEnemyHitPlayerStarship,
                            damage
                        )
                    }
                } else {
                    combatResult!!.text = combatResult!!.text.toString() + "\n" + getString(R.string.enemyMissed)
                }
            }


            if (enemy2Shields > 0) {
                if (DiceRoller.roll2D6().sum <= enemy2Weapons) {
                    val damage = 1
                    adv.currentShields = adv.currentShields - damage
                    if (adv.currentShields <= 0) {
                        adv.currentShields = 0
                        Adventure.showAlert(R.string.trokPlayerStarshipDestroyed, adv)
                    } else {
                        combatResult!!.text = combatResult!!.text.toString() + "\n" + getString(
                            R.string.trokSecondEnemyHitPlayerStarship,
                            damage
                        )
                    }
                } else {
                    combatResult!!.text = combatResult!!.text.toString() + "\n" + getString(R.string.secondEnemyMissed)
                }
            }

            refreshScreensFromResume()
        })

        refreshScreensFromResume()

        return rootView
    }

    override fun refreshScreensFromResume() {

        val adv = activity as TROKAdventure

        enemyShieldsValue!!.text = "" + enemyShields
        enemyWeaponsValue!!.text = "" + enemyWeapons
        enemy2ShieldsValue!!.text = "" + enemy2Shields
        enemy2WeaponsValue!!.text = "" + enemy2Weapons
        starshipShieldsValue!!.text = "" + adv.currentShields
        starshipWeaponsValue!!.text = "" + adv.currentWeapons
        starshipMissilesValue!!.text = "" + adv.missiles
    }
}
