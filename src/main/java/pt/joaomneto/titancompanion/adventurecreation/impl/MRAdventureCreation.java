package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRSkill;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.mr.MRAdventureCreationSkillsFragment;

public class MRAdventureCreation extends TWOFMAdventureCreation {

    private final static int FRAGMENT_MR_SPELLS = 1;
    private final static int FRAGMENT_MR_POTION = 2;

    List<Spell> chosenSkills = new ArrayList<>();

    public MRAdventureCreation() {
        super();
        Companion.getFragmentConfiguration().clear();
        Companion.getFragmentConfiguration().put(0, new AdventureFragmentRunner(
                R.string.title_adventure_creation_vitalstats,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
        Companion.getFragmentConfiguration().put(FRAGMENT_MR_SPELLS, new AdventureFragmentRunner(
                R.string.specialSkills,
                "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.mr.MRAdventureCreationSkillsFragment"));
        Companion.getFragmentConfiguration().put(FRAGMENT_MR_POTION, new AdventureFragmentRunner(R.string.title_adventure_creation_potions,
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
            for (Spell spell : getSkills()) {
                chosenSkillsS += spell + "#";
            }
            chosenSkillsS = chosenSkillsS.substring(0, chosenSkillsS.length() - 1);
        }

        bw.write("skills=" + chosenSkillsS + "\n");
        bw.write("provisionsValue=4\n");
        bw.write("provisions=10\n");
        bw.write("gold=0\n");
    }

    @Override
    public String validateCreationSpecificParameters() {
        StringBuilder sb = new StringBuilder();
        boolean error = false;
        if (this.chosenSkills == null || this.chosenSkills.isEmpty()) {
            sb.append(getString(R.string.chosenSkills));
        }

        return sb.toString();
    }

    private MRAdventureCreationSkillsFragment getMRSkillFragment() {
        MRAdventureCreationSkillsFragment mrSkillFragment = (MRAdventureCreationSkillsFragment) Companion.getFragments().get(FRAGMENT_MR_SPELLS);
        return mrSkillFragment;
    }


    public synchronized int getSpellValue() {
        return 3;
    }

    public void addSkill(MRSkill skill) {
        if (!chosenSkills.contains(skill)) {
            chosenSkills.add(skill);
        }
    }

    public void removeSkill(int skill) {
        chosenSkills.remove(skill);
    }

    public List<Spell> getSkills() {
        return chosenSkills;
    }
}
