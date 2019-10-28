package pt.joaomneto.titancompanion.adventure.iotlk

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureCombatTest
import pt.joaomneto.titancompanion.adventure.impl.IOTLKAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

@RunWith(RobolectricTestRunner::class)
class IOTLKAdventureCombatTest : AdventureCombatTest<IOTLKAdventure, AdventureCombatFragment>(
    IOTLKAdventure::class,
    AdventureCombatFragment::class,
    IOTLKAdventureVitalStatsTest.SAVEGAME
)
