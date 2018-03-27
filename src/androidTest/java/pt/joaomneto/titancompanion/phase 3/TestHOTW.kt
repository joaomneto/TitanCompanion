package pt.joaomneto.titancompanion.phase2

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.phase1.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestHOTW : TestST() {

    override val gamebook = FightingFantasyGamebook.HOWL_OF_THE_WEREWOLF
}