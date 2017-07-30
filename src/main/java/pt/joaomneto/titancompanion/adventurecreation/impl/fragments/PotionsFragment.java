package pt.joaomneto.titancompanion.adventurecreation.impl.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.TWOFMAdventureCreation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PotionsFragment extends Fragment {

	public static final int SKILL_POTION = 0;
	public static final int STRENGTH_POTION = 1;
	public static final int FORTUNE_POTION = 2;
	
	public PotionsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventurecreation_potions, container, false);
		
		List<Map<String, String>> potionList = new ArrayList<Map<String,String>>();

		
		String[] stringArray = getResources().getStringArray(R.array.standard_potion_list);
		
		for (String string : stringArray) {
			Map<String, String> potion = new HashMap<String, String>();
			potion.put("potion", string);
			potionList.add(potion);
		}
		
		SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), potionList, R.layout.potions_item, new String[] {"potion"}, new int[] {R.id.potion_name} );
		
		ListView lView = (ListView) rootView.findViewById(R.id.potionList);
		lView.setAdapter(mAdapter);
		lView.setSelector(R.drawable.row_selector);
		
		lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				((TWOFMAdventureCreation)getActivity()).setPotion(position);
			}

		});

		return rootView;
	}
}
