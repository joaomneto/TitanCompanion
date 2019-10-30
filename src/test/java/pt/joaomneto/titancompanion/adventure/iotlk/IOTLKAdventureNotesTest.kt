package pt.joaomneto.titancompanion.adventure.iotlk

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureNotesTest
import pt.joaomneto.titancompanion.adventure.impl.IOTLKAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

@RunWith(RobolectricTestRunner::class)
class IOTLKAdventureNotesTest : AdventureNotesTest<IOTLKAdventure, AdventureNotesFragment>(
    IOTLKAdventure::class,
    AdventureNotesFragment::class,
    IOTLKAdventureVitalStatsTest.SAVEGAME
)
