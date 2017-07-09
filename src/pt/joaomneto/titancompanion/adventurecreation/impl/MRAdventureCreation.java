package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc.TCOCAdventureCreationSpellsFragment;

public class MRAdventureCreation extends AdventureCreation {

    private final static int FRAGMENT_MR_SPELLS = 1;
    private final static int FRAGMENT_MR_POTION = 2;

    Set<String> chosenSkills = new HashSet<>();
    List<String> skillList = new ArrayList<>();

    public MRAdventureCreation() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(0, new AdventureFragmentRunner(
                R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
        fragmentConfiguration.put(FRAGMENT_MR_SPELLS, new AdventureFragmentRunner(
                R.string.specialSkills,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.mr.MRAdventureCreationSkillsFragment"));
        fragmentConfiguration.put(FRAGMENT_MR_POTION, new AdventureFragmentRunner(R.string.title_adventure_creation_potions,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.PotionsFragment"));

    }

    @Override
    protected void rollGamebookSpecificStats(View view) {

    }

    @Override
    protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
            throws IOException {

        String chosenSkillsS = "";

        if (!chosenSkills.isEmpty()) {
            for (String spell : getSpellList()) {
                chosenSkillsS += spell + "§"+chosenSkills.get(spell)+"#";
            }
            chosenSkillsS = chosenSkillsS.substring(0, chosenSkillsS.length() - 1);
        }

        bw.write("chosenSkills="+chosenSkillsS+"\n");
        bw.write("gold=0\n");
    }

    @Override
    public String validateCreationSpecificParameters() {
        StringBuilder sb = new StringBuilder();
        boolean error = false;
        if(this.chosenSkills == null || this.chosenSkills.isEmpty()){
            sb.append(getString(R.string.chosenSpells));
        }

        return  sb.toString();
    }

    private TCOCAdventureCreationSpellsFragment getTCOCSpellsFragment() {
        TCOCAdventureCreationSpellsFragment tcocSpellsFragment = (TCOCAdventureCreationSpellsFragment) getFragments().get(FRAGMENT_MR_SPELLS);
        return tcocSpellsFragment;
    }


    public synchronized int getSpellValue() {
        return 3;
    }

    public synchronized List<String> getSpellList() {
        for(String spell: chosenSkills){
            if(!skillList.contains(spell)){
                skillList.add(spell);
            }
        }

        Collections.sort(skillList);
        return skillList;
    }


    public void addSkill(String spell) {
        if(!chosenSkills.containsKey(spell)){
            chosenSkills.add(spell, 0);
        }
        chosenSkills.add(spell, chosenSkills.get(spell)+1);
    }

    public void removeSpell(int position) {
        String spell = getSpellList().get(position);
        int value = chosenSkills.add(spell) - 1;
        if(value == 0){
            chosenSkills.remove(spell);
        }else {
            chosenSkills.add(spell, value);
        }
    }

    public Map<String, Integer> getSpells() {
        return chosenSkills;
    }

    public int getSpellListSize() {
        int size = 0;
        for (String spell: getSpellList()) {
            Integer value = chosenSkills.get(spell);
            if(value !=null ){
                size+=value;
            }else{
                size+=1;
            }
        }
        return size;
    }
}
