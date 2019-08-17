package pt.joaomneto.titancompanion.adventurecreation.impl.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pt.joaomneto.titancompanion.adventure.impl.util.TranslatableEnum;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Joao Neto on 19-06-2017.
 */

public class TranslatableEnumAdapter extends ArrayAdapter<TranslatableEnum> {

    private List<? extends TranslatableEnum> values = null;
    private int listItemResource;

    public TranslatableEnumAdapter(Context context, int listItemResource, List<? extends TranslatableEnum> values) {
        super(context, listItemResource, android.R.id.text1, (List<TranslatableEnum>) values);
        this.values = values;
        this.listItemResource = listItemResource;
    }

    public TranslatableEnumAdapter(Context context, int listItemResource, TranslatableEnum[] values) {
        this(context, listItemResource, Arrays.asList(values));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView view = (TextView) inflater.inflate(listItemResource, parent, false);

        view.setText(values.get(position).getLabelId());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView view = (TextView) inflater.inflate(listItemResource, parent, false);

        view.setText(values.get(position).getLabelId());

        return view;
    }
}
