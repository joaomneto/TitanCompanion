package pt.joaomneto.titancompanion.adventurecreation.impl;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;

public class TOTAdventureCreation extends TFODAdventureCreation {
	public TOTAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration
				.put(0,
						new AdventureFragmentRunner(
								R.string.title_adventure_creation_vitalstats,
								"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));

	}
}
