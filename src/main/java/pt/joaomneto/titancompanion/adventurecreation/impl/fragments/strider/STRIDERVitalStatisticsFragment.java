package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.strider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;

public class STRIDERVitalStatisticsFragment extends Fragment {


	public STRIDERVitalStatisticsFragment(){
		
	}
	
	View rootView;
	TextView fearValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_27strider_adventurecreation_vital_statistics, container, false);
		fearValue = (TextView) rootView.findViewById(R.id.fearValue);

		return rootView;
	}

	public TextView getFearValue() {
		return fearValue;
	}
	
	

}
