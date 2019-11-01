package pt.joaomneto.titancompanion.adventure.cot

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureCombatTest
import pt.joaomneto.titancompanion.adventure.impl.COTAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

@RunWith(RobolectricTestRunner::class)
class TestCOTAdventureCombat : AdventureCombatTest<COTAdventure, AdventureCombatFragment>(
    COTAdventure::class,
    AdventureCombatFragment::class,
    TestCOTAdventureVitalStats.SAVEGAME
)
