package pt.joaomneto.titancompanion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pt.joaomneto.titancompanion.adventure.SpellAdventure;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

/**
 * Created by Joao Neto on 19-06-2017.
 */

public class SpellSpinnerAdapter extends ArrayAdapter<Spell> {

    private final Context context;
    private final List<Spell> values;
    private SpellAdventure adv;

    public SpellSpinnerAdapter(Context context, List<Spell> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.adv = (SpellAdventure) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView view = (TextView) inflater.inflate(android.R.layout.simple_spinner_item, parent, false);

        view.setText(values.get(position).getName());

        return view;
    }


}
