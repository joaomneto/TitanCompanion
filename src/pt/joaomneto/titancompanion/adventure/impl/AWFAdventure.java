package pt.joaomneto.titancompanion.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import android.os.Bundle;
import android.view.Menu;

public class AWFAdventure extends Adventure {

	int heroPoints;
	String superPower;

	static final Integer FRAGMENT_NOTES = 2;

	public AWFAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.awf.AWFAdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
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
		bw.write("heroPoints="+heroPoints+"\n");
		bw.write("superPower="+superPower+"\n");
	}

	

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setHeroPoints(Integer.valueOf(getSavedGame().getProperty("heroPoints")));
		setSuperPower(getSavedGame().getProperty("superPower"));

	}

	public int getHeroPoints() {
		return heroPoints;
	}

	public void setHeroPoints(int heroPoints) {
		this.heroPoints = heroPoints;
	}

	public String getSuperPower() {
		return superPower;
	}

	public void setSuperPower(String superPower) {
		this.superPower = superPower;
	}
	
	public Integer getCombatSkillValue(){
		if(superPower.equals("Super Strength")){
			return 13;
		}
		return getCurrentSkill();
	}

}
