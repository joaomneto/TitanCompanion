package pt.joaomneto.titancompanion.adventure.iotlk

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureEquipmentTest
import pt.joaomneto.titancompanion.adventure.impl.IOTLKAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment

@RunWith(RobolectricTestRunner::class)
class TestIOTLKAdventureEquipment : AdventureEquipmentTest<IOTLKAdventure, AdventureEquipmentFragment>(
    IOTLKAdventure::class,
    AdventureEquipmentFragment::class,
    TestIOTLKAdventureVitalStats.SAVEGAME
)
