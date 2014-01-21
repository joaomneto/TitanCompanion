package pt.joaomneto.ffgbutil.adventure.impl.fragments.awf;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.AWFAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AWFAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

	TextView heroPoints = null;

	public AWFAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_17awf_adventure_vitalstats, container, false);

		initialize(rootView);

	
		heroPoints = (TextView) rootView.findViewById(R.id.statsHeroPointsValue);
		final AWFAdventure adv = (AWFAdventure) getActivity();

		setupIncDecButton(rootView, R.id.plusHeroPointsButton, R.id.minusHeroPointsButton, adv, AWFAdventure.class, "getHeroPoints", "setHeroPoints", 999);

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		AWFAdventure adv = (AWFAdventure) getActivity();
		heroPoints.setText("" + adv.getHeroPoints());

	}

}
