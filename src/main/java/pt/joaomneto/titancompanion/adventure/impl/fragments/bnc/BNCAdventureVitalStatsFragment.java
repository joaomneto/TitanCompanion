package pt.joaomneto.titancompanion.adventure.impl.fragments.bnc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.BNCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class BNCAdventureVitalStatsFragment extends AdventureVitalStatsFragment {

	TextView willpowerValue = null;

	Button increaseWillpowerButton = null;

	Button decreaseWillpowerButton = null;

	public BNCAdventureVitalStatsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_25bnc_adventure_vitalstats, container, false);

		initialize(rootView);

		decreaseWillpowerButton = rootView
				.findViewById(R.id.minusWillpowerButton);
		increaseWillpowerButton = rootView
				.findViewById(R.id.plusWillpowerButton);
		willpowerValue = rootView.findViewById(R.id.statsWillpowerValue);
		final BNCAdventure adv = (BNCAdventure) getActivity();

		willpowerValue = rootView.findViewById(R.id.statsWillpowerValue);
		willpowerValue.setOnClickListener(view -> {

			AlertDialog.Builder alert = createAlertForInitialStatModification(R.string.setInitialWillpower, (dialog, whichButton) -> {

				EditText input = ((AlertDialog) dialog).findViewById(R.id.alert_editText_field);

				int value = Integer.parseInt(input.getText().toString());
				adv.setInitialWillpower(value);
			});


			alert.show();
		});

		decreaseWillpowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentWillpower() > 0)
					adv.setCurrentWillpower(adv.getCurrentWillpower() - 1);
				refreshScreensFromResume();

			}
		});

		increaseWillpowerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adv.getCurrentWillpower() < adv.getInitialWillpower())
					adv.setCurrentWillpower(adv.getCurrentWillpower() + 1);
				refreshScreensFromResume();

			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {
		super.refreshScreensFromResume();
		BNCAdventure adv = (BNCAdventure) getActivity();
		willpowerValue.setText("" + adv.getCurrentWillpower());

	}

}
