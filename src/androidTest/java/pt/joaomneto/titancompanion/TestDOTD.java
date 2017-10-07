package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.DEMONS_OF_THE_DEEP;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestDOTD extends TestTFOD {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return DEMONS_OF_THE_DEEP;
    }

}
