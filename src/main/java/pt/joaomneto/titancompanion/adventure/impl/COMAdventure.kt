package pt.joaomneto.titancompanion.adventure.impl

import android.app.AlertDialog
import android.widget.Button
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureKuddamFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.com.Kuddam
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException

class COMAdventure : TWOFMAdventure(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            COMAdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            COMAdventureCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.kuddams,
            COMAdventureKuddamFragment::class
        ),
        AdventureFragmentRunner(
            R.string.goldEquipment,
            AdventureEquipmentFragment::class
        ),
        AdventureFragmentRunner(
            R.string.notes,
            COMAdventureNotesFragment::class
        )
    )
) {

    companion object {

        const val FRAGMENT_VITAL_STATS = 0
        const val FRAGMENT_COMBAT = 1
        const val FRAGMENT_KUDDAM = 2
        const val FRAGMENT_EQUIPMENT = 3
        const val FRAGMENT_NOTES = 4
    }

    var tabasha: Int = 0
    var kuddamKilled: List<Kuddam> = emptyList()
    override var gold: Int = 0
    var fuel: Int = 0
    var cyphers: List<String> = emptyList()
    var tabashaSpecialSkill = false

    override fun loadAdventureSpecificValuesFromFile() {
        tabasha = savedGame.getProperty("tabasha").toInt()
        gold = savedGame.getProperty("gold").toInt()
        fuel = savedGame.getProperty("fuel").toInt()
        provisions = savedGame.getProperty("provisions").toInt()
        tabashaSpecialSkill = savedGame.getProperty("tabashaSpecialSkill").toBoolean()

        val cyphersS = savedGame.getProperty("cyphers")

        if (cyphersS != null) {
            cyphers = stringToStringList(cyphersS)
        }

        val kuddamKilledS = savedGame.getProperty("kuddamKilled")

        if (kuddamKilledS != null) {
            kuddamKilled = stringToEnumList(kuddamKilledS)
        }
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("tabasha=" + tabasha + "\n")
        bw.write("tabashaSpecialSkill=" + tabashaSpecialSkill + "\n")
        bw.write("gold=" + gold + "\n")
        bw.write("fuel=" + fuel + "\n")
        bw.write("provisions=" + provisions + "\n")
        bw.write("cyphers=" + stringListToText(cyphers) + "\n")
        bw.write("kuddamKilled=" + enumListToText(kuddamKilled) + "\n")
    }

    fun useTabashaInitialAction() {

        val alert = AlertDialog.Builder(this).create()

        val useTabashaPanel = layoutInflater.inflate(R.layout.component_30com_use_tabasha, null)

        val tabashaReplenishLuckButton = useTabashaPanel.findViewById<Button>(R.id.tabashaReplenishLuckButton)
        val tabashaReplenishSkillButton = useTabashaPanel.findViewById<Button>(R.id.tabashaReplenishSkillButton)
        val tabashaOtherSkillButton = useTabashaPanel.findViewById<Button>(R.id.tabashaOtherButton)

        tabashaReplenishLuckButton.isEnabled = !tabashaSpecialSkill && tabasha > 0
        tabashaReplenishSkillButton.isEnabled = !tabashaSpecialSkill && tabasha > 0



        tabashaReplenishSkillButton.setOnClickListener({
            currentSkill = initialSkill
            useTabashaStandardAction()
            alert.cancel()
        })

        tabashaReplenishLuckButton.setOnClickListener({
            currentLuck = initialLuck
            useTabashaStandardAction()
            alert.cancel()
        })

        tabashaOtherSkillButton.setOnClickListener({
            useTabashaStandardAction()
            alert.cancel()
        })

        alert.setView(useTabashaPanel)

        alert.show()
    }

    fun useTabashaStandardAction() {
        tabashaSpecialSkill = true
        tabasha--
        refreshScreens()
    }
}
