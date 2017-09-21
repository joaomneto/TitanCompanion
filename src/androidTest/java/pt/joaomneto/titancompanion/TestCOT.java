package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CITY_OF_THIEVES;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestCOT extends TestTFOD{

    @Override
    protected FightingFantasyGamebook getGamebook(){
        return CITY_OF_THIEVES;
    }

}