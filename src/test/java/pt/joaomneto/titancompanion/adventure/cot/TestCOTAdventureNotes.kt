package pt.joaomneto.titancompanion.adventure.cot

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureNotesTest
import pt.joaomneto.titancompanion.adventure.impl.COTAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

@RunWith(RobolectricTestRunner::class)
class TestCOTAdventureNotes : AdventureNotesTest<COTAdventure, AdventureNotesFragment>(
    COTAdventure::class,
    AdventureNotesFragment::class,
    TestCOTAdventureVitalStats.SAVEGAME
)
