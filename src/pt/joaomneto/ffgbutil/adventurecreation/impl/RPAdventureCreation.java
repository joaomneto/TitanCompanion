package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import android.view.View;

public class RPAdventureCreation extends AdventureCreation {


	public RPAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration
				.put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.VitalStatisticsFragment"));
	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("gold=2000\n");
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
	}

}
