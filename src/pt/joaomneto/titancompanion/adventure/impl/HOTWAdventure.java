package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;

public class HOTWAdventure extends Adventure {

    Integer change;


    public HOTWAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.hotw.HOTWAdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
        fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"));
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
        bw.write("change=" + getChange() + "\n");
    }


    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
        setChange(Integer.valueOf(getSavedGame().getProperty("change")));

    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }
}
