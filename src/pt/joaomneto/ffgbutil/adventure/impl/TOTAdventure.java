package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.SpellAdventure;
import android.os.Bundle;
import android.view.Menu;

public class TOTAdventure extends SpellAdventure {

	List<String> spells = new ArrayList<String>();;

	protected static final int FRAGMENT_SPELLS = 2;

	public TOTAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_SPELLS, new AdventureFragmentRunner(R.string.spells,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.tot.TOTAdventureSpellsFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes,
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
	public void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {

		bw.write("spells=" + arrayToString(spells) + "\n");
		bw.write("gold=" + getGold() + "\n");
	}

	public List<String> getSpells() {
		return spells;
	}

	public void setSpells(List<String> spells) {
		this.spells = spells;
	}


	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setGold(Integer.valueOf(getSavedGame().getProperty("gold")));

		setSpells(stringToArray(new String(getSavedGame().getProperty("spells").getBytes(
				java.nio.charset.Charset.forName("ISO-8859-1")))));

	}
}
