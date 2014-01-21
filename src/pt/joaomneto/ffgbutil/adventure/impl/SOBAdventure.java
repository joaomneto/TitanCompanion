package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import android.os.Bundle;
import android.view.Menu;

public class SOBAdventure extends Adventure {

	private int currentCrewStrength = -1;
	private int currentCrewStrike = -1;
	private int initialCrewStrength = -1;
	private int initialCrewStrike = -1;
	private int log = -1;
	private List<String> booty = new ArrayList<String>();

	protected static final int FRAGMENT_SHIP_COMBAT = 2;
	protected static final int FRAGMENT_BOOTY = 4;
	protected static final int FRAGMENT_NOTES = 5;

	public SOBAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.ffgbutil.adventure.impl.fragments.sob.SOBAdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_SHIP_COMBAT, new AdventureFragmentRunner(R.string.shipCombat, "pt.joaomneto.ffgbutil.adventure.impl.fragments.sob.SOBShipCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_BOOTY, new AdventureFragmentRunner(R.string.booty, "pt.joaomneto.ffgbutil.adventure.impl.fragments.sob.SOBAdventureBootyFragment"));
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

		bw.write("currentCrewStrength="+currentCrewStrength+"\n");
		bw.write("currentCrewStrike="+currentCrewStrike+"\n");
		bw.write("initialCrewStrength="+initialCrewStrength+"\n");
		bw.write("initialCrewStrike="+initialCrewStrike+"\n");
		bw.write("log=" + getLog() + "\n");
		bw.write("gold=" + getGold() + "\n");
		bw.write("booty="+arrayToString(getBooty())+"\n");
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {

		setCurrentCrewStrength(Integer.valueOf(getSavedGame().getProperty("currentCrewStrength")));
		setCurrentCrewStrike(Integer.valueOf(getSavedGame().getProperty("currentCrewStrike")));
		setInitialCrewStrength(Integer.valueOf(getSavedGame().getProperty("initialCrewStrength")));
		setInitialCrewStrike(Integer.valueOf(getSavedGame().getProperty("initialCrewStrike")));
		setLog(Integer.valueOf(getSavedGame().getProperty("log")));
		setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
		setBooty(stringToArray(getSavedGame().getProperty("booty")));
	}

	public int getCurrentCrewStrength() {
		return currentCrewStrength;
	}

	public void setCurrentCrewStrength(int currentCrewStrength) {
		this.currentCrewStrength = currentCrewStrength;
	}

	public int getCurrentCrewStrike() {
		return currentCrewStrike;
	}

	public void setCurrentCrewStrike(int currentCrewStrike) {
		this.currentCrewStrike = currentCrewStrike;
	}

	public int getInitialCrewStrength() {
		return initialCrewStrength;
	}

	public void setInitialCrewStrength(int initialCrewStrength) {
		this.initialCrewStrength = initialCrewStrength;
	}

	public int getInitialCrewStrike() {
		return initialCrewStrike;
	}

	public void setInitialCrewStrike(int initialCrewStrike) {
		this.initialCrewStrike = initialCrewStrike;
	}

	public int getLog() {
		return log;
	}

	public void setLog(int log) {
		this.log = log;
	}

	public List<String> getBooty() {
		return booty;
	}

	public void setBooty(List<String> booty) {
		this.booty = booty;
	}

	

}
