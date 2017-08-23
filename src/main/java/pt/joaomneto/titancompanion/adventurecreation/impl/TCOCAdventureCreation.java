package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc.TCOCAdventureCreationSpellsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class TCOCAdventureCreation extends AdventureCreation {

    private final static int FRAGMENT_TCOC_SPELLS = 1;
    Map<String, Integer> spells = new HashMap<>();
    List<String> spellList = new ArrayList<>();
    private int spellValue = -1;

    public TCOCAdventureCreation() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(0, new AdventureFragmentRunner(
                R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
        fragmentConfiguration.put(FRAGMENT_TCOC_SPELLS, new AdventureFragmentRunner(
                R.string.spells,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc.TCOCAdventureCreationSpellsFragment"));

    }

    @Override
    protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
            throws IOException {

        String spellsS = "";

        if (!spells.isEmpty()) {
            for (String spell : getSpellList()) {
                spellsS += spell + "§"+spells.get(spell)+"#";
            }
            spellsS = spellsS.substring(0, spellsS.length() - 1);
        }

        bw.write("spellValue="+spellValue+"\n");
        bw.write("spells="+spellsS+"\n");
        bw.write("gold=0\n");
    }

    @Override
    public String validateCreationSpecificParameters() {
        StringBuilder sb = new StringBuilder();
        boolean error = false;
        if(this.spellValue < 0){
            sb.append(getString(R.string.spellCount));
            error = true;
        }
        sb.append(error?"; ":"");
        if(this.spells == null || this.spells.isEmpty()){
            sb.append(getString(R.string.chosenSpells));
        }

        return  sb.toString();
    }

    private TCOCAdventureCreationSpellsFragment getTCOCSpellsFragment() {
        TCOCAdventureCreationSpellsFragment tcocSpellsFragment = (TCOCAdventureCreationSpellsFragment) getFragments().get(FRAGMENT_TCOC_SPELLS);
        return tcocSpellsFragment;
    }

    @Override
    protected void rollGamebookSpecificStats(View view) {
        spellValue = DiceRoller.roll2D6().getSum()+6;
        getTCOCSpellsFragment().getSpellScoreValue().setText(""+spellValue);

    }

    public synchronized int getSpellValue() {
        return spellValue;
    }

    public synchronized List<String> getSpellList() {
        for(String spell: spells.keySet()){
            if(!spellList.contains(spell)){
                spellList.add(spell);
            }
        }

        Collections.sort(spellList);
        return spellList;
    }


    public void addSpell(String spell) {
        if(!spells.containsKey(spell)){
            spells.put(spell, 0);
        }
        spells.put(spell, spells.get(spell)+1);
    }

    public void removeSpell(int position) {
        String spell = getSpellList().get(position);
        int value = spells.get(spell) - 1;
        if(value == 0){
            spells.remove(spell);
            getSpellList().remove(position);
        }else {
            spells.put(spell, value);
        }
    }

    public Map<String, Integer> getSpells() {
        return spells;
    }

    public int getSpellListSize() {
        int size = 0;
        for (String spell: getSpellList()) {
            Integer value = spells.get(spell);
            if(value !=null ){
                size+=value;
            }else{
                size+=1;
            }
        }
        return size;
    }
}
