package pt.joaomneto.titancompanion.adventure.impl.fragments.sob;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.SOBAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SOBAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

	TextView logValue = null;

	public SOBAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_16sob_adventure_vitalstats, container, false);

		initialize(rootView);
		final SOBAdventure adv = (SOBAdventure) getActivity();

		Runnable incTrigger = new Runnable() {

			public void run() {
				adv.setCurrentStamina(Math.min(adv.getInitialStamina(), adv.getCurrentStamina() + 1));
			}
		};

		setupIncDecButton(rootView, R.id.plusLogButton, R.id.minusLogButton, adv, SOBAdventure.class, "getLog", "setLog", 50, incTrigger, null);

		logValue = (TextView) rootView.findViewById(R.id.statsLogValue);

		Button testCrewStrengthButton = (Button) rootView.findViewById(R.id.testCrewStrengthButton);

		testCrewStrengthButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				boolean result = DiceRoller.roll3D6() < adv.getCurrentCrewStrength();

				int message = result ? R.string.success : R.string.failed;
				Adventure.showAlert(message, adv);
			}
		});

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
