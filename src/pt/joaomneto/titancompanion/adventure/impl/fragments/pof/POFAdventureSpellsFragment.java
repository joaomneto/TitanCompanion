package pt.joaomneto.titancompanion.adventure.impl.fragments.pof;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.SpellAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;
import pt.joaomneto.titancompanion.adventure.impl.util.SpellListAdapter;

/**
 * Created by 962633 on 06-07-2017.
 */

public class POFAdventureSpellsFragment extends AdventureSpellsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        Button addSpellButton = (Button) rootView.findViewById(R.id.addSpellButton);
        Spinner chooseSpellSpinner = (Spinner) rootView
                .findViewById(R.id.chooseSpellSpinner);

        chooseSpellSpinner.setVisibility(View.INVISIBLE);
        addSpellButton.setVisibility(View.INVISIBLE);

        return rootView;
    }

}
