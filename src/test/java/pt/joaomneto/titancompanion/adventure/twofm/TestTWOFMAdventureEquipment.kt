package pt.joaomneto.titancompanion.adventure.twofm

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureEquipmentTest
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment

@RunWith(RobolectricTestRunner::class)
class TestTWOFMAdventureEquipment : AdventureEquipmentTest<TWOFMAdventure, AdventureEquipmentFragment>(
    TWOFMAdventure::class,
    AdventureEquipmentFragment::class,
    TestTWOFMAdventureVitalStats.SAVEGAME
)
