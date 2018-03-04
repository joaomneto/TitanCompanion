package pt.joaomneto.titancompanion.adventurecreation.impl.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.TWOFMAdventureCreation;
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;
import pt.joaomneto.titancompanion.util.LocaleHelper;

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

        List<Map<String, String>> potionList = new ArrayList<Map<String, String>>();


        String[] stringArray = getResources().getStringArray(R.array.standard_potion_list);

        for (String string : stringArray) {
            Map<String, String> potion = new HashMap<String, String>();
            potion.put("potion", string);
            potionList.add(potion);
        }

        SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), potionList, R.layout.potions_item, new String[]{"potion"}, new int[]{R.id.potion_name});

        ListView lView = rootView.findViewById(R.id.potionList);
        lView.setAdapter(mAdapter);
        lView.setSelector(R.drawable.row_selector);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                ((TWOFMAdventureCreation) getActivity()).setPotion(position);
            }

        });

        Spinner spinner = rootView.findViewById(R.id.potionDosesSpinner);

        SpinnerAdapter adapter =
                ArrayAdapter.createFromResource(
                        this.getActivity().getActionBar().getThemedContext(),
                        R.array.potion_doses,
                        android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TWOFMAdventureCreation) getActivity()).setPotionDoses(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ((TWOFMAdventureCreation) getActivity()).setPotionDoses(getDefaultPotionDosage());
            }
        });


//        1 dose == position 0 of the spinner
        spinner.setSelection(getDefaultPotionDosage() - 1);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.buttonSaveAdventure).setOnClickListener((View v) -> {
            ((AdventureCreation) this.getActivity()).saveAdventure();
        });
    }

    private int getDefaultPotionDosage() {
        return ("fr".equals(LocaleHelper.getLanguage(this.getActivity())) || ((AdventureCreation) this.getActivity()).getGamebook().equals(FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN)) ? 2 : 1;
    }
}
