package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.hoh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

public class HOHVitalStatisticsFragment extends VitalStatisticsFragment {


    View rootView;
    TextView fearValue;
    public HOHVitalStatisticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_10hoh_adventurecreation_vital_statistics, container, false);
        fearValue = rootView.findViewById(R.id.fearValue);

        return rootView;
    }

    public TextView getFearValue() {
        return fearValue;
    }


}
