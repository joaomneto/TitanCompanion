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
public class HOTWAdventureCreation extends AdventureCreation {

    public HOTWAdventureCreation() {
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
        bw.write("provisionsValue=4\n");
        bw.write("provisions=10\n");
        bw.write("notes=\n");
        bw.write("change=0\n");
    }


}
