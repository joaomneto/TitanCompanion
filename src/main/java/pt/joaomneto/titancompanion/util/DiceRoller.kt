package pt.joaomneto.titancompanion.util

import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll

import java.util.Random

object DiceRoller {

    private val random = Random(System.currentTimeMillis())

    fun rollD6(): Int {
        return random.nextInt(6) + 1
    }

    fun roll2D6(): DiceRoll {
        return DiceRoll(rollD6(), rollD6())
    }

    fun roll3D6(): Int {
        return rollD6() + rollD6() + rollD6()
    }
}


