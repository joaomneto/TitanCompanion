package pt.joaomneto.titancompanion.adventure.impl.fragments.aod.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.AODAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.AODAdventureSoldiersFragment;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.SoldiersDivision;

import static android.view.View.GONE;

public class SoldierListAdapter extends ArrayAdapter<SoldiersDivision> {

    private final Context context;
    private final List<SoldiersDivision> values;
    private AODAdventure adv;
    private AODAdventureSoldiersFragment fragment;

    public SoldierListAdapter(Context context, List<SoldiersDivision> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.adv = (AODAdventure) context;
        this.fragment = adv.getSoldiersFragment();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (fragment.isBattleStaging()) {
            return getViewStaging(position, parent);
        } else if (fragment.isBattleStarted()) {
            return getViewBattle(position, parent);
        }
        return getViewStandard(position, parent);
    }

    @NonNull
    private View getViewStandard(final int position, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View soldiersView = inflater.inflate(R.layout.component_36aod_division, parent, false);

        final TextView divisionName = (TextView) soldiersView.getRootView().findViewById(R.id.aod_division_name);
        final TextView divisionTotal = (TextView) soldiersView.getRootView().findViewById(R.id.aod_division_totalValue);

        final SoldiersDivision division = values.get(position);

        divisionName.setText(division.getType());
        divisionTotal.setText("" + division.getQuantity());

        Button minusDivisionTotal = (Button) soldiersView.findViewById(R.id.aod_division_minusDivisionTotalTotal);
        Button plusDivisionTotal = (Button) soldiersView.findViewById(R.id.aod_division_plusDivisionTotalButton);

        final SoldierListAdapter adapter = this;

        minusDivisionTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                division.decrementAllValues();
                divisionTotal.setText("" + division.getQuantity());

                if (division.getQuantity() == 0) {
                    showSoldierRemovalAlert(position, soldiersView, adapter);
                }
            }
        });
        plusDivisionTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                division.incrementAllValues();
                divisionTotal.setText("" + division.getQuantity());
            }
        });


        return soldiersView;
    }

    @NonNull
    private View getViewBattle(final int position, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View soldiersView = inflater.inflate(R.layout.component_36aod_division_battle, parent, false);

        final TextView divisionName = (TextView) soldiersView.getRootView().findViewById(R.id.aod_division_name);
        final TextView divisionTotal = (TextView) soldiersView.getRootView().findViewById(R.id.aod_division_totalValue);

        final String type = values.get(position).getType();
        final int divisionQuantity = fragment.getSkirmishValueForDivision(type);

        if (divisionQuantity == 0) {
            soldiersView.setVisibility(GONE);
            return soldiersView;
        }

        divisionName.setText(type);
        divisionTotal.setText("" + divisionQuantity);

        final Button minusDivisionTotal = (Button) soldiersView.findViewById(R.id.aod_division_minusDivisionTotalTotal);


        minusDivisionTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                synchronized (this) {
                    int currentQuantity = fragment.getSkirmishValueForDivision(type);
                    if (currentQuantity > 0 && (fragment.getTargetLosses() >= fragment.getTurnArmyLosses())) {
                        fragment.incrementTurnArmyLosses();
                        if (fragment.getTargetLosses() == fragment.getTurnArmyLosses()) {
                            fragment.setBattleState(AODAdventureSoldiersFragment.AODAdventureBattleState.STARTED);
                            fragment.refreshScreensFromResume();
                        }
                        int newQuantity = Math.max(0, currentQuantity - 5);
                        fragment.setSkirmishValueForDivision(type, newQuantity);
                        divisionTotal.setText("" + newQuantity);
                    }
                }
            }
        });

        minusDivisionTotal.setVisibility(fragment.isBattleDamage() ? View.VISIBLE : View.GONE);

        return soldiersView;
    }

    @NonNull
    private View getViewStaging(final int position, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View soldiersView = inflater.inflate(R.layout.component_36aod_division_staging, parent, false);

        final TextView divisionName = (TextView) soldiersView.getRootView().findViewById(R.id.aod_division_name);
        final TextView divisionTotal = (TextView) soldiersView.getRootView().findViewById(R.id.aod_division_totalValue);
        final TextView divisionBattleTotal = (TextView) soldiersView.getRootView().findViewById(R.id.aod_division_battleValue);


        final SoldiersDivision division = values.get(position);

        divisionName.setText(division.getType());
        divisionTotal.setText("" + division.getQuantity());
        divisionBattleTotal.setText("" + fragment.getSkirmishValueForDivision(division.getType()));

        divisionBattleTotal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (!event.isShiftPressed()) {

                        String text = divisionBattleTotal.getText().toString();
                        if (text != null && text.toString().length() > 0) {
                            Integer value = new Integer(text.toString());

                            if (value % 5 != 0) {
                                value = 5 * (Math.round(value / 5));
                            }
                            divisionBattleTotal.setText(value + "");
                        } else {
                            divisionBattleTotal.setText("0");
                        }
                        return true; // consume.
                    }
                }
                return false; // pass on to other listeners.
            }
        });


        Button removeFromBattleButton = (Button) soldiersView.findViewById(R.id.aod_division_removeFromBattle);
        Button addToBattleButton = (Button) soldiersView.findViewById(R.id.aod_division_addToBattle);

        final SoldierListAdapter adapter = this;

        addToBattleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (division.getQuantity() > 0) {
                    division.setQuantity(Math.max(0, division.getQuantity() - 5));
                    fragment.setSkirmishValueForDivision(division.getType(), fragment.getSkirmishValueForDivision(division.getType()) + 5);
                    fragment.refreshScreensFromResume();
                }
            }
        });

        removeFromBattleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Integer skirmishValueForDivision = fragment.getSkirmishValueForDivision(division.getType());
                if (skirmishValueForDivision > 0) {
                    division.setQuantity(division.getQuantity() + 5);
                    fragment.setSkirmishValueForDivision(division.getType(), Math.max(0, skirmishValueForDivision - 5));
                    fragment.refreshScreensFromResume();
                }
            }
        });


        return soldiersView;
    }

    private void showSoldierRemovalAlert(final int position, final View view, final SoldierListAdapter adapter) {

        final SoldiersDivision division = values.get(position);

        AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
        alertbox.setMessage("Do you wish to remove all " + division.getType() + " from the army?");
        alertbox.setTitle("Soldiers killed?");


        alertbox.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                division.setQuantity(5);
                dialog.cancel();
            }
        });

        alertbox.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                values.remove(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        alertbox.show();
    }


}
