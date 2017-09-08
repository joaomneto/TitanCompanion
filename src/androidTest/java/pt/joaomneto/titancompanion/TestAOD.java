package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ARMIES_OF_DEATH;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CREATURE_OF_HAVOC;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestAOD extends TestST {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return ARMIES_OF_DEATH;
    }


}