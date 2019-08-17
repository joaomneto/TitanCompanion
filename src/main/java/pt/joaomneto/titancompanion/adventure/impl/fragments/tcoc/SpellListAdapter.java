package pt.joaomneto.titancompanion.adventure.impl.fragments.tcoc;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pt.joaomneto.titancompanion.adventure.impl.TCOCAdventure;

import java.util.List;

public class SpellListAdapter extends ArrayAdapter<String> {


    public SpellListAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, -1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TCOCAdventure adv = (TCOCAdventure) getContext();

        final View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        final TextView text1 = view.getRootView().findViewById(android.R.id.text1);

        String text = adv.getSpellList().get(position);

        Integer number = adv.getSpells().get(text);
        if (number != null && number > 1) {
            text += " (" + number + ")";
        }

        text1.setText(text);

        return view;
    }

}