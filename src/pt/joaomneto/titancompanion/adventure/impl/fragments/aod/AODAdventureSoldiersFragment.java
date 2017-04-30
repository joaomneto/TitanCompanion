package pt.joaomneto.titancompanion.adventure.impl.fragments.aod;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.AODAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.adapter.SoldierListAdapter;
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.adapter.RobotListAdapter;

public class AODAdventureSoldiersFragment extends AdventureFragment {

    protected static Integer[] gridRows;

    protected ListView soldiersList = null;
    protected Button buttonStartSkirmish = null;
    private Button buttonCommitForces = null;
    private Button buttonCombatReset = null;
    private Button buttonAddSoldiers = null;

    private LinearLayout buttonsNormal = null;
    private LinearLayout buttonsStaging = null;
    private LinearLayout buttonsBattle = null;

    protected View rootView = null;

    private boolean battleStaging = false;
    private boolean battleStarted = false;

    private Map<String, Integer> skirmishArmy = new HashMap<>();

    public AODAdventureSoldiersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_36aod_adventure_soldiers, container, false);

        init();

/*
		registerForContextMenu(soldiersList);
*/

        return rootView;
    }

    protected void init() {

        final AODAdventure adv = (AODAdventure) this.getActivity();

        buttonStartSkirmish = (Button) rootView.findViewById(R.id.aod_soldiers_buttonStartSkirmish);
        buttonCombatReset = (Button) rootView.findViewById(R.id.aod_soldiers_buttonResetBattle);
        buttonCommitForces = (Button) rootView.findViewById(R.id.aod_soldiers_buttonCommitForces);
        buttonAddSoldiers = (Button) rootView.findViewById(R.id.aod_soldiers_buttonAddNewSoldiers);
        soldiersList = (ListView) rootView.findViewById(R.id.aod_soldiers_soldiersList);
        buttonsBattle = (LinearLayout) rootView.findViewById(R.id.aod_soldiers_ButtonsBattle);
        buttonsStaging = (LinearLayout) rootView.findViewById(R.id.aod_soldiers_ButtonsStaging);
        buttonsNormal = (LinearLayout) rootView.findViewById(R.id.aod_soldiers_ButtonsNormal);

        buttonAddSoldiers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addSoldiersButtonOnClick();
            }
        });

        soldiersList.setAdapter(new SoldierListAdapter(adv, adv.getSoldiers()));


        buttonStartSkirmish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                startBattle();
            }

        });

        buttonCommitForces.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commitForces();
            }
        });

        buttonCombatReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCombat();
            }
        });

        refreshScreensFromResume();
    }


    protected void startBattle() {
        skirmishArmy.clear();
        battleStaging = true;
        battleStarted = false;
        refreshScreensFromResume();
    }

    private void commitForces() {

        battleStaging = false;
        battleStarted = true;
        refreshScreensFromResume();
    }

    private void resetCombat() {
        skirmishArmy.clear();
        battleStarted = false;
        battleStaging = false;
        refreshScreensFromResume();
    }

    protected void addSoldiersButtonOnClick() {

        final AODAdventure adv = (AODAdventure) getActivity();

        final View addSoldiersView = adv.getLayoutInflater().inflate(R.layout.component_36aod_add_soldiers, null);

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        builder.setTitle("Add Soldiers").setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mgr.hideSoftInputFromWindow(addSoldiersView.getWindowToken(), 0);
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                mgr.hideSoftInputFromWindow(addSoldiersView.getWindowToken(), 0);

                EditText soldiersType = (EditText) addSoldiersView.findViewById(R.id.aod_soldiers_type);
                EditText soldiersQuantity = (EditText) addSoldiersView.findViewById(R.id.aod_soldiers_quantity);

                String type = soldiersType.getText().toString();
                String quantityS = soldiersQuantity.getText().toString();
                Integer quantity = null;
                try {
                    quantity = Integer.valueOf(quantityS);
                } catch (NumberFormatException e) {
                    Adventure.showAlert("You must fill the type and quantity values!", AODAdventureSoldiersFragment.this.getActivity());
                    return;
                }

                SoldiersDivision sd = new SoldiersDivision(type,quantity);

                adv.getSoldiers().add(sd);

                refreshScreensFromResume();
            }

        });

        AlertDialog alert = builder.create();

        EditText typeValue = (EditText) addSoldiersView.findViewById(R.id.aod_soldiers_type);

        alert.setView(addSoldiersView);

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        typeValue.requestFocus();

        alert.show();
    }

    public Map<String, Integer> getSkirmishArmy() {
        return skirmishArmy;
    }

    public void setSkirmishArmy(Map<String, Integer> skirmishArmy) {
        this.skirmishArmy = skirmishArmy;
    }

    public Integer getSkirmishValueForDivision(String name) {
        return this.skirmishArmy.get(name) != null ? this.skirmishArmy.get(name) : 0;
    }

    public void setSkirmishValueForDivision(String name, Integer value) {
        this.skirmishArmy.put(name, value);
    }

    public boolean isBattleStaging() {
        return battleStaging;
    }

    public void setBattleStaging(boolean battleStaging) {
        this.battleStaging = battleStaging;
    }

    public void alternateButtonsLayout() {
        buttonsNormal.setVisibility(!battleStaging && !battleStarted ? View.VISIBLE : View.GONE);
        buttonsBattle.setVisibility(!battleStaging && battleStarted ? View.VISIBLE : View.GONE);
        buttonsStaging.setVisibility(battleStaging && !battleStarted ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshScreensFromResume() {
        AODAdventure adv = (AODAdventure) this.getActivity();
        SoldierListAdapter adapter = (SoldierListAdapter) soldiersList.getAdapter();
        adapter.notifyDataSetChanged();

        alternateButtonsLayout();

    }

}
