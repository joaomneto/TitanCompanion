package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.SpellAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRSkill;

public class MRAdventure extends SpellAdventure<MRSkill> {

    protected static final int FRAGMENT_SPELLS = 2;
    protected static final int FRAGMENT_EQUIPMENT = 3;
    protected static final int FRAGMENT_NOTES = 4;
    List<MRSkill> skills = new ArrayList<>();

    public MRAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_SPELLS, new AdventureFragmentRunner(R.string.chosenSkills,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment"));
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

        bw.write("skills=" + arrayToStringSpells(skills) + "\n");
        bw.write("gold=" + getGold() + "\n");
    }


    public List<MRSkill> getSpells() {
        return skills;
    }

    public void setSpells(List<MRSkill> skills) {
        this.skills = skills;
    }

    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        setGold(Integer.valueOf(getSavedGame().getProperty("gold")));

        setSpells(stringToArraySpells(new String(getSavedGame().getProperty("skills").getBytes(java.nio.charset.Charset.forName("ISO-8859-1"))), MRSkill.class));

    }

    public List<MRSkill> getSpellList() {
        return Arrays.asList(MRSkill.values());
    }

    public boolean isSpellSingleUse() {
        return false;
    }

}
