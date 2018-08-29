package pt.joaomneto.titancompanion.adventure.aod

data class Army(val divisions: List<SoldiersDivision> = emptyList()) {

    val stringToSaveGame: String
        get() {
            val sb = StringBuilder()

            var first = true

            for (division in divisions) {
                sb.append(if (!first) '#' else "")
                sb.append(division.category + "§" + division.quantity)
                first = false
            }

            return sb.toString()
        }

    companion object {
        fun getInstanceFromSavedString(savedArmy: String): Army {
            val armyList = savedArmy.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val divisions = armyList
                .map { division -> division.split("§".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() }
                .map {
                    val type = it[0]
                    val default: Boolean = enumValues<DefaultDivision>().any { it.name == type }
                    if (default)
                        StandardSoldiersDivision(
                            DefaultDivision.valueOf(
                                type
                            ), Integer.parseInt(it[1])
                        )
                    else
                        CustomSoldiersDivision(type, Integer.parseInt(it[1]))
                }

            return Army(divisions)
        }
    }
}
