package pt.joaomneto.ffgbutil.adventure.impl.fragments.st;

import pt.joaomneto.ffgbutil.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class STCrewStatsFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_04st_adventure_crewvitalstats, container, false);
		
		return rootView;
	}

}
