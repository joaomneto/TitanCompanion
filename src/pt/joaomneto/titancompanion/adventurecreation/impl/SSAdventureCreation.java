package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import android.view.View;

public class SSAdventureCreation extends AdventureCreation{

	
	public SSAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));

	}
	
	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {

		bw.write("spells=\n");
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		
	}


	@Override
	public String validateCreationSpecificParameters() {
		return NO_PARAMETERS_TO_VALIDATE;
	}

}
