package pt.joaomneto.titancompanion.adventure.impl.fragments.hotw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.HOTWAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class HOTWAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

    TextView changeValue = null;

    Button increaseChangeButton = null;

    Button decreaseChangeButton = null;

    public HOTWAdventureVitalStatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(
                R.layout.fragment_62hotw_adventure_vitalstats, container, false);

        //CHECKTHIS initialize(rootView);

        decreaseChangeButton = rootView
                .findViewById(R.id.minusChangeButton);
        increaseChangeButton = rootView
                .findViewById(R.id.plusChangeButton);
        changeValue = rootView.findViewById(R.id.statsChangeValue);
        final HOTWAdventure adv = (HOTWAdventure) getActivity();

        decreaseChangeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setChange(adv.getChange() - 1);
                refreshScreensFromResume();

            }
        });

        increaseChangeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setChange(adv.getChange() + 1);
                refreshScreensFromResume();

            }
        });

        refreshScreensFromResume();

        return rootView;
    }

    @Override
    public void refreshScreensFromResume() {
        super.refreshScreensFromResume();
        HOTWAdventure adv = (HOTWAdventure) getActivity();
        changeValue.setText("" + adv.getChange());

    }

}
