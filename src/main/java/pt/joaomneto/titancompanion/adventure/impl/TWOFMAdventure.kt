package pt.joaomneto.titancompanion.adventure.impl

import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.state.AdventureState
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner

open class TWOFMAdventure(
    override val fragmentConfiguration: Array<AdventureFragmentRunner> = DEFAULT_FRAGMENTS
) : Adventure<AdventureState>(fragmentConfiguration, AdventureState::class) {

    companion object {
        val DEFAULT_FRAGMENTS = arrayOf(
            AdventureFragmentRunner(
                R.string.vitalStats,
                AdventureVitalStatsFragment::class
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
                AdventureNotesFragment::class
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }
}
