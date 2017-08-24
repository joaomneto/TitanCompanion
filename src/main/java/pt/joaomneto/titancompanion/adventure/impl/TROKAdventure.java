package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;

public class TROKAdventure extends Adventure {

	protected static final int FRAGMENT_VEHICLE_COMBAT = 2;
	protected static final int FRAGMENT_EQUIPMENT = 3;
	protected static final int FRAGMENT_NOTES = 4;
	private int currentWeapons = -1;
	private int currentShields = -1;
	private int initialWeapons = -1;
	private int initialShields = -1;
	private int missiles = -1;

	public TROKAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.trok.TROKAdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_VEHICLE_COMBAT, new AdventureFragmentRunner(R.string.starshipCombat, "pt.joaomneto.titancompanion.adventure.impl.fragments.trok.TROKStarShipCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.moneyEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
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

		bw.write("currentWeapons=" + currentWeapons + "\n");
		bw.write("currentShields=" + currentShields + "\n");
		bw.write("initialWeapons=" + initialWeapons + "\n");
		bw.write("initialShields=" + initialShields + "\n");
		bw.write("missiles=" + missiles + "\n");
		bw.write("gold=" + getGold() + "\n");
		bw.write("provisions=" + getProvisions() + "\n");
		bw.write("provisionsValue=" + getProvisionsValue() + "\n");
	}

	public int getCurrentWeapons() {
		return currentWeapons;
	}

	public void setCurrentWeapons(int currentWeapons) {
		this.currentWeapons = currentWeapons;
	}

	public int getCurrentShields() {
		return currentShields;
	}

	public void setCurrentShields(int currentShields) {
		this.currentShields = currentShields;
	}

	public int getInitialWeapons() {
		return initialWeapons;
	}

	public void setInitialWeapons(int initialWeapons) {
		this.initialWeapons = initialWeapons;
	}

	public int getInitialShields() {
		return initialShields;
	}

	public void setInitialShields(int initialShields) {
		this.initialShields = initialShields;
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {

		setCurrentWeapons(Integer.valueOf(getSavedGame().getProperty("currentWeapons")));
		setCurrentShields(Integer.valueOf(getSavedGame().getProperty("currentShields")));
		setInitialWeapons(Integer.valueOf(getSavedGame().getProperty("initialWeapons")));
		setInitialShields(Integer.valueOf(getSavedGame().getProperty("initialShields")));
		setMissiles(Integer.valueOf(getSavedGame().getProperty("missiles")));
		setProvisions(Integer.valueOf(getSavedGame().getProperty("provisions")));
		setProvisionsValue(Integer.valueOf(getSavedGame().getProperty("provisionsValue")));
		setGold(Integer.valueOf(getSavedGame().getProperty("gold")));

	}

	public int getMissiles() {
		return missiles;
	}

	public void setMissiles(int missiles) {
		this.missiles = missiles;
	}

	public String getConsumeProvisionText() {
		return getResources().getString(R.string.usePepPill);
	}
	

	public String getProvisionsText() {
		return getResources().getString(R.string.pepPills);
	}
	
	public String getCurrencyName() {
		return getResources().getString(R.string.kopecks);
	}

}
