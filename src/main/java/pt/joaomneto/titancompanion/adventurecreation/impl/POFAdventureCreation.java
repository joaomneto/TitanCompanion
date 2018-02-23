package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.pof.POFVitalStatisticsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class POFAdventureCreation extends TFODAdventureCreation {


	private int powerValue = -1;

	public POFAdventureCreation() {
		super();
		Companion.getFragmentConfiguration().clear();
		Companion.getFragmentConfiguration().put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.pof.POFVitalStatisticsFragment"));
		Companion.getFragmentConfiguration().put(1, new AdventureFragmentRunner(R.string.title_adventure_creation_potions,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.PotionsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {
		bw.write("standardPotion=" + potion + "\n");
		bw.write("standardPotionValue=1\n");
		bw.write("provisions=0\n");
		bw.write("provisionsValue=0\n");
		bw.write("gold=0\n");
		bw.write("currentPower="+ powerValue +"\n");
		bw.write("initialPower="+ powerValue +"\n");
	}
	
	private POFVitalStatisticsFragment getPOFVitalStatisticsFragment() {
		POFVitalStatisticsFragment bncVitalStatisticsFragment = (POFVitalStatisticsFragment) Companion.getFragments().get(0);
		return bncVitalStatisticsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		powerValue = DiceRoller.rollD6()+6;
		getPOFVitalStatisticsFragment().getPowerValue().setText(""+ powerValue);
		
	}


	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.potion < 0){
			sb.append(getString(R.string.potion));
		}
		return  sb.toString();
	}
	
	

}
