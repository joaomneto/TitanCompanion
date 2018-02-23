package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;

public abstract class BaseAdventureCreation extends AdventureCreation {




	public BaseAdventureCreation() {
		super();
		Companion.getFragmentConfiguration().clear();
		Companion.getFragmentConfiguration()
				.put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("gold=0\n");
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
	}


	@Override
	public String validateCreationSpecificParameters() {
		return Companion.getNO_PARAMETERS_TO_VALIDATE();
	}

}
