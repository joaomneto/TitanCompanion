package pt.joaomneto.titancompanion.adventure.impl;


import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;

public class COHAdventure extends TFODAdventure {

    private static final int FRAGMENT_EQUIPMENT = 2;
    private static final int FRAGMENT_NOTES = 3;


    public COHAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.coh.COHAdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
        fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"));
    }

    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
    }

    @Override
    public void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
        bw.write("gold=" + getGold() + "\n");
    }

}
