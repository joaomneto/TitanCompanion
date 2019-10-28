package pt.joaomneto.titancompanion.adventure.tfod

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureNotesTest
import pt.joaomneto.titancompanion.adventure.impl.TFODAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

@RunWith(RobolectricTestRunner::class)
class TFODAdventureNotesTest : AdventureNotesTest<TFODAdventure, AdventureNotesFragment>(
    TFODAdventure::class,
    AdventureNotesFragment::class,
    TFODAdventureVitalStatsTest.SAVEGAME
)
