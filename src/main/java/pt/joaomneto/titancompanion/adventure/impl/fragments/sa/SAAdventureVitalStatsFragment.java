package pt.joaomneto.titancompanion.adventure.impl.fragments.sa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.SAAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class SAAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

    TextView armorValue = null;

    Button increasearmorButton = null;

    Button decreasearmorButton = null;

    public SAAdventureVitalStatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(
                R.layout.fragment_12sa_adventure_vitalstats, container, false);

        //CHECKTHIS	initialize(rootView);

        decreasearmorButton = rootView
                .findViewById(R.id.minusarmorButton);
        increasearmorButton = rootView
                .findViewById(R.id.plusarmorButton);
        armorValue = rootView.findViewById(R.id.statsarmorValue);
        final SAAdventure adv = (SAAdventure) getActivity();

        decreasearmorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentArmor() > 0)
                    adv.setCurrentArmor(adv.getCurrentArmor() - 1);
                refreshScreensFromResume();

            }
        });

        increasearmorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCurrentArmor(adv.getCurrentArmor() + 1);
                refreshScreensFromResume();

            }
        });


        refreshScreensFromResume();

        return rootView;
    }

    @Override
    public void refreshScreensFromResume() {
        super.refreshScreensFromResume();
        SAAdventure adv = (SAAdventure) getActivity();
        armorValue.setText("" + adv.getCurrentArmor());

    }

}
