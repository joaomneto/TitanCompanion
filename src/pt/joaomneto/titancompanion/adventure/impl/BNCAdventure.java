package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.bnc.BNCAdventureVitalStatsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class BNCAdventure extends Adventure {

	Integer initialWillpower;
	Integer currentWillpower;


	public BNCAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
				"pt.joaomneto.titancompanion.adventure.impl.fragments.bnc.BNCAdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
				"pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
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

		bw.write("currentWillpower=" + getCurrentWillpower() + "\n");
		bw.write("initialWillpower=" + getInitialWillpower() + "\n");
		bw.write("gold=" + getGold() + "\n");
	}


	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setCurrentWillpower(Integer.valueOf(getSavedGame().getProperty("currentWillpower")));
		setInitialWillpower(Integer.valueOf(getSavedGame().getProperty("initialWillpower")));
		setGold(Integer.valueOf(getSavedGame().getProperty("gold")));

	}

	public Integer getInitialWillpower() {
		return initialWillpower;
	}

	public void setInitialWillpower(Integer initialWillpower) {
		this.initialWillpower = initialWillpower;
	}

	public Integer getCurrentWillpower() {
		return currentWillpower;
	}

	public void setCurrentWillpower(Integer currentWillpower) {
		this.currentWillpower = currentWillpower;
	}

    public void testWillpower(View v) {

        boolean result = DiceRoller.roll2D6().getSum()<=getCurrentWillpower();

        setCurrentWillpower(Math.max(0, getCurrentWillpower()-1));

        String message = result ? "Success!" : "Failed...";
        showAlert(message, this);

		((BNCAdventureVitalStatsFragment)(getFragments().get(0))).refreshScreensFromResume();
    }
}
