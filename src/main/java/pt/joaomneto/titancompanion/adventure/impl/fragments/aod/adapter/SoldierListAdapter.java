package pt.joaomneto.titancompanion.adventure.impl.fragments.aod.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.AODAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.AODAdventureSoldiersFragment;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.SoldiersDivision;

import java.util.List;

import static android.view.View.GONE;

public class SoldierListAdapter<T extends SoldiersDivision> extends ArrayAdapter<T> {

    private final Context context;
    private final List<T> values;
    private AODAdventure adv;
    private AODAdventureSoldiersFragment fragment;

    public SoldierListAdapter(Context context, List<T> values) {
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

        final TextView divisionName = soldiersView.getRootView().findViewById(R.id.aod_division_name);
        final TextView divisionTotal = soldiersView.getRootView().findViewById(R.id.aod_division_totalValue);

        final SoldiersDivision division = values.get(position);

        divisionName.setText(division.getLabel(adv));
        divisionTotal.setText("" + division.getQuantity());

        Button minusDivisionTotal = soldiersView.findViewById(R.id.aod_division_minusDivisionTotalTotal);
        Button plusDivisionTotal = soldiersView.findViewById(R.id.aod_division_plusDivisionTotalButton);

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

        final TextView divisionName = soldiersView.getRootView().findViewById(R.id.aod_division_name);
        final TextView divisionTotal = soldiersView.getRootView().findViewById(R.id.aod_division_totalValue);

        final SoldiersDivision division = values.get(position);
        final int divisionQuantity = fragment.getSkirmishValueForDivision(division.getCategory());

        if (divisionQuantity == 0) {
            soldiersView.setVisibility(GONE);
            return soldiersView;
        }

        divisionName.setText(division.getLabel(adv));
        divisionTotal.setText("" + divisionQuantity);

        final Button minusDivisionTotal = soldiersView.findViewById(R.id.aod_division_minusDivisionTotalTotal);


        minusDivisionTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                synchronized (this) {
                    int currentQuantity = fragment.getSkirmishValueForDivision(division.getCategory());
                    if (currentQuantity > 0 && (fragment.getTargetLosses() >= fragment.getTurnArmyLosses())) {
                        fragment.incrementTurnArmyLosses();
                        if (fragment.getTargetLosses() == fragment.getTurnArmyLosses()) {
                            fragment.setBattleState(AODAdventureSoldiersFragment.AODAdventureBattleState.STARTED);
                            fragment.refreshScreen();
                        }
                        int newQuantity = Math.max(0, currentQuantity - 5);
                        fragment.setSkirmishValueForDivision(division.getCategory(), newQuantity);
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

        final TextView divisionName = soldiersView.getRootView().findViewById(R.id.aod_division_name);
        final TextView divisionTotal = soldiersView.getRootView().findViewById(R.id.aod_division_totalValue);
        final TextView divisionBattleTotal = soldiersView.getRootView().findViewById(R.id.aod_division_battleValue);


        final SoldiersDivision division = values.get(position);

        divisionName.setText(division.getLabel(adv));
        divisionTotal.setText("" + division.getQuantity());
        divisionBattleTotal.setText("" + fragment.getSkirmishValueForDivision(division.getLabel(adv)));

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


        Button removeFromBattleButton = soldiersView.findViewById(R.id.aod_division_removeFromBattle);
        Button addToBattleButton = soldiersView.findViewById(R.id.aod_division_addToBattle);

        final SoldierListAdapter adapter = this;

        addToBattleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (division.getQuantity() > 0) {
//                    FIXME
                    //division.setQuantity(Math.max(0, division.getQuantity() - 5));
                    fragment.setSkirmishValueForDivision(division.getCategory(), fragment.getSkirmishValueForDivision(division.getCategory()) + 5);
                    fragment.refreshScreen();
                }
            }
        });

        removeFromBattleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Integer skirmishValueForDivision = fragment.getSkirmishValueForDivision(division.getCategory());
                if (skirmishValueForDivision > 0) {
//                    FIXME
//                    division.setQuantity(division.getQuantity() + 5);
                    fragment.setSkirmishValueForDivision(division.getCategory(), Math.max(0, skirmishValueForDivision - 5));
                    fragment.refreshScreen();
                }
            }
        });


        return soldiersView;
    }

    private void showSoldierRemovalAlert(final int position, final View view, final SoldierListAdapter adapter) {

        final SoldiersDivision division = values.get(position);

        AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
        alertbox.setMessage(context.getString(R.string.aod_removeAllSoldiers, division.getLabel(adv)));
        alertbox.setTitle(R.string.soldiersKilledQuestion);


        alertbox.setNegativeButton(R.string.no, (dialog, id) -> {
//                    FIXME
//            division.setQuantity(5);
            dialog.cancel();
        });

        alertbox.setPositiveButton(R.string.yes, (dialog, which) -> {
            values.remove(position);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        alertbox.show();
    }


}
