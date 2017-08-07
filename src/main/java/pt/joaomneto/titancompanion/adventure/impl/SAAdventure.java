package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.sa.SAAdventureWeaponsFragment;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeapon;

public class SAAdventure extends Adventure {

    static final Integer FRAGMENT_WEAPONS = 2;
    static final Integer FRAGMENT_EQUIPMENT = 3;
    static final Integer FRAGMENT_NOTES = 4;
    Integer currentArmor;
    List<SAWeapon> weapons = new ArrayList<>();

    public SAAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.sa.SAAdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.sa.SAAdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_WEAPONS, new AdventureFragmentRunner(R.string.title_adventure_creation_weapons,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.sa.SAAdventureWeaponsFragment"));
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

        bw.write("currentArmor=" + currentArmor + "\n");
        bw.write("weapons=" + arrayToString(weapons) + "\n");
        bw.write("provisions=4\n");
        bw.write("provisionsValue=5\n");
    }

    @Override
    protected void loadAdventureSpecificValuesFromFile() {
        setCurrentArmor(Integer.valueOf(getSavedGame().getProperty("currentArmor")));
        List<String> weaponsStrings = stringToArray(getSavedGame().getProperty("weapons"));
        for (String weaponName : weaponsStrings
                ) {
            weapons.add(SAWeapon.valueOf(weaponName));
        }
    }

    public Integer getCurrentArmor() {
        return currentArmor;
    }

    public void setCurrentArmor(Integer currentArmor) {
        this.currentArmor = currentArmor;
    }

    public List<SAWeapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<SAWeapon> weapons) {
        this.weapons = weapons;
    }

    public SAAdventureWeaponsFragment getVitalStatsFragment() {
        return (SAAdventureWeaponsFragment) getFragments().get(FRAGMENT_WEAPONS);
    }

    public String getConsumeProvisionText() {
        return getResources().getString(R.string.usePepPill);
    }

    public String getProvisionsText() {
        return getResources().getString(R.string.pepPills);
    }

}
