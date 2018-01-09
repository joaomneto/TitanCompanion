package pt.joaomneto.titancompanion.adventurecreation.impl;

import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;

import android.view.WindowManager;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;

public abstract class BaseAdventureCreation extends AdventureCreation {




	public BaseAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration
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
		return NO_PARAMETERS_TO_VALIDATE;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		adjustFontScale(new Configuration(newConfig));
	}


	public void adjustFontScale(Configuration configuration) {
		if (configuration != null && configuration.fontScale != 1.0) {
			configuration.fontScale = (float) 1.0;
			DisplayMetrics metrics = getResources().getDisplayMetrics();
			WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(metrics);
			metrics.scaledDensity = configuration.fontScale * metrics.density;
			getBaseContext().getResources().updateConfiguration(configuration, metrics);
		}
	}

}
