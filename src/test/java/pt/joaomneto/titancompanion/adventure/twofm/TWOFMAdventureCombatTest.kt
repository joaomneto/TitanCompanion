package pt.joaomneto.titancompanion.adventure.twofm

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureCombatTest
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

@RunWith(RobolectricTestRunner::class)
class TWOFMAdventureCombatTest : AdventureCombatTest<TWOFMAdventure, AdventureCombatFragment>(
    TWOFMAdventure::class,
    AdventureCombatFragment::class,
    TWOFMAdventureVitalStatsTest.SAVEGAME
)
