package pt.joaomneto.titancompanion.adventure.impl.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adapter.SpellSpinnerAdapter;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.SpellAdventure;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;
import pt.joaomneto.titancompanion.adventure.impl.util.SpellListAdapter;

public class AdventureSpellsFragment extends AdventureFragment {

    protected SpellAdventure adv = null;
    ListView spellList = null;
    Spinner chooseSpellSpinner = null;
    Button addSpellButton = null;

    public AdventureSpellsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adv = (SpellAdventure) getActivity();
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(
                R.layout.fragment_08ss_adventure_spells, container, false);

        spellList = (ListView) rootView.findViewById(R.id.spellList);
        chooseSpellSpinner = (Spinner) rootView
                .findViewById(R.id.chooseSpellSpinner);
        addSpellButton = (Button) rootView.findViewById(R.id.addSpellButton);


        SpellListAdapter adapter = new SpellListAdapter(adv,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                adv.getSpells());
        spellList.setAdapter(adapter);

        spellList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                final int position = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.useSpellQuestion)
                        .setCancelable(false)
                        .setNegativeButton(R.string.close,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @SuppressWarnings("unchecked")
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Spell spell = adv.getSpells().get(position);
                                specificSpellActivation(adv, spell);
                                if (adv.isSpellSingleUse()) {
                                    adv.getSpells().remove(position);
                                    ((ArrayAdapter<String>) spellList
                                            .getAdapter())
                                            .notifyDataSetChanged();
                                }
                            }

                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        addSpellButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Spell spell = (Spell) chooseSpellSpinner.getSelectedItem();
                if (adv.isSpellSingleUse() || !adv.getSpells().contains(spell)) {
                    adv.getSpells().add(
                            spell);
                } else {
                    Adventure.showAlert(R.string.spellAlreadyChosen, adv);
                }
                refreshScreensFromResume();

            }
        });

        chooseSpellSpinner.setAdapter(getSpellAdapter());

        refreshScreensFromResume();

        return rootView;
    }


    protected void specificSpellActivation(final Adventure adv, Spell spell) {
        spell.getAction().accept(adv);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void refreshScreensFromResume() {
        ((ArrayAdapter<String>) spellList.getAdapter()).notifyDataSetChanged();

    }

    private ArrayAdapter<Spell> getSpellAdapter() {

        SpellSpinnerAdapter dataAdapter = new SpellSpinnerAdapter(getActivity(), ((SpellAdventure) getActivity()).getSpellList());
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return dataAdapter;
    }


}
