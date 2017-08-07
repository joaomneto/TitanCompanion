package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAVitalStatisticsFragment;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeapon;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeaponsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;
import android.view.View;

public class SAAdventureCreation extends AdventureCreation {

	
	private int currentArmor = -1;
	private int currentWeapons = -1;
	List<SAWeapon> weapons = new ArrayList<SAWeapon>();

	public SAAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAVitalStatisticsFragment"));
		fragmentConfiguration.put(1, new AdventureFragmentRunner(
				R.string.title_adventure_creation_weapons,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeaponsFragment"));
		
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentArmor="+currentArmor+"\n");
		bw.write("currentWeapons="+currentWeapons+"\n");
		bw.write("weapons="+Adventure.arrayToString(weapons)+"\n");
		bw.write("provisions=4\n");
		bw.write("provisionsValue=5\n");
	}
	
	private SAVitalStatisticsFragment getSAVitalStatisticsFragmentt() {
		SAVitalStatisticsFragment saVitalStatisticsFragment = (SAVitalStatisticsFragment) getFragments().get(0);
		return saVitalStatisticsFragment;
	}
	
	private SAWeaponsFragment getSAWeaponsFragment() {
		SAWeaponsFragment saWeaponsFragment = (SAWeaponsFragment) getFragments().get(1);
		return saWeaponsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		currentArmor = DiceRoller.rollD6()+6;
		getSAVitalStatisticsFragmentt().getArmorValue().setText(String.valueOf(currentArmor));
		currentWeapons = DiceRoller.rollD6();
		getSAWeaponsFragment().getWeaponsValue().setText(String.valueOf(currentWeapons));
		
	}

	public int getCurrentArmor() {
		return currentArmor;
	}

	public void setCurrentArmor(int currentArmor) {
		this.currentArmor = currentArmor;
	}

	public int getCurrentWeapons() {
		return currentWeapons;
	}

	public void setCurrentWeapons(int currentWeapons) {
		this.currentWeapons = currentWeapons;
	}


	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.currentArmor < 0 || this.getWeapons().isEmpty()){
			sb.append(getString(R.string.weaponsArmorMandatory));
		}
		return  sb.toString();
	}


	public List<SAWeapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<SAWeapon> weapons) {
		this.weapons = weapons;
	}



}
