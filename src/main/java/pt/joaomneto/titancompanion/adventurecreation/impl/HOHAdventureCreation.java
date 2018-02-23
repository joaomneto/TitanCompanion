package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.hoh.HOHVitalStatisticsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class HOHAdventureCreation extends AdventureCreation {

	
	private int fearValue = -1;
	
	public HOHAdventureCreation() {
		super();
		getFragmentConfiguration().clear();
		getFragmentConfiguration().put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.hoh.HOHVitalStatisticsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentFear="+fearValue+"\n");
		bw.write("maximumFear="+fearValue+"\n");
	}
	
	private HOHVitalStatisticsFragment getHOHVitalStatisticsFragment() {
		HOHVitalStatisticsFragment hohvitalStatisticsFragment = (HOHVitalStatisticsFragment) Companion.getFragments().get(0);
		return hohvitalStatisticsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		fearValue = DiceRoller.rollD6()+6;
		getHOHVitalStatisticsFragment().getFearValue().setText(""+fearValue);
		
	}

	public int getFearValue() {
		return fearValue;
	}

	public void setFearValue(int fearValue) {
		this.fearValue = fearValue;
	}

	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.fearValue < 0){
			sb.append(R.string.fear2);
		}
		return  sb.toString();
	}
	
	

}
