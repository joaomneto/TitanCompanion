package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.awf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.AWFAdventureCreation;

public class AWFAdventureCreationSuperpowerFragment extends Fragment {

    public AWFAdventureCreationSuperpowerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_17awf_adventure_creation_superpowers, container, false);

        List<Map<String, String>> skillList = new ArrayList<Map<String, String>>();


        final String[] stringArray = getResources().getStringArray(R.array.superpower_list);

        for (String string : stringArray) {
            Map<String, String> skill = new HashMap<String, String>();
            skill.put("skill", string);
            skillList.add(skill);
        }

        SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), skillList, R.layout.potions_item, new String[]{"skill"}, new int[]{R.id.potion_name});

        ListView lView = rootView.findViewById(R.id.superpowerList);
        lView.setAdapter(mAdapter);
        lView.setSelector(R.drawable.row_selector);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                ((AWFAdventureCreation) getActivity()).setSuperPower(stringArray[position]);
            }

        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.buttonSaveAdventure).setOnClickListener((View v) -> {
            ((AdventureCreation) this.getActivity()).saveAdventure();
        });
    }


}
