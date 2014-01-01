package pt.joaomneto.ffgbutil.adventure.impl.fragments.sa;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.SAAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SAAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

	TextView armorValue = null;

	Button increasearmorButton = null;

	Button decreasearmorButton = null;

	public SAAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_12sa_adventure_vitalstats, container, false);

		initialize(rootView);

		decreasearmorButton = (Button) rootView
				.findViewById(R.id.minusarmorButton);
		increasearmorButton = (Button) rootView
				.findViewById(R.id.plusarmorButton);
		armorValue = (TextView) rootView.findViewById(R.id.statsarmorValue);
		final SAAdventure adv = (SAAdventure) getActivity();

		decreasearmorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentArmor() > 0)
					adv.setCurrentArmor(adv.getCurrentArmor() - 1);
				refreshScreensFromResume();

			}
		});

		increasearmorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setCurrentArmor(adv.getCurrentArmor() + 1);
				refreshScreensFromResume();

			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		SAAdventure adv = (SAAdventure) getActivity();
		armorValue.setText("" + adv.getCurrentArmor());

	}

}
