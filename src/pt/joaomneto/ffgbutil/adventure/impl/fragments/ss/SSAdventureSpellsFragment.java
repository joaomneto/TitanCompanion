package pt.joaomneto.ffgbutil.adventure.impl.fragments.ss;

import java.util.Arrays;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.SSAdventure;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SSAdventureSpellsFragment extends DialogFragment implements
		AdventureFragment {

	ListView spellList = null;
	Spinner chooseSpellSpinner = null;
	Button addSpellButton = null;

	public SSAdventureSpellsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_08ss_adventure_spells, container, false);

		spellList = (ListView) rootView.findViewById(R.id.spellList);
		chooseSpellSpinner = (Spinner) rootView.findViewById(R.id.chooseSpellSpinner);
		addSpellButton = (Button) rootView.findViewById(R.id.addSpellButton);

		final SSAdventure adv = (SSAdventure) getActivity();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				adv.getSpells());
		spellList.setAdapter(adapter);

		spellList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new AlertDialog.Builder(adv);
				builder.setTitle("Use spell?")
						.setCancelable(false)
						.setNegativeButton("Close",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							public void onClick(DialogInterface dialog,
									int which) {
								String spell = adv.getSpells().get(position);
								if (spell.equals("Skill")) {
									adv.setCurrentSkill(adv.getCurrentSkill()
											+ (adv.getInitialSkill() / 2));
									if (adv.getCurrentSkill() > adv
											.getInitialSkill())
										adv.setCurrentSkill(adv
												.getInitialSkill());
								} else if (spell.equals("Stamina")) {
									adv.setCurrentStamina(adv
											.getCurrentStamina()
											+ (adv.getInitialStamina() / 2));
									if (adv.getCurrentStamina() > adv
											.getInitialStamina())
										adv.setCurrentStamina(adv
												.getInitialStamina());
								} else if (spell.equals("Luck")) {
									adv.setCurrentLuck(adv.getCurrentLuck()
											+ (adv.getInitialLuck() / 2));
									if (adv.getCurrentLuck() > adv
											.getInitialLuck())
										adv.setCurrentLuck(adv.getInitialLuck());
								}
								adv.getSpells().remove(position);
								((ArrayAdapter<String>) spellList.getAdapter())
										.notifyDataSetChanged();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});
		
		addSpellButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				adv.getSpells().add(chooseSpellSpinner.getSelectedItem().toString());
				refreshScreensFromResume();
				
			}
		});
		
		chooseSpellSpinner.setAdapter(getSpellAdapter());
		
		refreshScreensFromResume();

		return rootView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {
		((ArrayAdapter<String>) spellList.getAdapter()).notifyDataSetChanged();

	}

	private ArrayAdapter<String> getSpellAdapter() {

		List<String> list = Arrays.asList(new String[] { "Skill", "Stamina",
				"Luck", "Fire", "Ice", "Illusion", "Friendship", "Growth",
				"Bless", "Fear", "Withering", "Curse" });

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return dataAdapter;
	}

}
