package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import android.os.Bundle;
import android.view.Menu;

public class SAAdventure extends Adventure {

	Integer currentArmor;
	
	static final Integer FRAGMENT_EQUIPMENT = 2;
	static final Integer FRAGMENT_NOTES = 3;

	public SAAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration
				.put(FRAGMENT_VITAL_STATS,
						new AdventureFragmentRunner(R.string.vitalStats,
								"pt.joaomneto.ffgbutil.adventure.impl.fragments.hoh.HOHAdventureVitalStatsFragment"));
		fragmentConfiguration
				.put(FRAGMENT_COMBAT,
						new AdventureFragmentRunner(R.string.fights,
								"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment"));
		fragmentConfiguration
				.put(FRAGMENT_EQUIPMENT,
						new AdventureFragmentRunner(R.string.goldEquipment,
								"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment"));
		fragmentConfiguration
				.put(FRAGMENT_NOTES,
						new AdventureFragmentRunner(R.string.notes,
								"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureNotesFragment"));
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
	public void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentArmor=" + currentArmor + "\n");
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setCurrentArmor(Integer.valueOf(getSavedGame().getProperty("currentArmor")));

	}

	public Integer getCurrentArmor() {
		return currentArmor;
	}

	public void setCurrentArmor(Integer currentArmor) {
		this.currentArmor = currentArmor;
	}
	
	

}
