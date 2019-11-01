package pt.joaomneto.titancompanion.adventure.cot

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureEquipmentTest
import pt.joaomneto.titancompanion.adventure.impl.COTAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment

@RunWith(RobolectricTestRunner::class)
class TestCOTAdventureEquipment : AdventureEquipmentTest<COTAdventure, AdventureEquipmentFragment>(
    COTAdventure::class,
    AdventureEquipmentFragment::class,
    TestCOTAdventureVitalStats.SAVEGAME
)
