package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

public class SAVitalStatisticsFragment extends VitalStatisticsFragment {


    View rootView;
    TextView armorValue;
    public SAVitalStatisticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_12sa_adventurecreation_vital_statistics, container, false);
        armorValue = rootView.findViewById(R.id.armorValue);

        return rootView;
    }

    public TextView getArmorValue() {
        return armorValue;
    }


}
