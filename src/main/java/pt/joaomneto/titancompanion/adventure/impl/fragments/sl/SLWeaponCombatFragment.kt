package pt.joaomneto.titancompanion.adventure.impl.fragments.sl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_33sl_adventure_weaponcombat.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.SLAdventure
import pt.joaomneto.titancompanion.util.DiceRoller

class SLWeaponCombatFragment : AdventureFragment() {

    private var enemyLasers = 0
    private var enemyShields = 0
    private var enemyRating = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater!!.inflate(
            R.layout.fragment_33sl_adventure_weaponcombat, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = context as SLAdventure

        plusEnemylasersButton.setOnClickListener({
            enemyLasers += 1
            refreshScreensFromResume()
        })
        plusEnemyshieldsButton.setOnClickListener({
            enemyShields += 1
            refreshScreensFromResume()
        })
        plusEnemyratingButton.setOnClickListener({
            enemyRating += 1
            refreshScreensFromResume()
        })
        minusEnemylasersButton.setOnClickListener({
            enemyLasers = maxOf(0, enemyLasers - 1)
            refreshScreensFromResume()
        })
        minusEnemyshieldsButton.setOnClickListener({
            enemyShields = maxOf(0, enemyShields - 1)
            refreshScreensFromResume()
        })
        minusEnemyratingButton.setOnClickListener({
            enemyRating = maxOf(0, enemyRating - 1)
            refreshScreensFromResume()
        })

        pluslasersButton.setOnClickListener({
            adv.currentLasers += 1
            refreshScreensFromResume()
        })
        plusshieldsButton.setOnClickListener({
            adv.currentShields += 1
            refreshScreensFromResume()
        })
        plusratingButton.setOnClickListener({
            adv.rating += 1
            refreshScreensFromResume()
        })
        minuslasersButton.setOnClickListener({
            adv.currentLasers = maxOf(0, adv.currentLasers - 1)
            refreshScreensFromResume()
        })
        minusshieldsButton.setOnClickListener({
            adv.currentShields = maxOf(0, adv.currentShields - 1)
            refreshScreensFromResume()
        })
        minusratingButton.setOnClickListener({
            adv.rating = maxOf(0, adv.rating - 1)
            refreshScreensFromResume()
        })

        buttonAttack.setOnClickListener({
            combatTurn()
            refreshScreensFromResume()
        })
    }

    fun combatTurn() {

        val adv = activity as SLAdventure

        var combatText: String

        if (adv.rating > enemyRating) {
            combatText = playerTurn()
            combatText += if (enemyShields > 0)
                "\n${enemyTurn()}"
            else
                "\n${getString(R.string.slPlayerVictory)}"
            combatText += if (adv.currentShields == 0) "\n${getString(R.string.slEnemyVictory)}" else ""
        } else {
            combatText = enemyTurn()
            combatText += if (adv.currentShields > 0)
                "\n${playerTurn()}"
            else
                "\n${getString(R.string.slEnemyVictory)}"
            combatText += if (enemyShields == 0) "\n${getString(R.string.slPlayerVictory)}" else ""
        }

        combatResult?.text = combatText
    }

    private fun enemyTurn(): String {
        val adv = activity as SLAdventure


        if (DiceRoller.rollD6() <= enemyLasers) {
            adv.currentShields = maxOf(0, adv.currentShields - 2)
            return getString(R.string.slEnemyHitPlayer, 2)
        }


        return getString(R.string.enemyMissed)
    }

    private fun playerTurn(): String {
        val adv = activity as SLAdventure

        if (DiceRoller.rollD6() <= adv.currentLasers) {
            enemyShields = maxOf(0, enemyShields - 2)
            return getString(R.string.slDirectHit, 2)
        }

        return getString(R.string.missedTheEnemy)
    }

    override fun refreshScreensFromResume() {

        val adv = activity as SLAdventure

        enemyshieldsValue?.text = enemyShields.toString()
        enemylasersValue?.text = enemyLasers.toString()
        enemyratingValue?.text = enemyRating.toString()
        playerratingValue?.text = adv.rating.toString()
        playershieldsValue?.text = adv.currentShields.toString()
        playerlasersValue?.text = adv.currentLasers.toString()

        buttonAttack.isEnabled = adv.currentShields > 0
    }
}
