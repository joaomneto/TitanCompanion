package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.hoh.HOHVitalStatisticsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.view.View;

public class HOHAdventureCreation extends AdventureCreation {

	
	private int fearValue = -1;
	
	public HOHAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.hoh.HOHVitalStatisticsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentFear="+fearValue+"\n");
		bw.write("maximumFear="+fearValue+"\n");
	}
	
	private HOHVitalStatisticsFragment getHOHVitalStatisticsFragment() {
		HOHVitalStatisticsFragment hohvitalStatisticsFragment = (HOHVitalStatisticsFragment) getFragments().get(0);
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
			sb.append("Fear");
		}
		return  sb.toString();
	}
	
	

}
