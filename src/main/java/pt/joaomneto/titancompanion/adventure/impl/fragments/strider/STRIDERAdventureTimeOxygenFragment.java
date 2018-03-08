package pt.joaomneto.titancompanion.adventure.impl.fragments.strider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.STRIDERAdventure;

public class STRIDERAdventureTimeOxygenFragment extends AdventureFragment {

    TextView oxygenValue = null;
    Button increaseOxygenButton = null;
    Button decreaseOxygenButton = null;
    TextView timeValue = null;
    Button increaseTimeButton = null;
    Button decreaseTimeButton = null;
    TableLayout timeBar = null;
    TableLayout oxygenBar = null;

    STRIDERAdventure adventure = null;


    public STRIDERAdventureTimeOxygenFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_27strider_adventure_time_oxygen,
                container, false);


        adventure = (STRIDERAdventure) this.getContext();


        oxygenValue = rootView.findViewById(R.id.statsOxygenValue);
        increaseOxygenButton = rootView.findViewById(R.id.plusOxygenButton);
        decreaseOxygenButton = rootView.findViewById(R.id.minusOxygenButton);
        timeValue = rootView.findViewById(R.id.statsTimeValue);
        increaseTimeButton = rootView.findViewById(R.id.plusTimeButton);
        decreaseTimeButton = rootView.findViewById(R.id.minusTimeButton);
        timeBar = rootView.findViewById(R.id.strider_timeBar);
        oxygenBar = rootView.findViewById(R.id.strider_oxygenBar);

        increaseOxygenButton.setOnClickListener((View v) -> {
            adventure.increaseOxygen();
            refreshScreensFromResume();
        });

        decreaseOxygenButton.setOnClickListener((View v) -> {
            adventure.decreaseOxygen();
            refreshScreensFromResume();
        });

        increaseTimeButton.setOnClickListener((View v) -> {
            adventure.increaseTime();
            refreshScreensFromResume();
        });

        decreaseTimeButton.setOnClickListener((View v) -> {
            adventure.decreaseTime();
            refreshScreensFromResume();
        });

        refreshScreensFromResume();

        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void refreshScreensFromResume() {


        adventure = (STRIDERAdventure) this.getContext();

        if (adventure != null) {
            oxygenValue.setText("" + adventure.getOxygen());
            timeValue.setText("" + adventure.getTime());

            paintBar(timeBar, adventure.getTime());
            paintBar(oxygenBar, adventure.getOxygen());
        }

    }

    private void paintBar(TableLayout table, Integer time) {
        for (int i = 0; i < table.getChildCount(); i++) {
            View view = table.getChildAt(i);
            if (view instanceof TableRow) {

                TableRow row = (TableRow) view;
                View cell = ((TableRow) view).getChildAt(0);


                if (i < time) {
                    if (((double) time / (double) table.getChildCount()) < 0.85d) {
                        cell.setBackgroundColor(Color.parseColor("#4fa5d5"));
                    } else {
                        cell.setBackgroundColor(Color.parseColor("#ed1c00"));
                    }
                } else {
                    cell.setBackgroundColor(0x00000000);
                }
            }
        }
    }


}
