package pt.joaomneto.ffgbutil.adventurecreation.fragments;

import pt.joaomneto.ffgbutil.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VitalStatisticsFragment extends Fragment {
	
	
	public VitalStatisticsFragment(){
		
	}
	
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_adventure_vital_statistics, container, false);

		return rootView;
	}

}
