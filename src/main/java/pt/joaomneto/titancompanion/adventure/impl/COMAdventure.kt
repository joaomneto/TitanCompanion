package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.com.Kuddam
import java.io.BufferedWriter
import java.io.IOException
import android.app.AlertDialog
import android.widget.Button

class COMAdventure : TWOFMAdventure() {

    companion object {

        const val FRAGMENT_VITAL_STATS = 0
        const val FRAGMENT_COMBAT = 1
        const val FRAGMENT_KUDDAM = 2
        const val FRAGMENT_EQUIPMENT = 3
        const val FRAGMENT_NOTES = 4
    }

    var tabasha: Int = 0
    var kuddamKilled: MutableList<Kuddam> = mutableListOf()
    var gold: Int = 0
    var fuel: Int = 0
    var cyphers: MutableList<String> = mutableListOf()
    var tabashaSpecialSkill = false

    init {
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, AdventureFragmentRunner(R.string.vitalStats,
            "pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureVitalStatsFragment"))
        fragmentConfiguration.put(FRAGMENT_COMBAT, AdventureFragmentRunner(R.string.fights,
            "pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureCombatFragment"))
        fragmentConfiguration.put(FRAGMENT_KUDDAM, AdventureFragmentRunner(R.string.kuddams,
            "pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureKuddamFragment"))
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, AdventureFragmentRunner(R.string.goldEquipment,
            "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"))
        fragmentConfiguration.put(FRAGMENT_NOTES, AdventureFragmentRunner(R.string.notes,
            "pt.joaomneto.titancompanion.adventure.impl.fragments.com.COMAdventureNotesFragment"))
    }

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
            kuddamKilled = stringToEnumList(kuddamKilledS, Kuddam::class.java)
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
