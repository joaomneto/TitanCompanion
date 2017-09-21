package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.DEATHTRAP_DUNGEON;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestDD extends TestTFOD{

    @Override
    protected FightingFantasyGamebook getGamebook(){
        return DEATHTRAP_DUNGEON;
    }

}