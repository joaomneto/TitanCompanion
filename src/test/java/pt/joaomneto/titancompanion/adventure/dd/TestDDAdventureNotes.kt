package pt.joaomneto.titancompanion.adventure.dd

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureNotesTest
import pt.joaomneto.titancompanion.adventure.cot.TestCOTAdventureVitalStats
import pt.joaomneto.titancompanion.adventure.impl.DDAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

@RunWith(RobolectricTestRunner::class)
class TestDDAdventureNotes : AdventureNotesTest<DDAdventure, AdventureNotesFragment>(
    DDAdventure::class,
    AdventureNotesFragment::class,
    TestCOTAdventureVitalStats.SAVEGAME
)
