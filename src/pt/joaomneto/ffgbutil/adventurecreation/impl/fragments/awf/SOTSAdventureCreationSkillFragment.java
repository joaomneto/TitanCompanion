package pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.awf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventurecreation.impl.AWFAdventureCreation;
import android.os.Bundle;
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

		
		final String[] stringArray = getResources().getStringArray(R.array.skill_list);
		
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
				((AWFAdventureCreation)getActivity()).setSuperPower(stringArray[position]);
			}

		});

		return rootView;
	}
	
	
}
