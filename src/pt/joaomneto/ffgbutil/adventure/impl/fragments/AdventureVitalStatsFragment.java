package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdventureVitalStatsFragment extends AdventureFragment {

	TextView skillValue = null;
	TextView staminaValue = null;
	TextView luckValue = null;
	TextView provisionsValue = null;
	TextView provisionsText = null;
	

	Button increaseStaminaButton = null;
	Button increaseSkillButton = null;
	Button increaseLuckButton = null;
	Button increaseProvisionsButton = null;

	Button decreaseStaminaButton = null;
	Button decreaseSkillButton = null;
	Button decreaseLuckButton = null;
	Button decreaseProvisionsButton = null;
	
	Button buttonConsumeProvisions = null;
	

	public AdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_adventure_vitalstats, container, false);

		initialize(rootView);

		return rootView;
	}

	protected void initialize(View rootView) {
		skillValue = (TextView) rootView.findViewById(R.id.statsSkillValue);
		staminaValue = (TextView) rootView.findViewById(R.id.statsStaminaValue);
		luckValue = (TextView) rootView.findViewById(R.id.statsLuckValue);
		provisionsValue = (TextView) rootView.findViewById(R.id.provisionsValue);
		provisionsText = (TextView) rootView.findViewById(R.id.provisionsText);
		Button buttonConsumeProvisions = (Button) rootView.findViewById(R.id.buttonConsumeProvisions);
		

		increaseStaminaButton = (Button) rootView
				.findViewById(R.id.plusStaminaButton);
		increaseSkillButton = (Button) rootView
				.findViewById(R.id.plusSkillButton);
		increaseLuckButton = (Button) rootView
				.findViewById(R.id.plusLuckButton);
		increaseProvisionsButton = (Button) rootView
				.findViewById(R.id.plusProvisionsButton);

		decreaseStaminaButton = (Button) rootView
				.findViewById(R.id.minusStaminaButton);
		decreaseSkillButton = (Button) rootView
				.findViewById(R.id.minusSkillButton);
		decreaseLuckButton = (Button) rootView
				.findViewById(R.id.minusLuckButton);
		decreaseProvisionsButton = (Button) rootView
				.findViewById(R.id.minusProvisionsButton);
		final Adventure adv = (Adventure) getActivity();


		increaseStaminaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentStamina() < adv.getInitialStamina())
					adv.setCurrentStamina(adv.getCurrentStamina() + 1);
				refreshScreensFromResume();

			}
		});

		increaseSkillButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentSkill() < adv.getInitialSkill())
					adv.setCurrentSkill(adv.getCurrentSkill() + 1);
				refreshScreensFromResume();

			}
		});

		increaseLuckButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentLuck() < adv.getInitialLuck())
					adv.setCurrentLuck(adv.getCurrentLuck() + 1);
				refreshScreensFromResume();

			}
		});
		
		increaseProvisionsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					adv.setProvisions(adv.getProvisions() + 1);
				refreshScreensFromResume();

			}
		});

		decreaseStaminaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentStamina() > 0)
					adv.setCurrentStamina(adv.getCurrentStamina() - 1);
				refreshScreensFromResume();

			}
		});

		decreaseSkillButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentSkill() > 0)
					adv.setCurrentSkill(adv.getCurrentSkill() - 1);
				refreshScreensFromResume();

			}
		});

		decreaseLuckButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentLuck() > 0)
					adv.setCurrentLuck(adv.getCurrentLuck() - 1);
				refreshScreensFromResume();

			}
		});
		
		decreaseProvisionsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getProvisions() > 0)
					adv.setProvisions(adv.getProvisions() - 1);
				refreshScreensFromResume();

			}
		});
		
		if(adv.getProvisionsValue() == null || adv.getProvisionsValue() < 0){
			increaseProvisionsButton.setVisibility(View.INVISIBLE);
			decreaseProvisionsButton.setVisibility(View.INVISIBLE);
			provisionsValue.setVisibility(View.INVISIBLE);
			provisionsText.setVisibility(View.INVISIBLE);
			buttonConsumeProvisions.setVisibility(View.INVISIBLE);
		}
		
		
		buttonConsumeProvisions.setText(adv.getConsumeProvisionText());
		provisionsText.setText(adv.getConsumeProvisionText());
	}
	
	public void setProvisionsValue(Integer value) {
		this.provisionsValue.setText(value.toString());
	}


	@Override
	public void refreshScreensFromResume() {
		Adventure adv = (Adventure) getActivity();

		skillValue.setText("" + adv.getCurrentSkill());
		staminaValue.setText("" + adv.getCurrentStamina());
		luckValue.setText("" + adv.getCurrentLuck());
		provisionsValue.setText("" + adv.getProvisions());

	}
	
	

}
