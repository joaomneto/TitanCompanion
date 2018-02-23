package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.trok;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TROKVitalStatisticsFragment extends VitalStatisticsFragment {


    public TROKVitalStatisticsFragment() {

    }

    TextView shieldsValue;
    TextView weaponsValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_15trok_adventurecreation_vital_statistics, container, false);
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
