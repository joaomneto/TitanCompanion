package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;

public class HOTWAdventure extends Adventure {

    Integer change;
    List<String> keywords = new ArrayList<>();


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
                "pt.joaomneto.titancompanion.adventure.impl.fragments.hotw.HOTWAdventureNotesFragment"));
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

        String keywordS = "";

        if (!keywords.isEmpty()) {
            for (String note : keywords) {
                keywordS += note + "#";
            }
            keywordS = keywordS.substring(0, keywordS.length() - 1);
        }

        bw.write("gold=" + getGold() + "\n");
        bw.write("keywords=" + keywordS + "\n");
        bw.write("change=" + getChange() + "\n");
    }


    @Override
    protected void loadAdventureSpecificValuesFromFile() {


        setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
        setChange(Integer.valueOf(getSavedGame().getProperty("change")));

        String keywordsValue = getSavedGame().getProperty("keywords");
        if (keywordsValue != null) {
            String keywordsS = new String(keywordsValue.getBytes(java.nio.charset.Charset.forName("UTF-8")));

            if (keywordsS != null) {
                this.keywords = new ArrayList<String>();
                List<String> list = Arrays.asList(keywordsS.split("#"));
                for (String string : list) {
                    if (!string.isEmpty())
                        this.keywords.add(string);
                }
            }
        }


    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}
