package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure;
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.TALISMAN_OF_DEATH;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTOD extends TestTWOFM{

    @Override
    protected FightingFantasyGamebook getGamebook(){
        return TALISMAN_OF_DEATH;
    }

}
