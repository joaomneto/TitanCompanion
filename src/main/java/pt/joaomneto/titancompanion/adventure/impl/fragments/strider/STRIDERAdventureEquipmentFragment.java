package pt.joaomneto.titancompanion.adventure.impl.fragments.strider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment;

/**
 * Created by Joao Neto on 30-05-2017.
 */

public class STRIDERAdventureEquipmentFragment extends AdventureEquipmentFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.findViewById(R.id.goldLabel).setVisibility(View.GONE);
        view.findViewById(R.id.minusGoldButton).setVisibility(View.GONE);
        view.findViewById(R.id.plusGoldButton).setVisibility(View.GONE);
        view.findViewById(R.id.goldValue).setVisibility(View.GONE);

        return view;
    }
}
