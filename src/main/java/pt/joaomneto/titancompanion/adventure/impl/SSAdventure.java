package pt.joaomneto.titancompanion.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.SpellAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.ss.SSSpell;
import pt.joaomneto.titancompanion.adventure.impl.fragments.tot.TOTSpell;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

import android.os.Bundle;
import android.view.Menu;

public class SSAdventure extends SpellAdventure {

	Set<String> visitedClearings = new HashSet<String>();

	protected static final int FRAGMENT_SPELLS = 2;
	protected static final int FRAGMENT_EQUIPMENT = 3;
	protected static final int FRAGMENT_MAP = 4;
	protected static final int FRAGMENT_NOTES = 5;



	public SSAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_SPELLS, new AdventureFragmentRunner(R.string.spells, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_MAP, new AdventureFragmentRunner(R.string.map, "pt.joaomneto.titancompanion.adventure.impl.fragments.ss.SSAdventureMapFragment"));
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

		bw.write("spells=" + arrayToStringSpells(getSpells()) + "\n");
		bw.write("clearings=" + arrayToString(visitedClearings) + "\n");
		bw.write("gold=" + getGold() + "\n");
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setGold(Integer.valueOf(getSavedGame().getProperty("gold")));

		setSpells(stringToArraySpells(new String(getSavedGame().getProperty("spells").getBytes(java.nio.charset.Charset.forName("ISO-8859-1")))));

		setVisitedClearings(stringToSet(new String(getSavedGame().getProperty("clearings").getBytes(java.nio.charset.Charset.forName("ISO-8859-1")))));
	}

	public void addVisitedClearings(String clearing) {
		visitedClearings.add(clearing);
	}

	public Set<String> getVisitedClearings() {
		return visitedClearings;
	}

	public synchronized void setVisitedClearings(Set<String> visitedClearings) {
		this.visitedClearings = visitedClearings;
	}

	public List<Spell> getSpellList() {
		return Arrays.asList(SSSpell.values());
	}

    public boolean isSpellSingleUse() {
        return true;
    }

}
