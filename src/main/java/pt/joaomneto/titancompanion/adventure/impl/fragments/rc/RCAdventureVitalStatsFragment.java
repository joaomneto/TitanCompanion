package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventure.impl.RCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RCAdventureVitalStatsFragment extends AdventureVitalStatsFragment {


	public RCAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_22rc_adventure_vitalstats, container, false);

		//CHECKTHIS	initialize(rootView);


		final RCAdventure adv = (RCAdventure) getActivity();

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		RCAdventure adv = (RCAdventure) getActivity();

	}

}
