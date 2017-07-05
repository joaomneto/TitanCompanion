package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.strider.STRIDERVitalStatisticsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class STRIDERAdventureCreation extends BaseAdventureCreation {


	protected int fearValue = -1;

	public STRIDERAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new Adventure.AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.strider.STRIDERVitalStatisticsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("fear="+fearValue+"\n");
		bw.write("time="+0+"\n");
		bw.write("oxygen="+0+"\n");
	}

	private STRIDERVitalStatisticsFragment getSTRIDERVitalStatisticsFragment() {
		STRIDERVitalStatisticsFragment striderVitalStatisticsFragment = (STRIDERVitalStatisticsFragment) getFragments().get(0);
		return striderVitalStatisticsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		fearValue = DiceRoller.rollD6()+6;
		getSTRIDERVitalStatisticsFragment().getFearValue().setText(""+fearValue);

	}

	public int getFearValue() {
		return fearValue;
	}

	public void setFearValue(int fearValue) {
		this.fearValue = fearValue;
	}



}
