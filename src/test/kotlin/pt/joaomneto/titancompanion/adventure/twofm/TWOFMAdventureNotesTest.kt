package pt.joaomneto.titancompanion.adventure.twofm

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureNotesTest
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment

@RunWith(RobolectricTestRunner::class)
class TWOFMAdventureNotesTest : AdventureNotesTest<TWOFMAdventure, AdventureNotesFragment>(
    TWOFMAdventure::class,
    AdventureNotesFragment::class,
    TWOFMAdventureVitalStatsTest.SAVEGAME_TEXT
)
