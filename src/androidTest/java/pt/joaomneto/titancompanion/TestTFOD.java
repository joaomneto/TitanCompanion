package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;
import pt.joaomneto.titancompanion.util.LocaleHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.containsString;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTFOD extends TestTWOFM{

    @Override
    protected FightingFantasyGamebook getGamebook(){
        return THE_FOREST_OF_DOOM;
    }

    @Override
    protected void assertCorrectPotionDosage() {
        String language = LocaleHelper.getLanguage(mActivityTestRule.getActivity());
        String string = getString("fr".equals(language)?R.string.potionTwoDoses:R.string.potionOneDose);
        onView(withId(R.id.potionDosesSpinner)).check(matches(withSpinnerText(containsString(string))));
    }

}
