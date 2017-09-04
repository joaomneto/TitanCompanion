package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CRYPT_OF_THE_SORCERER;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestCOTS extends TestST {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return CRYPT_OF_THE_SORCERER;
    }


}