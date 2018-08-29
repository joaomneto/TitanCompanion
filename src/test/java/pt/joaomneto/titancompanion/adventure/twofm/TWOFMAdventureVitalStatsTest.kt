package pt.joaomneto.titancompanion.adventure.twofm

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureVitalStatsTest
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import org.apache.commons.collections4.MapUtils

@RunWith(RobolectricTestRunner::class)
class TWOFMAdventureVitalStatsTest : AdventureVitalStatsTest<TWOFMAdventure, AdventureVitalStatsFragment>(
    TWOFMAdventure::class,
    AdventureVitalStatsFragment::class,
    SAVEGAME
) {

    companion object {
        val SAVEGAME = MapUtils.toProperties(
            mapOf(
                MainState::gamebook.name to FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN.name,
                MainState::name.name to "test",
                MainState::initialSkill.name to "12",
                MainState::initialLuck.name to "12",
                MainState::initialStamina.name to "24",
                MainState::currentSkill.name to "12",
                MainState::currentLuck.name to "12",
                MainState::currentStamina.name to "24",
                MainState::currentReference.name to "1",
                MainState::equipment.name to "",
                MainState::notes.name to "",
                MainState::gold.name to "10",
                MainState::standardPotion.name to "1",
                MainState::standardPotionValue.name to "2",
                MainState::provisions.name to "10",
                MainState::provisionsValue.name to "4"
            )
        )!!
    }
}
