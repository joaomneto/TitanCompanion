package pt.joaomneto.titancompanion.adventure.impl

import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.AODAdventureSoldiersFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.Army
import pt.joaomneto.titancompanion.adventure.state.AdventureState
import pt.joaomneto.titancompanion.adventure.state.StateKey
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.util.*

class AODAdventure : Adventure<AODState>(
        arrayOf(
                AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
                AdventureFragmentRunner(R.string.soldiers, AODAdventureSoldiersFragment::class),
                AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
                AdventureFragmentRunner(R.string.goldTreasure, AdventureEquipmentFragment::class),
                AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
        )
        , AODState::class
) {

    override val currencyName = R.string.gold

    val soldiersFragment: AODAdventureSoldiersFragment?
        get() = getFragment(AODAdventureSoldiersFragment::class)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    override fun loadAdventureSpecificStateFromSavedGame(
            values: Map<out StateKey, Any>,
            savedGame: Properties
    ) = AODState(
            values,
            Army.getInstanceFromSavedString(savedGame.getProperty("soldiers"))
    )

    fun test() {
        state = state.increaseGold()
    }
}

data class AODState(
        override val values: Map<out StateKey, Any>
) : AdventureState(values) {

    val soldiers
        get() = (values[AODStateKey.SOLDIERS] as Army)

    constructor(
            state: Map<out StateKey, Any>,
            soldiers: Army
    ) : this(state.plus(AODStateKey.SOLDIERS to soldiers))

    override fun storeAdventureSpecificValuesInFile() = "soldiers=" + soldiers.stringToSaveGame
}

enum class AODStateKey(override val saveFileKey: String,
                       override val serializer: ((Any) -> String)? = null) : StateKey {
    SOLDIERS("soldiers", { (it as Army).stringToSaveGame })
}
