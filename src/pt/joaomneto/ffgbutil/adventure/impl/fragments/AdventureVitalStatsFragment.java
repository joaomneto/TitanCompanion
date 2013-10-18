package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdventureVitalStatsFragment extends DialogFragment {

	TextView skillValue = null;
	TextView staminaValue = null;
	TextView luckValue = null;

	Button increaseStaminaButton = null;
	Button increaseSkillButton = null;
	Button increaseLuckButton = null;

	Button decreaseStaminaButton = null;
	Button decreaseSkillButton = null;
	Button decreaseLuckButton = null;

	public AdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventure_vitalstats, container, false);

		skillValue = (TextView) rootView.findViewById(R.id.statsSkillValue);
		staminaValue = (TextView) rootView.findViewById(R.id.statsStaminaValue);
		luckValue = (TextView) rootView.findViewById(R.id.statsLuckValue);

		increaseStaminaButton = (Button) rootView.findViewById(R.id.plusStaminaButton);
		increaseSkillButton = (Button) rootView.findViewById(R.id.plusSkillButton);
		increaseLuckButton = (Button) rootView.findViewById(R.id.plusLuckButton);

		decreaseStaminaButton = (Button) rootView.findViewById(R.id.minusStaminaButton);
		decreaseSkillButton = (Button) rootView.findViewById(R.id.minusSkillButton);
		decreaseLuckButton = (Button) rootView.findViewById(R.id.minusLuckButton);

		final Adventure adv = (Adventure) getActivity();

		skillValue.setText("" + adv.getCurrentSkill());
		staminaValue.setText("" + adv.getCurrentStamina());
		luckValue.setText("" + adv.getCurrentLuck());
		
		
		increaseStaminaButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(adv.getCurrentStamina()<adv.getInitialStamina())
					adv.setCurrentStamina(adv.getCurrentStamina()+1);
				updateValues();
				
			}
		});
		

		increaseSkillButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(adv.getCurrentSkill()<adv.getInitialSkill())
					adv.setCurrentSkill(adv.getCurrentSkill()+1);
				updateValues();
				
			}
		});
		

		increaseLuckButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(adv.getCurrentLuck()<adv.getInitialLuck())
					adv.setCurrentLuck(adv.getCurrentLuck()+1);
				updateValues();
				
				
			}
		});
		

		decreaseStaminaButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(adv.getCurrentStamina()>0)
					adv.setCurrentStamina(adv.getCurrentStamina()-1);
				updateValues();
				
			}
		});
		

		decreaseSkillButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(adv.getCurrentSkill()>0)
					adv.setCurrentSkill(adv.getCurrentSkill()-1);
				updateValues();
				
			}
		});
		

		decreaseLuckButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(adv.getCurrentLuck()>0)
					adv.setCurrentLuck(adv.getCurrentLuck()-1);
				updateValues();
				
			}
		});
		

		return rootView;
	}

	public void updateValues() {
		Adventure adv = (Adventure) getActivity();
		skillValue.setText(adv.getCurrentSkill()+"");
		staminaValue.setText(adv.getCurrentStamina()+"");
		luckValue.setText(adv.getCurrentLuck()+"");
		
	}

}
