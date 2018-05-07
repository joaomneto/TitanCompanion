package pt.joaomneto.titancompanion.phase1

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CRYPT_OF_THE_SORCERER

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCOTS : TestST() {

    override val gamebook = CRYPT_OF_THE_SORCERER
}
