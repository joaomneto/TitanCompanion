package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SEAS_OF_BLOOD;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestSOB extends TestST {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return SEAS_OF_BLOOD;
    }


}