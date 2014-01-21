package pt.joaomneto.ffgbutil.adventure.impl.fragments.sob;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.SOBAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SOBAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

	TextView logValue = null;

	public SOBAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_16sob_adventure_vitalstats, container, false);

		initialize(rootView);
		final SOBAdventure adv = (SOBAdventure) getActivity();
		
		Runnable incTrigger = new Runnable() {
			
			public void run() {
				adv.setCurrentStamina(Math.min(adv.getInitialStamina(), adv.getCurrentStamina()+1));
			}
		};
		
		setupIncDecButton(rootView, R.id.plusLogButton, R.id.minusLogButton, adv, SOBAdventure.class, "getLog", "setLog", 50, incTrigger, null);

		logValue = (TextView) rootView.findViewById(R.id.statsLogValue);


		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		SOBAdventure adv = (SOBAdventure) getActivity();
		logValue.setText("" + adv.getLog());

	}

}
