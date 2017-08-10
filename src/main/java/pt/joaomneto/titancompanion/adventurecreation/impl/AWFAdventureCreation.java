package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import android.view.View;

public class AWFAdventureCreation extends AdventureCreation {

	String superPower;

	public AWFAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration
				.put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
		fragmentConfiguration
				.put(1, new AdventureFragmentRunner(R.string.title_adventure_creation_superpower, "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.awf.AWFAdventureCreationSuperpowerFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("heroPoints=0\n");
		bw.write("superPower=\n");
	}

	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.superPower == null || this.superPower.length()==0){
			sb.append(getString(R.string.aodSuperpower));
		}
		return  sb.toString();
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
	}

	public String getSuperPower() {
		return superPower;
	}

	public void setSuperPower(String superPower) {
		this.superPower = superPower;
	}

}
