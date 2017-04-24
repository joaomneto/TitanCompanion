package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sob;

import pt.joaomneto.titancompanion.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SOBVitalStatisticsFragment extends Fragment {
	

	public SOBVitalStatisticsFragment(){
		
	}
	
	View rootView;
	TextView crewStrengthValue;
	TextView crewStrikeValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_16sob_adventurecreation_vital_statistics, container, false);
		crewStrengthValue = (TextView) rootView.findViewById(R.id.crewStrengthValue);
		crewStrikeValue = (TextView) rootView.findViewById(R.id.crewStrikeValue);

		return rootView;
	}

	public View getRootView() {
		return rootView;
	}

	public TextView getCrewStrengthValue() {
		return crewStrengthValue;
	}

	public TextView getCrewStrikeValue() {
		return crewStrikeValue;
	}



	
}
