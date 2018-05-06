package pt.joaomneto.titancompanion.adventure.impl.util

class DiceRoll(val d1: Int, val d2: Int) {

    val sum: Int
        get() = d1 + d2

    val isDouble: Boolean
        get() = d1 == d2

    override fun toString(): String {
        return "DiceRoll{" +
            "d1=" + d1 +
            ", d2=" + d2 +
            ", total=" + (d1 + d2) + "}"
    }
}
