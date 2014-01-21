package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import android.view.View;

public class AWFAdventureCreation extends AdventureCreation {

	String superPower;

	public AWFAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration
				.put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.VitalStatisticsFragment"));
		fragmentConfiguration
				.put(1, new AdventureFragmentRunner(R.string.title_adventure_creation_superpower, "pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.awf.AWFAdventureCreationSuperpowerFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("heroPoints=0\n");
		bw.write("superPower=\n");
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
