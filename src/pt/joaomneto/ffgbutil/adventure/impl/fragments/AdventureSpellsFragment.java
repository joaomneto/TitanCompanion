package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import java.util.Arrays;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.SpellAdventure;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

public abstract class AdventureSpellsFragment extends AdventureFragment {

	ListView spellList = null;
	Spinner chooseSpellSpinner = null;
	Button addSpellButton = null;
	
	protected SpellAdventure adv = null;

	public AdventureSpellsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_08ss_adventure_spells, container, false);

		spellList = (ListView) rootView.findViewById(R.id.spellList);
		chooseSpellSpinner = (Spinner) rootView
				.findViewById(R.id.chooseSpellSpinner);
		addSpellButton = (Button) rootView.findViewById(R.id.addSpellButton);


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
								specificSpellActivation(adv, spell);
								if (getSingleUse()) {
									adv.getSpells().remove(position);
									((ArrayAdapter<String>) spellList
											.getAdapter())
											.notifyDataSetChanged();
								}
							}

						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		addSpellButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.getSpells().add(
						chooseSpellSpinner.getSelectedItem().toString());
				refreshScreensFromResume();

			}
		});

		chooseSpellSpinner.setAdapter(getSpellAdapter());

		refreshScreensFromResume();

		return rootView;
	}
	
	protected boolean getSingleUse(){
		return true;
	}

	protected abstract void specificSpellActivation(final Adventure adv,
			String spell);

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {
		((ArrayAdapter<String>) spellList.getAdapter()).notifyDataSetChanged();

	}

	private ArrayAdapter<String> getSpellAdapter() {

		List<String> list = Arrays.asList(getSpellList());

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return dataAdapter;
	}

	protected abstract String[] getSpellList();

}
