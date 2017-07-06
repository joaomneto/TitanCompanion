package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.SpellAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.pof.POFSpell;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

public class POFAdventure extends SpellAdventure {

    protected static final int FRAGMENT_SPELLS = 2;
    protected static final int FRAGMENT_EQUIPMENT = 3;
    protected static final int FRAGMENT_NOTES = 4;
    Set<String> visitedClearings = new HashSet<String>();
    private int currentPower = -1;
    private int initialPower = -1;


    public POFAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.pof.POFAdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_SPELLS, new AdventureFragmentRunner(R.string.spells, "pt.joaomneto.titancompanion.adventure.impl.fragments.pof.POFAdventureSpellsFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
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


        bw.write("currentPower=" + getCurrentPower() + "\n");
        bw.write("initialPower=" + getInitialPower() + "\n");
        bw.write("standardPotion=" + getStandardPotion() + "\n");
        bw.write("standardPotionValue=" + getStandardPotionValue() + "\n");
        bw.write("provisions=0\n");
        bw.write("provisionsValue=4\n");
        bw.write("gold=" + getGold() + "\n");
        bw.write("currentPower=" + getGold() + "\n");
        bw.write("initialPower=" + getGold() + "\n");
    }

    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        setStandardPotion(Integer.valueOf(getSavedGame()
                .getProperty("standardPotion")));
        setStandardPotionValue(Integer.valueOf(getSavedGame()
                .getProperty("standardPotionValue")));
        setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
        setCurrentPower(Integer.valueOf(getSavedGame().getProperty("currentPower")));
        setInitialPower(Integer.valueOf(getSavedGame().getProperty("initialPower")));
        setSpells(getSpellList());

    }


    public boolean isSpellSingleUse() {
        return false;
    }

    public List<Spell> getSpellList() {
        return Arrays.asList(POFSpell.values());
    }

    public int getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(int currentPower) {
        this.currentPower = currentPower;
    }

    public int getInitialPower() {
        return initialPower;
    }

    public void setInitialPower(int initialPower) {
        this.initialPower = initialPower;
    }
}
