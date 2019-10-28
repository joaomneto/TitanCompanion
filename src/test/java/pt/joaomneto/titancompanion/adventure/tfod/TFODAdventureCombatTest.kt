package pt.joaomneto.titancompanion.adventure.tfod

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureCombatTest
import pt.joaomneto.titancompanion.adventure.impl.TFODAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

@RunWith(RobolectricTestRunner::class)
class TFODAdventureCombatTest : AdventureCombatTest<TFODAdventure, AdventureCombatFragment>(
    TFODAdventure::class,
    AdventureCombatFragment::class,
    TFODAdventureVitalStatsTest.SAVEGAME
)
