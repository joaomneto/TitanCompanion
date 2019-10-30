package pt.joaomneto.titancompanion.adventure.tfod

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureEquipmentTest
import pt.joaomneto.titancompanion.adventure.impl.TFODAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment

@RunWith(RobolectricTestRunner::class)
class TFODAdventureEquipmentTest : AdventureEquipmentTest<TFODAdventure, AdventureEquipmentFragment>(
    TFODAdventure::class,
    AdventureEquipmentFragment::class,
    TFODAdventureVitalStatsTest.SAVEGAME
)
