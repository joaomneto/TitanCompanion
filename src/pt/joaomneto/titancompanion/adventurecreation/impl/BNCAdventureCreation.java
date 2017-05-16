package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.bnc.BNCVitalStatisticsFragment;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.hoh.HOHVitalStatisticsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class BNCAdventureCreation extends AdventureCreation {


	private int willpowerValue = -1;

	public BNCAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.bnc.BNCVitalStatisticsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentWillpower="+willpowerValue+"\n");
		bw.write("initialWillpower="+willpowerValue+"\n");
		bw.write("provisions=0\n");
		bw.write("provisionsValue=4\n");
		bw.write("gold=0\n");
	}
	
	private BNCVitalStatisticsFragment getBNCVitalStatisticsFragment() {
		BNCVitalStatisticsFragment bncVitalStatisticsFragment = (BNCVitalStatisticsFragment) getFragments().get(0);
		return bncVitalStatisticsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		willpowerValue = DiceRoller.rollD6()+6;
		getBNCVitalStatisticsFragment().getWillpowerValue().setText(""+willpowerValue);
		
	}


	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.willpowerValue < 0){
			sb.append(getString(R.string.bncWillpower));
		}
		return  sb.toString();
	}
	
	

}
