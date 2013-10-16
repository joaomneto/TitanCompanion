package pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.tcoc;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TCOCSpellsFragment extends Fragment {

	public static final int SKILL_POTION = 0;
	public static final int STRENGTH_POTION = 1;
	public static final int FORTUNE_POTION = 2;
	
	public TCOCSpellsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_tcoc_adventurecreation_spells, container, false);
		

		final TextView spellScoreValue = (TextView) rootView.findViewById(R.id.spellScoreValue);
		
		Button rollSpellScoreButton = (Button) rootView.findViewById(R.id.rollSpellScoreButton);
		rollSpellScoreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				spellScoreValue.setText(""+(DiceRoller.roll2D6()+6));
				
			}
		});

		return rootView;
	}
}
