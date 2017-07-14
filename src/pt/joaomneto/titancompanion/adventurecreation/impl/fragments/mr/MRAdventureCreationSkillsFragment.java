package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.mr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRSkill;
import pt.joaomneto.titancompanion.adventure.impl.util.SpellListAdapter;
import pt.joaomneto.titancompanion.adventurecreation.impl.MRAdventureCreation;

import static android.view.View.GONE;

public class MRAdventureCreationSkillsFragment extends AdventureFragment {

	private TextView spellScoreValue = null;
	private TextView spellPointsText = null;
	private String[] skillList = null;
	private SpellListAdapter selectedSkillsAdapter = null;
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

		SpellListAdapter adapter = new SpellListAdapter(activity,
				android.R.layout.simple_list_item_1, android.R.id.text1, Arrays.asList(getSkills()));

		listview.setAdapter(adapter);

		final ListView selectedSpellsListView = (ListView) rootView.findViewById(R.id.selectedSpellListView);


		selectedSkillsAdapter = new SpellListAdapter(activity,
				android.R.layout.simple_list_item_1, android.R.id.text1, activity.getSkills());

		selectedSpellsListView.setAdapter(selectedSkillsAdapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				if (activity.getSpellValue() <= activity.getSkills().size())
					return;
				activity.addSkill(getSkills()[position]);
				selectedSkillsAdapter.notifyDataSetChanged();
			}
		});

		selectedSpellsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle(R.string.deleteSkillQuestion)
						.setCancelable(false)
						.setNegativeButton(R.string.close,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						activity.removeSkill(position);
						selectedSkillsAdapter.notifyDataSetChanged();
					}
				});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		return rootView;
	}


	public MRSkill[] getSkills() {
		return MRSkill.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {

		selectedSkillsAdapter.notifyDataSetChanged();

	}

}
