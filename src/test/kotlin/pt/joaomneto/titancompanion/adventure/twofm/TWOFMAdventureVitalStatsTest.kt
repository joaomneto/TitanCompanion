package pt.joaomneto.titancompanion.adventure.twofm

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pt.joaomneto.titancompanion.adventure.AdventureVitalStatsTest
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

@RunWith(RobolectricTestRunner::class)
class TWOFMAdventureVitalStatsTest : AdventureVitalStatsTest<TWOFMAdventure, AdventureVitalStatsFragment>(
    TWOFMAdventure::class,
    AdventureVitalStatsFragment::class,
    SAVEGAME_TEXT
) {

    companion object {
        val SAVEGAME_TEXT =
            "gamebook=${FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN.name}\n" +
                "name=test\n" +
                "initialSkill=12\n" +
                "initialLuck=12\n" +
                "initialStamina=24\n" +
                "currentSkill=12\n" +
                "currentLuck=12\n" +
                "currentStamina=24\n" +
                "currentReference=1\n" +
                "equipment=\n" +
                "notes=\n" +
                "gold=10\n" +
                "standardPotion=1\n" +
                "standardPotionValue=2\n" +
                "provisions=10\n" +
                "provisionsValue=4\n"
    }
}
