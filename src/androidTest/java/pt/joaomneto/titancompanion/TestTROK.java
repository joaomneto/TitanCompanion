package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_RINGS_OF_KETHER;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTROK extends TestST {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return THE_RINGS_OF_KETHER;
    }


}