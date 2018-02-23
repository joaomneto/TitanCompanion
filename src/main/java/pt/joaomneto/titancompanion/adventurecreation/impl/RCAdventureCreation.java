package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;

/**
 * Created by Cristina on 28-07-2015.
 */
public class RCAdventureCreation extends AdventureCreation {

    public RCAdventureCreation() {
        super();
        getFragmentConfiguration().clear();
        getFragmentConfiguration().put(0, new Adventure.AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));

    }

    @Override
    protected void rollGamebookSpecificStats(View view) {

    }


    @Override
    public String validateCreationSpecificParameters() {
        return null;
    }

    @Override
    protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
            throws IOException {
        bw.write("gold=0\n");
        bw.write("robots=\n");
        bw.write("provisions=5\n");
        bw.write("provisionsValue=1\n");

    }
}
