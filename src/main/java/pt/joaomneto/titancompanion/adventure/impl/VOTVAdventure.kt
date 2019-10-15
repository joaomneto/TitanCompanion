package pt.joaomneto.titancompanion.adventure.impl

import android.view.Menu
import android.view.View
import android.widget.ListView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.votv.VOTVAdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.votv.VOTVAdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.votv.VOTVAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import kotlin.math.max

class VOTVAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            VOTVAdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            AdventureCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.spellsAndEquipment,
            VOTVAdventureEquipmentFragment::class
        ),
        AdventureFragmentRunner(
            R.string.notesAndAfflictions,
            VOTVAdventureNotesFragment::class
        )
    )
) {

    var currentFaith = -1
    var afflictions = mutableListOf<String>()
    var spells = mutableListOf<String>()

    val decreaseFaithListener =
        { _: View ->
            currentFaith = max(0, currentFaith - 1)
            refreshScreens()
        }

    val increaseFaithListener =
        { _: View ->
            currentFaith++
            refreshScreens()
        }

    fun addSpellListener(spellList: ListView) =
        addToListOnClickListener(spells, spellList, this)
    fun addAfflictionListener(afflictionList: ListView) =
        addToListOnClickListener(afflictions, afflictionList, this)
    fun removeSpellListener(spellList: ListView) =
        listItemLongPressListener(spells, spellList, this)
    fun removeAfflictionListener(afflictionList: ListView) =
        listItemLongPressListener(afflictions, afflictionList, this)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("spells=${stringListToText(spells)}\n")
        bw.write("afflictions=${stringListToText(afflictions)}\n")
        bw.write("currentFaith=$currentFaith\n")
        bw.write("gold=$gold\n")
        bw.write
    }

    override fun loadAdventureSpecificValuesFromFile() = with(savedGame) {
        currentFaith = Integer.valueOf(getProperty("currentFaith"))
        gold = Integer.valueOf(getProperty("gold"))
        spells = stringToStringList(getProperty("spells"))
        afflictions = stringToStringList(getProperty("afflictions"))
    }
}
