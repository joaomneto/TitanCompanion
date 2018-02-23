package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.SOTSAdventureCreation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SOTSAdventureCreationSkillFragment extends Fragment {

	public SOTSAdventureCreationSkillFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_20sots_adventure_creation_skill, container, false);

		List<Map<String, String>> skillList = new ArrayList<Map<String,String>>();


		List<String> stringArray = new ArrayList<String>();
		for (SOTSMartialArt art : SOTSMartialArt.values()) {
			stringArray.add(getResources().getString(art.getNameId()));
		}

		for (String string : stringArray) {
			Map<String, String> skill = new HashMap<String, String>();
			skill.put("skill", string);
			skillList.add(skill);
		}

		SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), skillList, R.layout.potions_item, new String[] {"skill"}, new int[] {R.id.potion_name} );

		ListView lView = (ListView) rootView.findViewById(R.id.skillList);
		lView.setAdapter(mAdapter);
		lView.setSelector(R.drawable.row_selector);

		lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				((SOTSAdventureCreation)getActivity()).setSkill(getArtFromString(stringArray.get(position)));
			}

		});

		return rootView;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		view.findViewById(R.id.buttonSaveAdventure).setOnClickListener((View v) -> {
			((AdventureCreation) this.getActivity()).saveAdventure(v);
		});
	}

	SOTSMartialArt getArtFromString(String nameString) {
		for (SOTSMartialArt art : SOTSMartialArt.values()) {
			if (nameString.equals(getResources().getString(art.getNameId())))
				return art;
		}
		return null;
	}

}
