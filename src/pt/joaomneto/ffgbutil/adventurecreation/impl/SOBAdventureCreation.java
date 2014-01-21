package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sob.SOBVitalStatisticsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.view.View;

public class SOBAdventureCreation extends AdventureCreation {

	
	private int currentCrewStrength = -1;
	private int currentCrewStrike = -1;


	public SOBAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sob.SOBVitalStatisticsFragment"));
		
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentCrewStrength="+currentCrewStrength+"\n");
		bw.write("currentCrewStrike="+currentCrewStrike+"\n");
		bw.write("initialCrewStrength="+currentCrewStrength+"\n");
		bw.write("initialCrewStrike="+currentCrewStrike+"\n");
		bw.write("log=0\n");
		bw.write("gold=20\n");
		bw.write("booty=\n");
	}
	
	private SOBVitalStatisticsFragment getSOBVitalStatisticsFragment() {
		SOBVitalStatisticsFragment sobVitalStatisticsFragment = (SOBVitalStatisticsFragment) getSupportFragmentManager()
				.getFragments().get(0);
		return sobVitalStatisticsFragment;
	}
	

	@Override
	protected void rollGamebookSpecificStats(View view) {
		currentCrewStrike = DiceRoller.rollD6()+6;
		currentCrewStrength = DiceRoller.roll2D6()+6;
		getSOBVitalStatisticsFragment().getCrewStrikeValue().setText(""+currentCrewStrike);
		getSOBVitalStatisticsFragment().getCrewStrengthValue().setText(""+currentCrewStrength);
		
	}

}
