package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import android.os.Bundle;
import android.view.Menu;

public class TCOCAdventure extends Adventure {

	List<String> spells;
	Integer spellValue;

	protected static final int FRAGMENT_SPELLS = 2;

	public TCOCAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS,
				new AdventureFragmentRunner(R.string.vitalStats,
						"AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(
				R.string.fights, "AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_SPELLS, new AdventureFragmentRunner(
				R.string.spells, "TCOCAdventureSpellsFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT,
				new AdventureFragmentRunner(R.string.goldEquipment,
						"AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(
				R.string.notes, "AdventureNotesFragment"));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tcoc_adventure);

			setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
			spellValue = Integer.valueOf(getSavedGame().getProperty("spellValue"));
			String spellsS = new String(getSavedGame().getProperty("spells")
					.getBytes(java.nio.charset.Charset.forName("ISO-8859-1")));

			if (spellsS != null) {
				spells = new ArrayList<String>();
				List<String> list = Arrays.asList(spellsS.split("#"));
				for (String string : list) {
					if (!string.isEmpty())
						spells.add(string);
				}
			}

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
		
		String spellsS = "";

		if (!spells.isEmpty()) {
			for (String spell : spells) {
				spellsS += spell + "#";
			}
			spellsS = spellsS.substring(0, spellsS.length() - 1);
		}
		
		bw.write("spells=" + spellsS + "\n");
		bw.write("spellValue=" + spellValue + "\n");
		bw.write("gold=" + getGold() + "\n");
	}

	public List<String> getSpells() {
		return spells;
	}

	public void setSpells(List<String> spells) {
		this.spells = spells;
	}

	public Integer getSpellValue() {
		return spellValue;
	}

	public void setSpellValue(Integer spellValue) {
		this.spellValue = spellValue;
	}

}
