package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sots.SOTSAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sots.SOTSAdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.sots.SOTSAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSMartialArt
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class SOTSAdventure : TFODAdventure(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, SOTSAdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.fights, SOTSAdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.goldEquipment, SOTSAdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    var currentHonour = -1
    var willowLeafArrows = -1
    var bowelRakerArrows = -1
    var armourPiercerArrows = -1
    var hummingBulbArrows = -1
    var skill: SOTSMartialArt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("honour=" + currentHonour + "\n")
        bw.write("skill=" + skill!!.name + "\n")
        if (SOTSMartialArt.KYUJUTSU == skill) {
            bw.write("armourPiercerArrows=" + armourPiercerArrows + "\n")
            bw.write("bowelRakerArrows=" + bowelRakerArrows + "\n")
            bw.write("hummingBulbArrows=" + hummingBulbArrows + "\n")
            bw.write("willowLeafArrows=" + willowLeafArrows + "\n")
        }
    }

    override fun loadAdventureSpecificValuesFromFile() {
        currentHonour = Integer.valueOf(savedGame.getProperty("honour"))
        skill = SOTSMartialArt.valueOf(savedGame.getProperty("skill"))
        if (SOTSMartialArt.KYUJUTSU == skill) {
            armourPiercerArrows = Integer.parseInt(savedGame.getProperty("armourPiercerArrows"))
            bowelRakerArrows = Integer.parseInt(savedGame.getProperty("bowelRakerArrows"))
            hummingBulbArrows = Integer.parseInt(savedGame.getProperty("hummingBulbArrows"))
            willowLeafArrows = Integer.parseInt(savedGame.getProperty("willowLeafArrows"))
        }
    }

    companion object {

        internal var FRAGMENT_EQUIPMENT: Int? = 2
        internal var FRAGMENT_NOTES: Int? = 3
    }
}
