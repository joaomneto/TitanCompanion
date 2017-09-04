package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.PHANTOMS_OF_FEAR;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestPOF extends TestTWOFM {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return PHANTOMS_OF_FEAR;
    }

}
