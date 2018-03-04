package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.trok;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

public class TROKVitalStatisticsFragment extends VitalStatisticsFragment {


    View rootView;
    TextView shieldsValue;
    TextView weaponsValue;
    public TROKVitalStatisticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_15trok_adventurecreation_vital_statistics, container, false);
        shieldsValue = rootView.findViewById(R.id.shieldsValue);
        weaponsValue = rootView.findViewById(R.id.weaponsValue);

        return rootView;
    }

    public TextView getShieldsValue() {
        return shieldsValue;
    }

    public TextView getWeaponsValue() {
        return weaponsValue;
    }


}
