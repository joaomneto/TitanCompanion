package pt.joaomneto.titancompanion.adventure.twofm

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureNotesTest
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

@RunWith(RobolectricTestRunner::class)
class TestTWOFMAdventureNotes : AdventureNotesTest<TWOFMAdventure, AdventureNotesFragment>(
    TWOFMAdventure::class,
    AdventureNotesFragment::class,
    TestTWOFMAdventureVitalStats.SAVEGAME
)
