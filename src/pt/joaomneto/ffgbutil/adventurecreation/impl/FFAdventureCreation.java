package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.ff.FFVitalStatisticsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.view.View;

public class FFAdventureCreation extends AdventureCreation {

	
	private int currentFirepower = -1;
	private int currentArmour = -1;


	public FFAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.ff.FFVitalStatisticsFragment"));
		
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("currentFirepower="+currentFirepower+"\n");
		bw.write("currentArmour="+currentArmour+"\n");
		bw.write("initialFirepower="+currentFirepower+"\n");
		bw.write("initialArmour="+currentArmour+"\n");
		bw.write("rockets=4\n");
		bw.write("ironSpikes=3\n");
		bw.write("oilCannisters=2\n");
		bw.write("provisions=10\n");
		bw.write("provisionsValue=4\n");
		bw.write("spareWheels=2\n");
		bw.write("gold=200\n");
		bw.write("carEnhancements=\n");
	}

	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.currentFirepower < 0){
			sb.append("Firepower and Armor");
		}
		return  sb.toString();
	}

	private FFVitalStatisticsFragment getFFVitalStatsFragment() {
		FFVitalStatisticsFragment ffVitalStatsFragment = (FFVitalStatisticsFragment) getFragments().get(0);
		return ffVitalStatsFragment;
	}
	

	@Override
	protected void rollGamebookSpecificStats(View view) {
		currentFirepower = DiceRoller.rollD6()+6;
		currentArmour = DiceRoller.roll2D6().getSum()+24;
		stamina = DiceRoller.roll2D6().getSum()+24;
		getFFVitalStatsFragment().getFirepowerValue().setText(""+currentFirepower);
		getFFVitalStatsFragment().getArmorValue().setText(""+currentArmour);
		
	}

}
