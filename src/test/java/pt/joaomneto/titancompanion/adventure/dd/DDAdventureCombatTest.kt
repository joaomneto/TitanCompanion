package pt.joaomneto.titancompanion.adventure.dd

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureCombatTest
import pt.joaomneto.titancompanion.adventure.impl.DDAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

@RunWith(RobolectricTestRunner::class)
class DDAdventureCombatTest : AdventureCombatTest<DDAdventure, AdventureCombatFragment>(
    DDAdventure::class,
    AdventureCombatFragment::class,
    DDAdventureVitalStatsTest.SAVEGAME
)
