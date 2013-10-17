package pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.st;

import pt.joaomneto.ffgbutil.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class STCrewAndShipVitalStatisticsFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_04st_adventurecreation_crewshipvitalstats, container, false);

		return rootView;
	}

}
