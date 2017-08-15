package pt.joaomneto.titancompanion.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSMartialArt;

import android.os.Bundle;
import android.view.Menu;

public class SOTSAdventure extends TFODAdventure {

	int currentHonour = -1;
	int willowLeafArrows = -1;
	int bowelRakerArrows = -1;
	int armourPiercerArrows = -1;
	int hummingBulbArrows = -1;
	SOTSMartialArt skill = null;

	static Integer FRAGMENT_EQUIPMENT = 2;
	static Integer FRAGMENT_NOTES = 3;
	
	public SOTSAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.sots.SOTSAdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.sots.SOTSAdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.sots.SOTSAdventureEquipmentFragment"));
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
		bw.write("honour=" + getCurrentHonour() + "\n");
		bw.write("skill=" + getSkill().name() + "\n");
		if (SOTSMartialArt.KYUJUTSU == skill) {
			bw.write("armourPiercerArrows=" + getArmourPiercerArrows() + "\n");
			bw.write("bowelRakerArrows=" + getBowelRakerArrows() + "\n");
			bw.write("hummingBulbArrows=" + getHummingBulbArrows() + "\n");
			bw.write("willowLeafArrows=" + getWillowLeafArrows() + "\n");
		}
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setCurrentHonour(Integer.valueOf(getSavedGame().getProperty("honour")));
		setSkill(SOTSMartialArt.valueOf(getSavedGame().getProperty("skill")));
		if (SOTSMartialArt.KYUJUTSU == skill) {
			setArmourPiercerArrows(Integer.parseInt(getSavedGame().getProperty("armourPiercerArrows")));
			setBowelRakerArrows(Integer.parseInt(getSavedGame().getProperty("bowelRakerArrows")));
			setHummingBulbArrows(Integer.parseInt(getSavedGame().getProperty("hummingBulbArrows")));
			setWillowLeafArrows(Integer.parseInt(getSavedGame().getProperty("willowLeafArrows")));
		}
	}

	public int getCurrentHonour() {
		return currentHonour;
	}

	public void setCurrentHonour(int currentHonour) {
		this.currentHonour = currentHonour;
	}

	public int getWillowLeafArrows() {
		return willowLeafArrows;
	}

	public void setWillowLeafArrows(int willowLeafArrows) {
		this.willowLeafArrows = willowLeafArrows;
	}

	public int getBowelRakerArrows() {
		return bowelRakerArrows;
	}

	public void setBowelRakerArrows(int bowelRakerArrows) {
		this.bowelRakerArrows = bowelRakerArrows;
	}

	public int getArmourPiercerArrows() {
		return armourPiercerArrows;
	}

	public void setArmourPiercerArrows(int armourPiercerArrows) {
		this.armourPiercerArrows = armourPiercerArrows;
	}

	public int getHummingBulbArrows() {
		return hummingBulbArrows;
	}

	public void setHummingBulbArrows(int hummingBulbArrows) {
		this.hummingBulbArrows = hummingBulbArrows;
	}

	public SOTSMartialArt getSkill() {
		return skill;
	}

	public void setSkill(SOTSMartialArt skill) {
		this.skill = skill;
	}

}
