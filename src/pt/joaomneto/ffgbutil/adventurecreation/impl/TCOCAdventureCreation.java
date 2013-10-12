package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;

public class TCOCAdventureCreation extends AdventureCreation {

	private int spellValue = -1;
	List<String> spells = new ArrayList<String>();
	
	public TCOCAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.ffgbutil.adventurecreation.fragments.VitalStatisticsFragment"));
		fragmentConfiguration.put(1, new AdventureFragmentRunner(
				R.string.spells,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.tcoc.TCOCSpellsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {
		
		String spellsS = "";

		if (!spells.isEmpty()) {
			for (String spell : spells) {
				spellsS += spell + "#";
			}
			spellsS = spellsS.substring(0, spellsS.length() - 1);
		}

		bw.write("spellValue="+spellValue+"\n");
		bw.write("spells="+spellsS+"\n");
		bw.write("gold=0\n");
	}

}
