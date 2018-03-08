package pt.joaomneto.titancompanion.adventure.impl.fragments.aod

import java.util.ArrayList

/**
 * Created by Joao Neto on 26/04/17.
 */

class Army : ArrayList<SoldiersDivision>() {

    val stringToSaveGame: String
        get() {
            val sb = StringBuilder()

            var first = true

            for (division in this) {
                sb.append(if (!first) '#' else "")
                sb.append(division.getCategory() + "§" + division.quantity)
                first = false
            }

            return sb.toString()
        }

    fun recalculate(skirmishArmy: Map<String, Int>) {
        for (division in this) {
            val qt = skirmishArmy[division.getCategory()]
            division.quantity = (division.quantity + (qt ?: 0))
            division.initialQuantity = division.quantity
        }
    }

    companion object {
        fun getInstanceFromSavedString(savedArmy: String): Army {
            val result = Army()

            val armyList = savedArmy.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            armyList
                .map { division -> division.split("§".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() }
                .mapTo(result) {
                    val type = it[0]
                    val default: Boolean = enumValues<DefaultDivision>().any { it.name == type }
                    if (default)
                        StandardSoldiersDivision(DefaultDivision.valueOf(type), Integer.parseInt(it[1]))
                    else
                        CustomSoldiersDivision(type, Integer.parseInt(it[1]))
                }

            return result
        }
    }
}