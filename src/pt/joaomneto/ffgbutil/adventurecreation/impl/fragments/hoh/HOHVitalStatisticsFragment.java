package pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.hoh;

import pt.joaomneto.ffgbutil.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HOHVitalStatisticsFragment extends Fragment {
	

	public HOHVitalStatisticsFragment(){
		
	}
	
	View rootView;
	TextView fearValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_10hoh_adventurecreation_vital_statistics, container, false);
		fearValue = (TextView) rootView.findViewById(R.id.fearValue);

		return rootView;
	}

	public TextView getFearValue() {
		return fearValue;
	}
	
	

}
