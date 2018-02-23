package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sob;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SOBVitalStatisticsFragment extends VitalStatisticsFragment {


    public SOBVitalStatisticsFragment() {
    }

    TextView crewStrengthValue;
    TextView crewStrikeValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_16sob_adventurecreation_vital_statistics, container, false);
        crewStrengthValue = rootView.findViewById(R.id.crewStrengthValue);
        crewStrikeValue = rootView.findViewById(R.id.crewStrikeValue);

        return rootView;
    }

    public TextView getCrewStrengthValue() {
        return crewStrengthValue;
    }

    public TextView getCrewStrikeValue() {
        return crewStrikeValue;
    }


}
