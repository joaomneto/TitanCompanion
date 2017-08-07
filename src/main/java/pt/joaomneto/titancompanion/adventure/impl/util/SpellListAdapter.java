package pt.joaomneto.titancompanion.adventure.impl.util;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pt.joaomneto.titancompanion.R;

/**
 * Created by Joao Neto on 23-05-2017.
 */

public class SpellListAdapter extends ArrayAdapter {

    private final LayoutInflater mInflater;
    private final int mResource;
    private final Context mContext;
    private final int mDropDownResource;
    private final int mFieldId;
    private List<Spell> mObjects;


    public SpellListAdapter(@NonNull Context context, @LayoutRes int resource,
                            @IdRes int textViewResourceId, @NonNull List<Spell> objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = mDropDownResource = resource;
        mObjects = objects;
        mFieldId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = mInflater.inflate(mResource, parent, false);
        TextView text = view.findViewById(mFieldId);

        text.setText(mObjects.get(position).getName());

        return view;
    }
}
