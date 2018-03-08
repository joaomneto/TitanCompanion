package pt.joaomneto.titancompanion.adventure.impl.fragments.pof;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureSpellsFragment;

/**
 * Created by Joao Neto on 06-07-2017.
 */

public class POFAdventureSpellsFragment extends AdventureSpellsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        Button addSpellButton = rootView.findViewById(R.id.addSpellButton);
        Spinner chooseSpellSpinner = rootView
                .findViewById(R.id.chooseSpellSpinner);
        View separator = rootView.findViewById(R.id.spellChooserSeparator);

        chooseSpellSpinner.setVisibility(View.INVISIBLE);
        addSpellButton.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);

        return rootView;
    }

}
