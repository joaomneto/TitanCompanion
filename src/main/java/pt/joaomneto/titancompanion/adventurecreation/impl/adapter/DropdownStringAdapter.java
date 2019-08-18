package pt.joaomneto.titancompanion.adventurecreation.impl.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Joao Neto on 19-06-2017.
 */

public class DropdownStringAdapter extends ArrayAdapter<String> {

    private List<? extends String> values = null;
    private int listItemResource;

    public DropdownStringAdapter(Context context, int listItemResource, List<? extends String> values) {
        super(context, listItemResource, android.R.id.text1, (List<String>) values);
        this.listItemResource = listItemResource;
        this.values = values;
    }

    public DropdownStringAdapter(Context context, int listItemResource, String[] values) {
        this(context, listItemResource, Arrays.asList(values));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView view = (TextView) inflater.inflate(listItemResource, parent, false);

        view.setText(values.get(position));

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView view = (TextView) inflater.inflate(listItemResource, parent, false);

        view.setText(values.get(position));

        return view;
    }
}
