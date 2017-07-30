package pt.joaomneto.titancompanion.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import android.os.Bundle;
import android.view.Menu;

public class FFAdventure extends Adventure {

	private int currentFirepower = -1;
	private int currentArmour = -1;
	private int initialFirepower = -1;
	private int initialArmour = -1;
	private int rockets = -1;
	private int ironSpikes = -1;
	private int oilCannisters = -1;
	private int spareWheels = -1;

	List<String> carEnhancements = new ArrayList<String>();

	protected static final int FRAGMENT_VEHICLE_COMBAT = 2;
	protected static final int FRAGMENT_VEHICLE_EQUIPMENT = 3;
	protected static final int FRAGMENT_EQUIPMENT = 4;
	protected static final int FRAGMENT_NOTES = 5;

	public FFAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.ff.FFAdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_VEHICLE_COMBAT, new AdventureFragmentRunner(R.string.vehicleCombat, "pt.joaomneto.titancompanion.adventure.impl.fragments.ff.FFVehicleCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_VEHICLE_EQUIPMENT, new AdventureFragmentRunner(R.string.vehicleCombat, "pt.joaomneto.titancompanion.adventure.impl.fragments.ff.FFVehicleStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
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

		bw.write("currentFirepower=" + currentFirepower + "\n");
		bw.write("currentArmour=" + currentArmour + "\n");
		bw.write("initialFirepower=" + initialFirepower + "\n");
		bw.write("initialArmour=" + initialArmour + "\n");
		bw.write("rockets=" + rockets + "\n");
		bw.write("ironSpikes=" + ironSpikes + "\n");
		bw.write("oilCannisters=" + oilCannisters + "\n");
		bw.write("spareWheels=" + spareWheels + "\n");

		bw.write("gold=" + getGold() + "\n");
		bw.write("provisions=" + getProvisions() + "\n");
		bw.write("provisionsValue=" + getProvisionsValue() + "\n");
		bw.write("carEnhancements=" + arrayToString(carEnhancements));
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {

		setCurrentFirepower(Integer.valueOf(getSavedGame().getProperty("currentFirepower")));
		setCurrentArmour(Integer.valueOf(getSavedGame().getProperty("currentArmour")));
		setInitialFirepower(Integer.valueOf(getSavedGame().getProperty("initialFirepower")));
		setInitialArmour(Integer.valueOf(getSavedGame().getProperty("initialArmour")));
		setRockets(Integer.valueOf(getSavedGame().getProperty("rockets")));
		setIronSpikes(Integer.valueOf(getSavedGame().getProperty("ironSpikes")));
		setOilCannisters(Integer.valueOf(getSavedGame().getProperty("oilCannisters")));
		setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
		setProvisions(Integer.valueOf(getSavedGame().getProperty("provisions")));
		setProvisionsValue(Integer.valueOf(getSavedGame().getProperty("provisionsValue")));
		setSpareWheels(Integer.valueOf(getSavedGame().getProperty("spareWheels")));
		setCarEnhancements(stringToArray(getSavedGame().getProperty("provisionsValue")));
	}

	public int getCurrentFirepower() {
		return currentFirepower;
	}

	public void setCurrentFirepower(int currentFirepower) {
		this.currentFirepower = currentFirepower;
	}

	public int getCurrentArmour() {
		return currentArmour;
	}

	public void setCurrentArmour(int currentArmour) {
		this.currentArmour = currentArmour;
	}

	public int getInitialFirepower() {
		return initialFirepower;
	}

	public void setInitialFirepower(int initialFirepower) {
		this.initialFirepower = initialFirepower;
	}

	public int getInitialArmour() {
		return initialArmour;
	}

	public void setInitialArmour(int initialArmour) {
		this.initialArmour = initialArmour;
	}

	public int getRockets() {
		return rockets;
	}

	public void setRockets(int rockets) {
		this.rockets = rockets;
	}

	public int getIronSpikes() {
		return ironSpikes;
	}

	public void setIronSpikes(int ironSpikes) {
		this.ironSpikes = ironSpikes;
	}

	public int getOilCannisters() {
		return oilCannisters;
	}

	public void setOilCannisters(int oilCannisters) {
		this.oilCannisters = oilCannisters;
	}

	public int getSpareWheels() {
		return spareWheels;
	}

	public void setSpareWheels(int spareWheels) {
		this.spareWheels = spareWheels;
	}

	public List<String> getCarEnhancements() {
		return carEnhancements;
	}

	public void setCarEnhancements(List<String> carEnhancements) {
		this.carEnhancements = carEnhancements;
	}

	public String getConsumeProvisionText() {
		return getResources().getString(R.string.useMedKit);
	}
	

	public String getProvisionsText() {
		return getResources().getString(R.string.medKits);
	}
	
	public String getCurrencyName() {
		return getResources().getString(R.string.credits);
	}

}
