package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import pt.joaomneto.titancompanion.adventurecreation.impl.SAAdventureCreation;

/**
 * Created by Joao Neto on 19-06-2017.
 */

public class SAWeaponSpinnerAdapter extends ArrayAdapter<SAWeapon> {

    private List<SAWeapon> values = null;

    public SAWeaponSpinnerAdapter(Context context, List<SAWeapon> values) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        this.values = values;
    }

    public SAWeaponSpinnerAdapter(Context context, SAWeapon[] values) {
        this(context, Arrays.asList(values));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView view = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        view.setText(values.get(position).getLabelId());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView view = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        view.setText(values.get(position).getLabelId());

        return view;
    }
}
