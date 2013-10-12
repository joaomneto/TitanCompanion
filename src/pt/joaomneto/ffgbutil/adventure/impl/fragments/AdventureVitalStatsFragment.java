package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class AdventureVitalStatsFragment extends DialogFragment {

	NumberPicker skillValue = null;
	NumberPicker staminaValue = null;
	NumberPicker luckValue = null;
	

	public AdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_adventure_vitalstats, container, false);

		skillValue = (NumberPicker) rootView.findViewById(R.id.skillPicker);
		staminaValue = (NumberPicker) rootView.findViewById(R.id.staminaPicker);
		luckValue = (NumberPicker) rootView.findViewById(R.id.luckPicker);

		final Adventure adv = (Adventure) getActivity();

		skillValue.setMinValue(0);
		skillValue.setMaxValue(adv.getInitialSkill());
		skillValue.setValue(adv.getCurrentSkill());
		staminaValue.setMinValue(0);
		staminaValue.setMaxValue(adv.getInitialStamina());
		staminaValue.setValue(adv.getCurrentStamina());
		luckValue.setMinValue(0);
		luckValue.setMaxValue(adv.getCurrentLuck() + 1);
		luckValue.setValue(adv.getCurrentLuck());
		
		skillValue.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				adv.setCurrentSkill(newVal);

			}
		});
		
		staminaValue.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				adv.setCurrentStamina(newVal);

			}
		});
		
		luckValue.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				adv.setCurrentLuck(newVal);

			}
		});

		return rootView;
	}

	public void setSkillValue(Integer currentSkill) {
		skillValue.setValue(currentSkill);

	}

	public void setLuckValue(Integer currentLuck) {
		luckValue.setValue(currentLuck);
	}

	public void setStaminaValue(Integer currentStamina) {
		staminaValue.setValue(currentStamina);

	}

}
