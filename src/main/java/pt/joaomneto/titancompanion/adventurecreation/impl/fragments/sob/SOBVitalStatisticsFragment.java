package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

public class SOBVitalStatisticsFragment extends VitalStatisticsFragment {


    View rootView;
    TextView crewStrengthValue;
    TextView crewStrikeValue;
    public SOBVitalStatisticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_16sob_adventurecreation_vital_statistics, container, false);
        crewStrengthValue = rootView.findViewById(R.id.crewStrengthValue);
        crewStrikeValue = rootView.findViewById(R.id.crewStrikeValue);

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
