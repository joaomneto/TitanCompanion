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

public class AWFAdventureCreationSuperpowerFragment extends Fragment {
	
	public AWFAdventureCreationSuperpowerFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_17awf_adventure_creation_superpowers, container, false);
		
		List<Map<String, String>> superpowerList = new ArrayList<Map<String,String>>();

		
		final String[] stringArray = getResources().getStringArray(R.array.superpower_list);
		
		for (String string : stringArray) {
			Map<String, String> superpower = new HashMap<String, String>();
			superpower.put("superpower", string);
			superpowerList.add(superpower);
		}
		
		SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), superpowerList, R.layout.potions_item, new String[] {"superpower"}, new int[] {R.id.potion_name} );
		
		ListView lView = (ListView) rootView.findViewById(R.id.superpowerList);
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