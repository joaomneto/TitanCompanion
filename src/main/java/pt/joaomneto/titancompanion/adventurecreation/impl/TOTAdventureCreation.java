package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;

public class TOTAdventureCreation extends AdventureCreation {
    public TOTAdventureCreation() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration
                .put(0,
                        new AdventureFragmentRunner(
                                R.string.title_adventure_creation_vitalstats,
                                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));

    }

    @Override
    protected void rollGamebookSpecificStats(View view) {

    }

    @Override
    protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {

    }


    @Override
    public String validateCreationSpecificParameters() {
        return null;
    }
}
