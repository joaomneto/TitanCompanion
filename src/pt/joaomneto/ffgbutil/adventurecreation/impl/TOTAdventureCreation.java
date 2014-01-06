package pt.joaomneto.ffgbutil.adventurecreation.impl;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;

public class TOTAdventureCreation extends TFODAdventureCreation {
	public TOTAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration
				.put(0,
						new AdventureFragmentRunner(
								R.string.title_adventure_creation_vitalstats,
								"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.VitalStatisticsFragment"));

	}
}
