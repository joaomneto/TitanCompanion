package pt.joaomneto.titancompanion.adventure.dd

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureEquipmentTest
import pt.joaomneto.titancompanion.adventure.cot.TestCOTAdventureVitalStats
import pt.joaomneto.titancompanion.adventure.impl.DDAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment

@RunWith(RobolectricTestRunner::class)
class TestDDAdventureEquipment : AdventureEquipmentTest<DDAdventure, AdventureEquipmentFragment>(
    DDAdventure::class,
    AdventureEquipmentFragment::class,
    TestCOTAdventureVitalStats.SAVEGAME
)
