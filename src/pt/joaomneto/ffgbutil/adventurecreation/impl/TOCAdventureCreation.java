package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;

/**
 * Created by Cristina on 28-07-2015.
 */
public class TOCAdventureCreation  extends TFODAdventureCreation{

    public TOCAdventureCreation() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(0, new Adventure.AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.VitalStatisticsFragment"));

    }

    @Override
    protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
            throws IOException {
        bw.write("gold=0\n");
    }
}
