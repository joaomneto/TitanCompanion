package pt.joaomneto.titancompanion.adventurecreation.impl.fragments;

import pt.joaomneto.titancompanion.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;

public class VitalStatisticsFragment extends Fragment {
	

	public VitalStatisticsFragment(){
		
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventurecreation_vital_statistics, container, false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.buttonRollStats).setOnClickListener((View v) -> {
			((AdventureCreation) this.getActivity()).rollStats(v);
		});

		view.findViewById(R.id.buttonSaveAdventure).setOnClickListener((View v) -> {
			((AdventureCreation) this.getActivity()).saveAdventure(v);
		});
	}
}
