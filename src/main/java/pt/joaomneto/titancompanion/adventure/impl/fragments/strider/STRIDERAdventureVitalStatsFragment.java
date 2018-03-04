package pt.joaomneto.titancompanion.adventure.impl.fragments.strider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventure.impl.HOHAdventure;
import pt.joaomneto.titancompanion.adventure.impl.STRIDERAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class STRIDERAdventureVitalStatsFragment extends AdventureVitalStatsFragment {
//TODO
	TextView fearValue = null;

	Button increaseFearButton = null;

	Button decreaseFearButton = null;

	public STRIDERAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_27strider_adventure_vitalstats, container, false);

		//CHECKTHIS	initialize(rootView);


		fearValue = rootView.findViewById(R.id.statsFearValue);

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		STRIDERAdventure adv = (STRIDERAdventure) getActivity();
		fearValue.setText("" + adv.getCurrentFear());

	}

}
