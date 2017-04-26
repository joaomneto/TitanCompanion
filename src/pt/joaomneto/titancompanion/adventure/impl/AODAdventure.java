package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.Army;

public class AODAdventure extends Adventure {


    public static final int FRAGMENT_SOLDIERS = 1;
    public static final int FRAGMENT_COMBAT = 2;
    public static final int FRAGMENT_EQUIPMENT = 3;
    public static final int FRAGMENT_NOTES = 4;

    private Army soldiers = new Army();


    public AODAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_SOLDIERS, new AdventureFragmentRunner(R.string.soldiers, "pt.joaomneto.titancompanion.adventure.impl.fragments.aod.AODAdventureSoldiersFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.rp.RPAdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldTreasure, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
        fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adventure, menu);
        return true;
    }

    @Override
    public void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {

        bw.write("gold=" + getGold() + "\n");
        bw.write("soldiers=" + getSoldiers().getStringToSaveGame());
    }

    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
        setSoldiers(Army.getInstanceFromSavedString(getSavedGame().getProperty("soldiers")));

    }

    public String getCurrencyName() {
        return getResources().getString(R.string.gold);
    }

    public Army getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(Army soldiers) {
        this.soldiers = soldiers;
    }



}
