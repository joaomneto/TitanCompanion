package pt.joaomneto.titancompanion.adventure.impl.fragments.sots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.SOTSAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class SOTSAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

    TextView honorValue = null;


    Button increaseHonorButton = null;

    Button decreaseHonorButton = null;


    public SOTSAdventureVitalStatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(
                R.layout.fragment_20sots_adventure_vitalstats, container, false);

        initialize(rootView);

        return rootView;
    }

    protected void initialize(View rootView) {
        //CHECKTHIS super.initialize(rootView);
        honorValue = rootView.findViewById(R.id.statsHonourValue);


        increaseHonorButton = rootView
                .findViewById(R.id.plusHonourButton);

        decreaseHonorButton = rootView
                .findViewById(R.id.minusHonourButton);

        final SOTSAdventure adv = (SOTSAdventure) getActivity();


        increaseHonorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCurrentHonour(adv.getCurrentHonour() + 1);
                refreshScreensFromResume();

            }
        });


        decreaseHonorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentHonour() > 0)
                    adv.setCurrentHonour(adv.getCurrentHonour() - 1);
                refreshScreensFromResume();

            }
        });

    }


    @Override
    public void refreshScreensFromResume() {

        super.refreshScreensFromResume();
        SOTSAdventure adv = (SOTSAdventure) getActivity();

        honorValue.setText("" + adv.getCurrentHonour());

    }


}
