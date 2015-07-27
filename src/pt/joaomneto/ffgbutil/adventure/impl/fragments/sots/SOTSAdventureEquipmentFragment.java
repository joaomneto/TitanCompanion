package pt.joaomneto.ffgbutil.adventure.impl.fragments.sots;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
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
import android.widget.TextView;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.SOTSAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment;

public class SOTSAdventureEquipmentFragment extends AdventureEquipmentFragment {


	Button minusHummingButton = null;
	Button plusHummingButton = null;
	TextView hummingValue = null;
	Button minusArmourPButton = null;
	Button plusArmourPButton = null;
	TextView armourPValue = null;
	Button minusWillowButton = null;
	Button plusWillowButton = null;
	TextView willowValue = null;
	Button minusBowelButton = null;
	Button plusBowelButton = null;
	TextView bowelValue = null;


	TextView hummingLabel = null;
	TextView armourPLabel = null;
	TextView willowLabel = null;
	TextView bowelLabel = null;

	public SOTSAdventureEquipmentFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_20sots_adventure_equipment, container, false);

		final Adventure adv = (Adventure) getActivity();

		initialize(rootView, adv);

		return rootView;
	}

	@Override
	protected void initialize(View rootView, Adventure adv_) {
		super.initialize(rootView, adv_);

		hummingValue = (TextView) rootView.findViewById(R.id.hummingValue);
		armourPValue = (TextView) rootView.findViewById(R.id.armourPValue);
		willowValue = (TextView) rootView.findViewById(R.id.willowValue);
		bowelValue = (TextView) rootView.findViewById(R.id.bowelValue);

		hummingLabel = (TextView) rootView.findViewById(R.id.hummingLabel);
		armourPLabel = (TextView) rootView.findViewById(R.id.armourPLabel);
		willowLabel = (TextView) rootView.findViewById(R.id.willowLabel);
		bowelLabel = (TextView) rootView.findViewById(R.id.bowelLabel);

		minusHummingButton = (Button) rootView.findViewById(R.id.minusHummingButton);
		plusHummingButton = (Button) rootView.findViewById(R.id.plusHummingButton);

		minusArmourPButton = (Button) rootView.findViewById(R.id.minusArmourPButton);
		plusArmourPButton = (Button) rootView.findViewById(R.id.plusArmourPButton);

		minusWillowButton = (Button) rootView.findViewById(R.id.minusWillowButton);
		plusWillowButton = (Button) rootView.findViewById(R.id.plusWillowButton);

		minusBowelButton = (Button) rootView.findViewById(R.id.minusBowelButton);
		plusBowelButton = (Button) rootView.findViewById(R.id.plusBowelButton);

		final SOTSAdventure adv = (SOTSAdventure) adv_;

		minusHummingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setHummingBulbArrows(Math.max(adv.getHummingBulbArrows() - 1, 0));
				refreshScreensFromResume();
			}
		});

		plusHummingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setHummingBulbArrows(adv.getHummingBulbArrows() + 1);
				refreshScreensFromResume();
			}
		});

		minusArmourPButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setArmourPiercerArrows(Math.max(adv.getArmourPiercerArrows() - 1, 0));
				refreshScreensFromResume();
			}
		});

		plusArmourPButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setArmourPiercerArrows(adv.getArmourPiercerArrows() + 1);
				refreshScreensFromResume();
			}
		});

		minusWillowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setWillowLeafArrows(Math.max(adv.getWillowLeafArrows() - 1, 0));
				refreshScreensFromResume();
			}
		});

		plusWillowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setWillowLeafArrows(adv.getWillowLeafArrows() + 1);
				refreshScreensFromResume();
			}
		});

		minusBowelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setBowelRakerArrows(Math.max(adv.getBowelRakerArrows() - 1, 0));
				refreshScreensFromResume();
			}
		});

		plusBowelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setBowelRakerArrows(adv.getBowelRakerArrows() + 1);
				refreshScreensFromResume();
			}
		});

		if(!adv.getSkill().equals("Kyujutsu")){
			minusHummingButton.setVisibility(View.GONE);
			plusHummingButton.setVisibility(View.GONE);
			hummingValue.setVisibility(View.GONE);
			minusArmourPButton.setVisibility(View.GONE);
			plusArmourPButton.setVisibility(View.GONE);
			armourPValue.setVisibility(View.GONE);
			minusWillowButton.setVisibility(View.GONE);
			plusWillowButton.setVisibility(View.GONE);
			willowValue.setVisibility(View.GONE);
			minusBowelButton.setVisibility(View.GONE);
			plusBowelButton.setVisibility(View.GONE);
			bowelValue.setVisibility(View.GONE);

			willowLabel.setVisibility(View.GONE);
			bowelLabel.setVisibility(View.GONE);
			armourPLabel.setVisibility(View.GONE);
			hummingLabel.setVisibility(View.GONE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		SOTSAdventure adv = (SOTSAdventure) getActivity();

		hummingValue.setText(""+adv.getHummingBulbArrows());
		armourPValue.setText(""+adv.getArmourPiercerArrows());
		willowValue.setText(""+adv.getWillowLeafArrows());
		bowelValue.setText(""+adv.getBowelRakerArrows());

	}

}
