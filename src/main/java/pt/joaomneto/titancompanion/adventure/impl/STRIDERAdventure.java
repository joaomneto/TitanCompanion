package pt.joaomneto.titancompanion.adventure.impl;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.strider.STRIDERAdventureVitalStatsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class STRIDERAdventure extends Adventure {

	private final static int FRAGMENT_VITAL_TIME_OXYGEN = 1;
	private final static int FRAGMENT_COMBAT = 2;
	private final static int FRAGMENT_EQUIPMENT = 3;
	private final static int FRAGMENT_NOTES = 4;
	Integer currentFear;
	Integer time;
	Integer oxygen;


	public STRIDERAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
				"pt.joaomneto.titancompanion.adventure.impl.fragments.strider.STRIDERAdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_VITAL_TIME_OXYGEN, new AdventureFragmentRunner(R.string.timeAndOxygen,
				"pt.joaomneto.titancompanion.adventure.impl.fragments.strider.STRIDERAdventureTimeOxygenFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
				"pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.equipment2,
				"pt.joaomneto.titancompanion.adventure.impl.fragments.strider.STRIDERAdventureEquipmentFragment"));
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

		bw.write("fear=" + currentFear + "\n");
		bw.write("time=" + time + "\n");
		bw.write("oxygen=" + oxygen + "\n");
	}

	public Integer getCurrentFear() {
		return currentFear;
	}

	public void setCurrentFear(Integer currentFear) {
		this.currentFear = currentFear;
	}


	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setCurrentFear(Integer.valueOf(getSavedGame().getProperty("fear")));
		setTime(Integer.valueOf(getSavedGame().getProperty("time")));
		setOxygen(Integer.valueOf(getSavedGame().getProperty("oxygen")));
	}

    public void testFear(View v) {

        boolean result = DiceRoller.roll2D6().getSum()<=getCurrentFear();

        int message = result ? R.string.success : R.string.failed;
        showAlert(message, this);

        ((STRIDERAdventureVitalStatsFragment)(getFragments().get(0))).refreshScreensFromResume();
    }

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public void increaseTime() {
		this.time = Math.min(time+1, 48);
	}

	public void decreaseTime() {
		this.time = Math.max(time-1, 0);
	}

	public Integer getOxygen() {
		return oxygen;
	}

	public void setOxygen(Integer oxygen) {
		this.oxygen = oxygen;
	}

	public void increaseOxygen() {
		this.oxygen = Math.min(oxygen+1, 20);
	}

	public void decreaseOxygen() {
		this.oxygen = Math.max(oxygen-1, 0);
	}


}
