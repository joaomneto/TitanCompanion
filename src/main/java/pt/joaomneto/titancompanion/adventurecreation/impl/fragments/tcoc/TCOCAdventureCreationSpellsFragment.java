package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.TCOCAdventureCreation;

public class TCOCAdventureCreationSpellsFragment extends Fragment {

    public static final int SKILL_POTION = 0;
    public static final int STRENGTH_POTION = 1;
    public static final int FORTUNE_POTION = 2;

    private TextView spellScoreValue = null;
    private String[] spellList = null;
    private TCOCAdventureCreation activity;

    public TCOCAdventureCreationSpellsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_02tcoc_adventurecreation_spells, container, false);

        spellScoreValue = rootView.findViewById(R.id.spellScoreValue);
        spellList = getResources().getStringArray(R.array.tcoc_spells);

        final ListView listview = rootView.findViewById(R.id.spellListView);

        activity = (TCOCAdventureCreation) getActivity();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, spellList);

        listview.setAdapter(adapter);

        final ListView selectedSpellsListView = rootView.findViewById(R.id.selectedSpellListView);

        final ArrayAdapter<String> selectedSpellsAdapter = new SpellListAdapter(activity, activity.getSpellList());

        selectedSpellsListView.setAdapter(selectedSpellsAdapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (activity.getSpellValue() <= activity.getSpellListSize())
                    return;
                activity.addSpell(spellList[position]);
                selectedSpellsAdapter.notifyDataSetChanged();
                spellScoreValue.setText((activity.getSpellValue() - activity.getSpellListSize()) + "");
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
                        spellScoreValue.setText((activity.getSpellValue() - activity.getSpellListSize()) + "");

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

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

    public TextView getSpellScoreValue() {
        return spellScoreValue;
    }


}
