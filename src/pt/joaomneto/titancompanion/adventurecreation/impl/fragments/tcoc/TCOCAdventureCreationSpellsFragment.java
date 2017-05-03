package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.TCOCAdventureCreation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

import java.util.List;

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

        spellScoreValue = (TextView) rootView.findViewById(R.id.spellScoreValue);
        spellList = getResources().getStringArray(R.array.tcoc_spells);

        final ListView listview = (ListView) rootView.findViewById(R.id.spellListView);

        activity = (TCOCAdventureCreation) getActivity();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, spellList);

        listview.setAdapter(adapter);

        final ListView selectedSpellsListView = (ListView) rootView.findViewById(R.id.selectedSpellListView);

        final ArrayAdapter<String> selectedSpellsAdapter = new SpellListAdapter(activity, activity.getSpellList());

        selectedSpellsListView.setAdapter(selectedSpellsAdapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (activity.getSpellValue() == activity.getSpellList().size())
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
                builder.setTitle("Delete spell?")
                        .setCancelable(false)
                        .setNegativeButton("Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.removeSpell(position);
                        selectedSpellsAdapter.notifyDataSetChanged();
                        spellScoreValue.setText((activity.getSpellValue() - activity.getSpellList().size()) + "");
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return rootView;
    }

    public TextView getSpellScoreValue() {
        return spellScoreValue;
    }

    public class SpellListAdapter extends ArrayAdapter<String> {


        public SpellListAdapter(@NonNull Context context, @NonNull List<String> objects) {
            super(context, -1, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            TCOCAdventureCreation adv = (TCOCAdventureCreation) getContext();

            final View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            final TextView text1 = (TextView) view.getRootView().findViewById(android.R.id.text1);

            String text = adv.getSpellList().get(position);

            Integer number = adv.getSpells().get(text);
            if (number != null && number > 1) {
                text += " (" + number + ")";
            }

            text1.setText(text);

            return view;
        }

    }

}
