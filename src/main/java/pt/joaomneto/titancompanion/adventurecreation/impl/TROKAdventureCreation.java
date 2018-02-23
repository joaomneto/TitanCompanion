package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.trok.TROKVitalStatisticsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class TROKAdventureCreation extends AdventureCreation {

	
	private int currentWeapons = -1;
	private int currentShields = -1;


	public TROKAdventureCreation() {
		super();
		getFragmentConfiguration().clear();
		getFragmentConfiguration().put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.trok.TROKVitalStatisticsFragment"));
		
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentWeapons="+currentWeapons+"\n");
		bw.write("currentShields="+currentShields+"\n");
		bw.write("initialWeapons="+currentWeapons+"\n");
		bw.write("initialShields="+currentShields+"\n");
		bw.write("missiles=2\n");
		bw.write("provisions=4\n");
		bw.write("provisionsValue=6\n");
		bw.write("gold=5000\n");
	}
	
	private TROKVitalStatisticsFragment getTROKVitalStatisticsFragment() {
		TROKVitalStatisticsFragment trokVitalStatsFragment = (TROKVitalStatisticsFragment) Companion.getFragments().get(0);
		return trokVitalStatsFragment;
	}

	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.currentWeapons < 0){
			sb.append(getString(R.string.starshipStatsMandatory));
		}
		return  sb.toString();
	}


	@Override
	protected void rollGamebookSpecificStats(View view) {
		currentWeapons = DiceRoller.rollD6()+6;
		currentShields = DiceRoller.rollD6();
		getTROKVitalStatisticsFragment().getWeaponsValue().setText(""+currentWeapons);
		getTROKVitalStatisticsFragment().getShieldsValue().setText(""+currentShields);
		
	}

}
