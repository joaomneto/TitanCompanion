package pt.joaomneto.titancompanion.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;

import android.os.Bundle;
import android.view.Menu;

public class TCOCAdventure extends Adventure {

    Map<String, Integer> spells = new HashMap<>();
    List<String> spellList = new ArrayList<>();
    Integer spellValue;

    protected static final int FRAGMENT_SPELLS = 2;
    protected static final int FRAGMENT_EQUIPMENT = 3;
    protected static final int FRAGMENT_NOTES = 4;

    public TCOCAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration
                .put(FRAGMENT_VITAL_STATS,
                        new AdventureFragmentRunner(R.string.vitalStats,
                                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
        fragmentConfiguration
                .put(FRAGMENT_COMBAT,
                        new AdventureFragmentRunner(R.string.fights,
                                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
        fragmentConfiguration
                .put(FRAGMENT_SPELLS,
                        new AdventureFragmentRunner(
                                R.string.spells,
                                "pt.joaomneto.titancompanion.adventure.impl.fragments.tcoc.TCOCAdventureSpellsFragment"));
        fragmentConfiguration
                .put(FRAGMENT_EQUIPMENT,
                        new AdventureFragmentRunner(R.string.goldEquipment,
                                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
        fragmentConfiguration
                .put(FRAGMENT_NOTES,
                        new AdventureFragmentRunner(R.string.notes,
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
    public void storeAdventureSpecificValuesInFile(BufferedWriter bw)
            throws IOException {

        String spellsS = "";

        if (!spells.isEmpty()) {
            for (String spell : getSpellList()) {
                spellsS += spell + "§"+spells.get(spell)+"#";
            }
            spellsS = spellsS.substring(0, spellsS.length() - 1);
        }

        bw.write("spells=" + spellsS + "\n");
        bw.write("spellValue=" + spellValue + "\n");
        bw.write("gold=" + getGold() + "\n");
    }


    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
        spellValue = Integer.valueOf(getSavedGame().getProperty("spellValue"));
        String spellsS = new String(getSavedGame().getProperty("spells")
                .getBytes(java.nio.charset.Charset.forName("UTF-8")));

        if (spellsS != null) {
            spells = new HashMap<>();
            List<String> list = Arrays.asList(spellsS.split("#"));
            for (String string : list) {
                if (!string.isEmpty()) {
                    String[] split = string.split("§");
                    spells.put(split[0], Integer.parseInt(split[1]));
                }
            }

        }

    }

    public synchronized List<String> getSpellList() {
        for (String spell : spells.keySet()) {
            if (!spellList.contains(spell)) {
                spellList.add(spell);
            }
        }

        Collections.sort(spellList);
        return spellList;
    }


    public void removeSpell(int position) {
        String spell = getSpellList().get(position);
        int value = spells.get(spell) - 1;
        if (value == 0) {
            spells.remove(spell);
            getSpellList().remove(spell);
        } else {
            spells.put(spell, value);
        }
    }

    public Map<String, Integer> getSpells() {
        return spells;
    }

    public int getSpellListSize() {
        int size = 0;
        for (String spell : getSpellList()) {
            Integer value = spells.get(spell);
            if (value != null) {
                size += value;
            } else {
                size += 1;
            }
        }
        return size;
    }

}
