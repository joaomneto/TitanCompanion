package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventurecreation.impl.SOTSAdventureCreation;
import android.os.Bundle;
import android.view.Menu;

public class SOTSAdventure extends Adventure {

	int currentHonour = -1;
	int willowLeafArrows = -1;
	int bowelRakerArrows = -1;
	int armourPiercerArrows = -1;
	int hummingBulbArrows = -1;
	String skill = "";

	static Integer FRAGMENT_20STOS_ARROWS = 2;
	static Integer FRAGMENT_COMBAT = 3;
	static Integer FRAGMENT_PROVISIONS = 4;
	static Integer FRAGMENT_EQUIPMENT = 5;
	static Integer FRAGMENT_NOTES = 6;

	public SOTSAdventure() {
		super();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_20STOS_ARROWS, new AdventureFragmentRunner(R.string.arrows, "pt.joaomneto.ffgbutil.adventure.impl.fragments.sots.SOTSAdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.ffgbutil.adventure.impl.fragments.sots.SOTSAdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_PROVISIONS, new AdventureFragmentRunner(R.string.potionsProvisions, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureProvisionsFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureNotesFragment"));
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
		bw.write("skill=" + getSkill() + "\n");
		bw.write("armourPiercerArrows=" + getArmourPiercerArrows() + "\n");
		bw.write("bowelRakerArrows=" + getBowelRakerArrows() + "\n");
		bw.write("hummingBulbArrows=" + getHummingBulbArrows() + "\n");
		bw.write("willowLeafArrows=" + getWillowLeafArrows() + "\n");
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setCurrentHonour(Integer.valueOf(getSavedGame().getProperty("honour")));
		setSkill(getSavedGame().getProperty("skill"));
		setArmourPiercerArrows(Integer.parseInt(getSavedGame().getProperty("armourPiercerArrows")));
		setBowelRakerArrows(Integer.parseInt(getSavedGame().getProperty("bowelRakerArrows")));
		setHummingBulbArrows(Integer.parseInt(getSavedGame().getProperty("hummingBulbArrows")));
		setWillowLeafArrows(Integer.parseInt(getSavedGame().getProperty("willowLeafArrows")));
		
		if(!skill.equals(SOTSAdventureCreation.SOTS20_KYUJUTSTU)){
			
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

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

}
