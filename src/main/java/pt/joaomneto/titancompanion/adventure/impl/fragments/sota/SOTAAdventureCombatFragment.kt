package pt.joaomneto.titancompanion.adventure.impl.fragments.sota

import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll

class SOTAAdventureCombatFragment : AdventureCombatFragment() {

    override fun suddenDeath(diceRoll: DiceRoll, enemyDiceRoll: DiceRoll): Boolean? {
        return diceRoll.sum == 12
    }
}
