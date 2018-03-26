package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.dod.DODAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.dod.DODAdventureMedallionFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.dod.DODAdventurePoisonFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.dod.Medallion
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class DODAdventure : TFODAdventure(
        arrayOf(
                AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
                AdventureFragmentRunner(R.string.poison, DODAdventurePoisonFragment::class),
                AdventureFragmentRunner(R.string.fights, DODAdventureCombatFragment::class),
                AdventureFragmentRunner(R.string.medallions, DODAdventureMedallionFragment::class),
                AdventureFragmentRunner(R.string.goldEquipment, AdventureEquipmentFragment::class),
                AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
        )
) {

    var poison = 0
    var medallions = mutableListOf<MedallionStatus>()

    override fun loadAdventureSpecificValuesFromFile() {
        super.loadAdventureSpecificValuesFromFile()
        poison = Integer.valueOf(savedGame.getProperty("poison"))

        val medallionsS = savedGame.getProperty("medallions")

        var medallionsMap = emptyMap<Medallion, Int>()

        if (medallionsS != null) {
            medallionsMap = stringToEnumMap(medallionsS)
        }

        medallions = Medallion.values().map {
            MedallionStatus(
                    it,
                    medallionsMap.containsKey(it),
                    medallionsMap[it] ?: 0
            )
        }.sortedBy { it.medallion.name }.toMutableList()
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        super.storeAdventureSpecificValuesInFile(bw)
        bw.write("poison=$poison\n")
        bw.write("medallions=${convertMedallionsToText(medallions)}\n")
    }

    private fun convertMedallionsToText(medallions: List<MedallionStatus>) =
            medallions.joinToString(separator = "#", transform = {
                if (it.achieved)
                    "${it.medallion.name}§${it.power}"
                else
                    ""
            })

    fun hasMedallionPower(): Boolean {
        return medallions.any { it.achieved && it.power > 0 }
    }

    fun getMedallionLowestPower(): MedallionStatus? {
        return medallions.filter { it.achieved && it.power > 0 }.minBy { it.power }
    }
}

class MedallionStatus(val medallion: Medallion, var achieved: Boolean, var power: Int)