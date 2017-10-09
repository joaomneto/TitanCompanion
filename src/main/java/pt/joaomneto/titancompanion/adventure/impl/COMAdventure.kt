package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.com.Kuddam


class COMAdventure : TWOFMAdventure() {

    companion object {
//        const val FRAGMENT_KUDDAM = 2
//        const val FRAGMENT_EQUIPMENT = 3
//        const val FRAGMENT_NOTES = 4
    }

    var tabasha: Int = 0
    var kuddamKilled: List<Kuddam> = emptyList()
    var gold: Int = 0
    var fuel: Int = 0
    var cyphers: List<String> = emptyList()

    init {
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"))
        fragmentConfiguration.put(FRAGMENT_COMBAT, AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"))
//        fragmentConfiguration.put(FRAGMENT_KUDDAM, AdventureFragmentRunner(R.string.kuddams,
//                "pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureKuddamFragment"))
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, AdventureFragmentRunner(R.string.goldEquipment,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"))
        fragmentConfiguration.put(FRAGMENT_NOTES, AdventureFragmentRunner(R.string.notes,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"))
    }


    override fun loadAdventureSpecificValuesFromFile() {
        tabasha = Integer.valueOf(savedGame.getProperty("tabasha"))
        gold = Integer.valueOf(savedGame.getProperty("gold"))
        fuel = Integer.valueOf(savedGame.getProperty("fuel"))

        val cyphersS = savedGame.getProperty("cyphers")

        if (cyphersS != null) {
            cyphers = stringToStringList(cyphersS)
        }

        val kuddamKilledS = savedGame.getProperty("kuddamKilled")

        if (kuddamKilledS != null) {
            kuddamKilled = stringToEnumList(cyphersS, Kuddam::class.java)
        }

    }


}
