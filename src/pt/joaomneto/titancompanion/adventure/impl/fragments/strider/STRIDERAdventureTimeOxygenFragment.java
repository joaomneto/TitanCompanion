package pt.joaomneto.titancompanion.adventure.impl.fragments.strider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
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


        final STRIDERAdventure adventure = (STRIDERAdventure) this.getContext();
        STRIDERAdventure adventureF = adventure;


        oxygenValue = (TextView) rootView.findViewById(R.id.statsOxygenValue);
        increaseOxygenButton = (Button) rootView.findViewById(R.id.plusOxygenButton);
        decreaseOxygenButton = (Button) rootView.findViewById(R.id.minusOxygenButton);
        timeValue = (TextView) rootView.findViewById(R.id.statsTimeValue);
        increaseTimeButton = (Button) rootView.findViewById(R.id.plusTimeButton);
        decreaseTimeButton = (Button) rootView.findViewById(R.id.minusTimeButton);
        timeBar = (TableLayout) rootView.findViewById(R.id.strider_timeBar);
        oxygenBar = (TableLayout) rootView.findViewById(R.id.strider_oxygenBar);

        increaseOxygenButton.setOnClickListener((View v) -> {
            adventureF.increaseOxygen();
            refreshScreensFromResume();
        });

        decreaseOxygenButton.setOnClickListener((View v) -> {
            adventureF.decreaseOxygen();
            refreshScreensFromResume();
        });

        increaseTimeButton.setOnClickListener((View v) -> {
            adventureF.increaseTime();
            refreshScreensFromResume();
        });

        decreaseTimeButton.setOnClickListener((View v) -> {
            adventureF.decreaseTime();
            refreshScreensFromResume();
        });

        refreshScreensFromResume();

		return rootView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {


        if (adventure!=null) {
            oxygenValue.setText(""+adventure.getOxygen());
            timeValue.setText(""+adventure.getTime());

            paintBar(timeBar, adventure.getTime());
            paintBar(oxygenBar, adventure.getOxygen());
        }

    }

    private void paintBar(TableLayout table, Integer time) {
        for(int i = 0 ; i < table.getChildCount(); i++) {
            View view = table.getChildAt(i);
            if (view instanceof TableRow) {

                TableRow row = (TableRow) view;
                View cell = ((TableRow) view).getChildAt(0);


                if(i<time){
                    cell.setBackgroundColor(Color.parseColor("#4fa5d5"));
                }else{
                    cell.setBackgroundColor(0x00000000);
                }
            }
        }
    }


}
