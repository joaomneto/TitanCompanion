package pt.joaomneto.ffgbutil.adventurecreation.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.ffgbutil.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PotionsFragment extends Fragment {

	public PotionsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventure_potions, container, false);
		
		List<Map<String, String>> potionList = new ArrayList<Map<String,String>>();

		
		String[] stringArray = getResources().getStringArray(R.array.standard_potion_list);
		
		for (String string : stringArray) {
			Map<String, String> potion = new HashMap<String, String>();
			potion.put("potion", string);
			potionList.add(potion);
		}
		
		SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), potionList, android.R.layout.simple_list_item_1, new String[] {"potion"}, new int[] {android.R.id.text1} );
		
		ListView lView = (ListView) rootView.findViewById(R.id.potionList);
		lView.setAdapter(mAdapter);

		return rootView;
	}
}
