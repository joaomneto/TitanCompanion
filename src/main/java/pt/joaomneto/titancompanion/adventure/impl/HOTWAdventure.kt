package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.hotw.HOTWAdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.hotw.HOTWAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays

class HOTWAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            HOTWAdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            AdventureCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.goldEquipment,
            AdventureEquipmentFragment::class
        ),
        AdventureFragmentRunner(
            R.string.notes,
            HOTWAdventureNotesFragment::class
        )
    )
) {

    var change: Int? = null
    private var keywords: MutableList<String> = ArrayList()

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

        var keywordS = ""

        if (!keywords.isEmpty()) {
            for (note in keywords) {
                keywordS += note + "#"
            }
            keywordS = keywordS.substring(0, keywordS.length - 1)
        }

        bw.write("gold=" + gold + "\n")
        bw.write("keywords=" + keywordS + "\n")
        bw.write("change=" + change + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {

        gold = Integer.valueOf(savedGame.getProperty("gold"))
        change = Integer.valueOf(savedGame.getProperty("change"))

        val keywordsValue = savedGame.getProperty("keywords")
        if (keywordsValue != null) {
            val keywordsS = String(keywordsValue.toByteArray(java.nio.charset.Charset.forName("UTF-8")))

            this.keywords = ArrayList()
            val list = Arrays.asList(*keywordsS.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            for (string in list) {
                if (!string.isEmpty())
                    this.keywords.add(string)
            }
        }
    }

    fun getKeywords(): List<String> {
        return keywords
    }
}
