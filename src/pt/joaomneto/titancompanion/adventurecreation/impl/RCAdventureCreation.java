package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;

/**
 * Created by Cristina on 28-07-2015.
 */
public class RCAdventureCreation extends TFODAdventureCreation{

    public RCAdventureCreation() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(0, new Adventure.AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));

    }

    @Override
    protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
            throws IOException {
        bw.write("gold=0\n");
        bw.write("robotArmor=0\n");
        bw.write("robotSpeed=0\n");
        bw.write("robotCombat=0\n");
        bw.write("robotBonus=0\n");
        bw.write("provisions=5\n");
        bw.write("provisionsValue=1\n");

    }
}
