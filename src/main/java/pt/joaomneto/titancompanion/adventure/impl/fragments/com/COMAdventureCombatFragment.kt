package pt.joaomneto.titancompanion.adventure.impl.fragments.com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_30com_adventure_combat.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.COMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.util.DiceRoller

/**
 * Created by joao.neto on 10/24/17.
 */

class COMAdventureCombatFragment : AdventureCombatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        rootView = inflater.inflate(
            R.layout.fragment_30com_adventure_combat, container, false
        )

        init()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.context as COMAdventure

        oneStrikeCombatButton.setOnClickListener {
            val playerRoll = DiceRoller.roll2D6()
            val enemyRoll = DiceRoller.roll2D6()

            when {
                playerRoll.sum > enemyRoll.sum -> Adventure.showAlert(
                    getString(
                        R.string.oneStrikeCombatVictory,
                        playerRoll.sum,
                        enemyRoll.sum
                    ), adv
                )
                playerRoll.sum < enemyRoll.sum -> Adventure.showAlert(
                    getString(
                        R.string.oneStrikeCombatLoss,
                        playerRoll.sum,
                        enemyRoll.sum
                    ), adv
                )
                else -> Adventure.showAlert(getString(R.string.oneStrikeCombatTie, playerRoll.sum, enemyRoll.sum), adv)
            }
        }
    }

    override fun switchLayoutCombatStarted() {
        super.switchLayoutCombatStarted()
        oneStrikeCombatButton.visibility = View.GONE
    }

    override fun switchLayoutReset(clearResult: Boolean) {
        super.switchLayoutReset(clearResult)
        oneStrikeCombatButton.visibility = View.VISIBLE
    }
}