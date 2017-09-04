package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestHOTW extends TestST {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return FightingFantasyGamebook.HOWL_OF_THE_WEREWOLF;
    }


}