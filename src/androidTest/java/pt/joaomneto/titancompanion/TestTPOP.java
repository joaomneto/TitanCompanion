package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTPOP extends TestTWOFM {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return FightingFantasyGamebook.THE_PORT_OF_PERIL;
    }


}