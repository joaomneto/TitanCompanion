package pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.ff;

import pt.joaomneto.ffgbutil.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FFVitalStatisticsFragment extends Fragment {
	

	public FFVitalStatisticsFragment(){
		
	}
	
	View rootView;
	TextView armorValue;
	TextView firepowerValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_13ff_adventurecreation_vital_statistics, container, false);
		armorValue = (TextView) rootView.findViewById(R.id.armorValue);
		firepowerValue = (TextView) rootView.findViewById(R.id.firepowerValue);

		return rootView;
	}

	public TextView getArmorValue() {
		return armorValue;
	}
	
	public TextView getFirepowerValue() {
		return firepowerValue;
	}
}
