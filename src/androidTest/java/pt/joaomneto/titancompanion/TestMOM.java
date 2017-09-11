package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.MASKS_OF_MAYHEM;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestMOM extends TestTFOD {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return MASKS_OF_MAYHEM;
    }

}
