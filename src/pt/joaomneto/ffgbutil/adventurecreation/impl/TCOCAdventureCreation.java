package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.tcoc.TCOCAdventureCreationSpellsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.view.View;

public class TCOCAdventureCreation extends AdventureCreation {

	private final static int FRAGMENT_TCOC_SPELLS = 1;
	
	private int spellValue = -1;
	List<String> spells = new ArrayList<String>();
	
	public TCOCAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.VitalStatisticsFragment"));
		fragmentConfiguration.put(1, new AdventureFragmentRunner(
				R.string.spells,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.tcoc.TCOCAdventureCreationSpellsFragment"));

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
	
	private TCOCAdventureCreationSpellsFragment getTCOCSpellsFragment() {
		TCOCAdventureCreationSpellsFragment tcocSpellsFragment = (TCOCAdventureCreationSpellsFragment) getSupportFragmentManager()
				.getFragments().get(FRAGMENT_TCOC_SPELLS);
		return tcocSpellsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		spellValue = DiceRoller.roll2D6().getSum()+6;
		getTCOCSpellsFragment().getSpellScoreValue().setText(""+spellValue);
		
	}

	public synchronized int getSpellValue() {
		return spellValue;
	}

	public synchronized List<String> getSpells() {
		return spells;
	}
	
	

}
