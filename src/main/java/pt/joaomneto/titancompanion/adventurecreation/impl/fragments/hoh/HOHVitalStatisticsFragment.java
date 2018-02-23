package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.hoh;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HOHVitalStatisticsFragment extends VitalStatisticsFragment {


    public HOHVitalStatisticsFragment() {

    }

    TextView fearValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_10hoh_adventurecreation_vital_statistics, container, false);
        fearValue = rootView.findViewById(R.id.fearValue);

        return rootView;
    }

    public TextView getFearValue() {
        return fearValue;
    }


}
