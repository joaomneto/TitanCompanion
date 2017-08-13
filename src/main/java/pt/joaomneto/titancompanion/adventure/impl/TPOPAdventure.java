package pt.joaomneto.titancompanion.adventure.impl;


import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;

public class TPOPAdventure extends TWOFMAdventure {

    int copper = 0;

    public TPOPAdventure() {
        super();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tpop.TPOPAdventureEquipmentFragment"));
        fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"));

    }

    public int getCopper() {
        return copper;
    }

    public void setCopper(int copper) {
        this.copper = copper;
    }

    @Override
    public void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {

        bw.write("copper=" + copper + "\n");
    }

    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        String copper = getSavedGame().getProperty("copper");

        this.copper = Integer.valueOf(copper!=null && copper.length()>0?copper:"0");
    }

}
