package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;

public class AODAdventureCreation extends AdventureCreation {


	public AODAdventureCreation() {
		super();
		getFragmentConfiguration().clear();
		getFragmentConfiguration()
				.put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("gold=700\n");
		bw.write("soldiers=Warriors§100#Dwarves§50#Elves§50#Knights§20\n");
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
	}


	@Override
	public String validateCreationSpecificParameters() {
		return Companion.getNO_PARAMETERS_TO_VALIDATE();
	}



}
