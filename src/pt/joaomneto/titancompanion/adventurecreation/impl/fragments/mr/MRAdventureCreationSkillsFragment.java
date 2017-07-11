package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.mr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.MRAdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc.SpellListAdapter;

import static android.view.View.GONE;

public class MRAdventureCreationSkillsFragment extends Fragment {

	private TextView spellScoreValue = null;
	private TextView spellPointsText = null;
	private String[] skillList = null;
	private MRAdventureCreation activity;

	public MRAdventureCreationSkillsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_29mr_adventurecreation_skills, container, false);

		spellScoreValue = (TextView) rootView.findViewById(R.id.spellScoreValue);
		spellPointsText = (TextView) rootView.findViewById(R.id.spellPointsText);

		spellScoreValue.setVisibility(GONE);
		spellPointsText.setVisibility(GONE);

		skillList = getResources().getStringArray(R.array.tcoc_spells);

		final ListView listview = (ListView) rootView.findViewById(R.id.spellListView);

		activity = (MRAdventureCreation) getActivity();

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_list_item_1, android.R.id.text1, skillList);

		listview.setAdapter(adapter);

		final ListView selectedSpellsListView = (ListView) rootView.findViewById(R.id.selectedSpellListView);

		final ArrayAdapter<String> selectedSpellsAdapter = new SpellListAdapter(activity, activity.getSpellList());

		selectedSpellsListView.setAdapter(selectedSpellsAdapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				if (activity.getSpellValue() <= activity.getSkills().size())
					return;
				activity.addSkill(skillList[position]);
				selectedSpellsAdapter.notifyDataSetChanged();
			}
		});

		selectedSpellsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle(R.string.deleteSpellQuestion)
						.setCancelable(false)
						.setNegativeButton(R.string.close,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						activity.removeSpell(position);
						selectedSpellsAdapter.notifyDataSetChanged();
					}
				});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		return rootView;
	}




}
