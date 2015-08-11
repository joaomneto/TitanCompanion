package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.HOHAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.RCAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RCAdventureVitalStatsFragment extends AdventureVitalStatsFragment {


	public RCAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_22rc_adventure_vitalstats, container, false);

		initialize(rootView);


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
