package pt.joaomneto.titancompanion.adventure.impl.fragments.aod.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.RCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.SoldiersDivision;
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.Robot;

public class SoldierListAdapter extends ArrayAdapter<SoldiersDivision> {

    private final Context context;
    private final List<SoldiersDivision> values;
    private RCAdventure adv;

    public SoldierListAdapter(Context context, List<SoldiersDivision> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.adv = (RCAdventure) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View robotView = inflater.inflate(R.layout.component_36aod_division, parent, false);

        final TextView divisionName = (TextView) robotView.getRootView().findViewById(R.id.aod_division_name);
        final TextView divisionTotal = (TextView) robotView.getRootView().findViewById(R.id.aod_division_totalValue);

        final SoldiersDivision division = values.get(position);

        divisionName.setText(division.getType());
        divisionTotal.setText("" + division.getQuantity());

        Button minusDivisionTotal = (Button) robotView.findViewById(R.id.aod_division_minusDivisionTotalButton);
        Button plusDivisionTotal = (Button) robotView.findViewById(R.id.aod_division_plusDivisionTotalButton);

        final SoldierListAdapter adapter = this;

        minusDivisionTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                division.setQuantity(Math.max(0, division.getQuantity() - 5));
                divisionTotal.setText("" + division.getQuantity());
            }
        });
        plusDivisionTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                division.setQuantity(division.getQuantity() + 5);
                divisionTotal.setText("" + division.getQuantity());
            }
        });


        return robotView;
    }


}
