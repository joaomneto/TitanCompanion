package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sa.SAVitalStatisticsFragment;
import pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sa.SAWeaponsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.view.View;

public class SAAdventureCreation extends AdventureCreation {

	
	private int currentArmor = -1;
	private int currentWeapons = -1;
	List<String> weapons = new ArrayList<String>();
	
	public List<String> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<String> weapons) {
		this.weapons = weapons;
	}

	public SAAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sa.SAVitalStatisticsFragment"));
		fragmentConfiguration.put(1, new AdventureFragmentRunner(
				R.string.title_adventure_creation_weapons,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sa.SAWeaponsFragment"));
		
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentArmor="+currentArmor+"\n");
		bw.write("currentWeapons="+currentWeapons+"\n");
		bw.write("weapons="+Adventure.arrayToString(weapons)+"\n");
	}
	
	private SAVitalStatisticsFragment getSAVitalStatisticsFragmentt() {
		SAVitalStatisticsFragment saVitalStatisticsFragment = (SAVitalStatisticsFragment) getSupportFragmentManager()
				.getFragments().get(0);
		return saVitalStatisticsFragment;
	}
	
	private SAWeaponsFragment getSAWeaponsFragment() {
		SAWeaponsFragment saWeaponsFragment = (SAWeaponsFragment) getSupportFragmentManager()
				.getFragments().get(1);
		return saWeaponsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		currentArmor = DiceRoller.rollD6()+6;
		getSAVitalStatisticsFragmentt().getArmorValue().setText(""+currentArmor);
		currentWeapons = DiceRoller.rollD6();
		getSAWeaponsFragment().getWeaponsValue().setText(""+currentWeapons);
		
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

	


	
	

}
