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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.AODAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.titancompanion.adventure.impl.fragments.aod.adapter.SoldierListAdapter;
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.adapter.RobotListAdapter;
import pt.joaomneto.titancompanion.util.DiceRoller;
import pt.joaomneto.titancompanion.util.EnglishNumberToWords;

public class AODAdventureSoldiersFragment extends AdventureFragment {

    private static final Map<AODAdventureBattleBalance, Map<Integer, AODAdventureBattleResults>> battleResults = new HashMap<>();


    private static final String ARMY = "ARMY";
    private static final String ENEMY = "ENEMY";

    static {
        battleResults.put(AODAdventureBattleBalance.EVEN, new HashMap<Integer, AODAdventureBattleResults>());
        battleResults.put(AODAdventureBattleBalance.SUPERIOR, new HashMap<Integer, AODAdventureBattleResults>());
        battleResults.put(AODAdventureBattleBalance.INFERIOR, new HashMap<Integer, AODAdventureBattleResults>());

        battleResults.get(AODAdventureBattleBalance.EVEN).put(1, AODAdventureBattleResults.A10);
        battleResults.get(AODAdventureBattleBalance.EVEN).put(2, AODAdventureBattleResults.A5);
        battleResults.get(AODAdventureBattleBalance.EVEN).put(3, AODAdventureBattleResults.A5);
        battleResults.get(AODAdventureBattleBalance.EVEN).put(4, AODAdventureBattleResults.E5);
        battleResults.get(AODAdventureBattleBalance.EVEN).put(5, AODAdventureBattleResults.E5);
        battleResults.get(AODAdventureBattleBalance.EVEN).put(6, AODAdventureBattleResults.E10);

        battleResults.get(AODAdventureBattleBalance.SUPERIOR).put(1, AODAdventureBattleResults.A5);
        battleResults.get(AODAdventureBattleBalance.SUPERIOR).put(2, AODAdventureBattleResults.E5);
        battleResults.get(AODAdventureBattleBalance.SUPERIOR).put(3, AODAdventureBattleResults.E5);
        battleResults.get(AODAdventureBattleBalance.SUPERIOR).put(4, AODAdventureBattleResults.E5);
        battleResults.get(AODAdventureBattleBalance.SUPERIOR).put(5, AODAdventureBattleResults.E10);
        battleResults.get(AODAdventureBattleBalance.SUPERIOR).put(6, AODAdventureBattleResults.E15);

        battleResults.get(AODAdventureBattleBalance.INFERIOR).put(1, AODAdventureBattleResults.A15);
        battleResults.get(AODAdventureBattleBalance.INFERIOR).put(2, AODAdventureBattleResults.A10);
        battleResults.get(AODAdventureBattleBalance.INFERIOR).put(3, AODAdventureBattleResults.A5);
        battleResults.get(AODAdventureBattleBalance.INFERIOR).put(4, AODAdventureBattleResults.A5);
        battleResults.get(AODAdventureBattleBalance.INFERIOR).put(5, AODAdventureBattleResults.E5);
        battleResults.get(AODAdventureBattleBalance.INFERIOR).put(6, AODAdventureBattleResults.E5);
    }

    protected static Integer[] gridRows;

    protected ListView soldiersList = null;
    protected Button buttonStartSkirmish = null;
    private Button buttonCommitForces = null;
    private Button buttonCombatReset = null;
    private Button buttonAddSoldiers = null;
    private Button buttonCancelBattle = null;
    private Button buttonCombatTurn = null;

    private Button buttonPlusEnemyTroops = null;
    private Button buttonMinusEnemyTroops = null;
    private TextView enemyForcesQuantity = null;
    private TextView enemyForcesQuantityBattle = null;
    private TextView enemyForcesLabelBattle = null;

    private TextView combatResult = null;
    private TextView armyTitle = null;

    private LinearLayout buttonsNormal = null;
    private LinearLayout buttonsStaging = null;
    private LinearLayout buttonsBattle = null;

    protected View rootView = null;

    private AODAdventureBattleState battleState = AODAdventureBattleState.NORMAL;
    private AODAdventureBattleBalance battleBalance = AODAdventureBattleBalance.EVEN;

    private Map<String, Integer> skirmishArmy = new HashMap<>();
    private int enemyForces = 0;
    private int turnArmyLosses = 0;
    private int targetLosses;


    AODAdventure adv = null;


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

        adv = (AODAdventure) this.getActivity();

        buttonStartSkirmish = (Button) rootView.findViewById(R.id.aod_soldiers_buttonStartSkirmish);
        buttonCombatReset = (Button) rootView.findViewById(R.id.aod_soldiers_buttonResetBattle);
        buttonCommitForces = (Button) rootView.findViewById(R.id.aod_soldiers_buttonCommitForces);
        buttonAddSoldiers = (Button) rootView.findViewById(R.id.aod_soldiers_buttonAddNewSoldiers);
        buttonCombatTurn = (Button) rootView.findViewById(R.id.aod_soldiers_buttonCombatTurn);
        soldiersList = (ListView) rootView.findViewById(R.id.aod_soldiers_soldiersList);
        buttonsBattle = (LinearLayout) rootView.findViewById(R.id.aod_soldiers_ButtonsBattle);
        buttonsStaging = (LinearLayout) rootView.findViewById(R.id.aod_soldiers_ButtonsStaging);
        buttonsNormal = (LinearLayout) rootView.findViewById(R.id.aod_soldiers_ButtonsNormal);
        buttonCancelBattle = (Button) rootView.findViewById(R.id.aod_soldiers_buttonCancelBattle);
        buttonPlusEnemyTroops = (Button) rootView.findViewById(R.id.aod_division_plusEnemyForcesButton);
        buttonMinusEnemyTroops = (Button) rootView.findViewById(R.id.aod_division_minusEnemyForcesButton);
        enemyForcesQuantity = (TextView) rootView.findViewById(R.id.aod_division_enemyForcesValue);
        enemyForcesQuantityBattle = (TextView) rootView.findViewById(R.id.aod_soldiers_enemyTroopsValue);
        enemyForcesLabelBattle = (TextView) rootView.findViewById(R.id.aod_soldiers_enemyTroopsLabel);
        armyTitle = (TextView) rootView.findViewById((R.id.aod_soldiers_armyTitle));

        combatResult = (TextView) rootView.findViewById(R.id.aod_soldiers_combatResultText);

        buttonPlusEnemyTroops.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enemyForces = enemyForces + 5;
                refreshScreensFromResume();
            }
        });

        buttonMinusEnemyTroops.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enemyForces = Math.max(0, enemyForces - 5);
                refreshScreensFromResume();
            }
        });


        buttonAddSoldiers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addSoldiersButtonOnClick();
            }
        });

        if (adv != null) {
            soldiersList.setAdapter(new SoldierListAdapter(adv, adv.getSoldiers()));
        }


        buttonStartSkirmish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commitForces();

            }

        });

        buttonCommitForces.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startBattle();
            }
        });

        buttonCombatTurn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                combatTurn();
            }
        });

        OnClickListener resetBattleOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCombat();
            }
        };
        buttonCombatReset.setOnClickListener(resetBattleOnClickListener);
        buttonCancelBattle.setOnClickListener(resetBattleOnClickListener);

        refreshScreensFromResume();
    }


    protected void startBattle() {


        combatResult.setText("");

        if (enemyForces == 0) {
            Adventure.showAlert("You must set the number of enemy forces!", adv);
            return;
        }

        int totalForces = 0;

        for (String type : skirmishArmy.keySet()) {
            totalForces += skirmishArmy.get(type);
        }

        if (totalForces == 0) {
            Adventure.showAlert("You must commit some of your troops to the battle!", adv);
            return;
        }

        battleState = AODAdventureBattleState.STARTED;


        if (totalForces > enemyForces) {
            battleBalance = AODAdventureBattleBalance.SUPERIOR;
        } else if (totalForces < enemyForces) {
            battleBalance = AODAdventureBattleBalance.INFERIOR;
        } else {
            battleBalance = AODAdventureBattleBalance.EVEN;
        }


        refreshScreensFromResume();
    }

    private void combatTurn() {

        if(targetLosses == turnArmyLosses){
            battleState = AODAdventureBattleState.STARTED;
            targetLosses = 0;
            turnArmyLosses = 0;
        }

        if (battleState.equals(AODAdventureBattleState.DAMAGE)) {
            Adventure.showAlert("You must distribute the losses for your army.", adv);
            return;
        }

        combatResult.setText("");

        int totalForces = 0;

        for (String type : skirmishArmy.keySet()) {
            totalForces += skirmishArmy.get(type);
        }



        if (totalForces > enemyForces) {
            battleBalance = AODAdventureBattleBalance.SUPERIOR;
        } else if (totalForces < enemyForces) {
            battleBalance = AODAdventureBattleBalance.INFERIOR;
        } else {
            battleBalance = AODAdventureBattleBalance.EVEN;
        }

        int diceRoll = DiceRoller.rollD6();




        AODAdventureBattleResults result = battleResults.get(battleBalance).get(diceRoll);

        combatResult.append("You've rolled a " + EnglishNumberToWords.convert(diceRoll) + ". ");
        combatResult.append("You are in ");
        combatResult.append(battleBalance.getTextToDisplay() +" ("+totalForces+" vs "+enemyForces+")");


        if (result.getArmyOrEnemy().equals("ARMY")) {
            combatResult.append(" and you suffer the loss of " + result.getQuantity() + " troops. ");
            battleState = AODAdventureBattleState.DAMAGE;
            targetLosses = result.getQuantity();
        } else {
            combatResult.append(" and you kill " + result.getQuantity() + " enemy troops!");
            enemyForces -= result.getQuantity();
        }



        if(enemyForces==0){
            adv.getSoldiers().recalculate(skirmishArmy);
            combatResult.append(" You have therefore destroyed the opposing army!");
            buttonCombatTurn.setVisibility(View.GONE);
            buttonCombatReset.setText("Close Combat");
        }

        if(battleState.equals(AODAdventureBattleState.DAMAGE) && totalForces <= result.getQuantity()){
            skirmishArmy.clear();
            adv.getSoldiers().recalculate(skirmishArmy);
            combatResult.append(" Your army has therefore been destroyed...");
            buttonCombatTurn.setVisibility(View.GONE);
            buttonCombatReset.setText("Close Combat");
        }


        refreshScreensFromResume();

    }

    private void commitForces() {

        battleState = AODAdventureBattleState.STAGING;
        refreshScreensFromResume();
    }

    private void resetCombat() {
        buttonCombatTurn.setVisibility(View.VISIBLE);
        buttonCombatReset.setText(R.string.reset);
        skirmishArmy.clear();
        battleState = AODAdventureBattleState.NORMAL;
        enemyForces = 0;

        for (SoldiersDivision sd : adv.getSoldiers()) {
            sd.resetToInitialValues();
        }
        refreshScreensFromResume();
    }

    protected void addSoldiersButtonOnClick() {

        final AODAdventure adv = (AODAdventure) getActivity();

        final View addSoldiersView = adv.getLayoutInflater().inflate(R.layout.component_36aod_add_soldiers, null);

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        builder.setTitle("Add Soldiers").setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mgr.hideSoftInputFromWindow(addSoldiersView.getWindowToken(), 0);
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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

                SoldiersDivision sd = new SoldiersDivision(type, quantity);

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

    public boolean isBattleStarted() {
        return battleState.equals(AODAdventureBattleState.STARTED) || battleState.equals(AODAdventureBattleState.DAMAGE);
    }

    public boolean isBattleDamage() {
        return battleState.equals(AODAdventureBattleState.DAMAGE);
    }

    public void setSkirmishValueForDivision(String name, Integer value) {
        this.skirmishArmy.put(name, value);
    }

    public boolean isBattleStaging() {
        return battleState.equals(AODAdventureBattleState.STAGING);
    }

    public boolean isBattleNormal() {
        return battleState.equals(AODAdventureBattleState.NORMAL);
    }


    public void alternateButtonsLayout() {
        buttonsNormal.setVisibility(isBattleNormal() ? View.VISIBLE : View.GONE);
        buttonsBattle.setVisibility(isBattleStarted() ? View.VISIBLE : View.GONE);
        combatResult.setVisibility(isBattleStarted() ? View.VISIBLE : View.GONE);
        buttonsStaging.setVisibility(isBattleStaging() ? View.VISIBLE : View.GONE);
        armyTitle.setText(isBattleNormal() ? R.string.currentArmy : (isBattleStaging() ? R.string.selectForces : R.string.skirmishForces));
        enemyForcesQuantityBattle.setVisibility(isBattleStarted() ? View.VISIBLE : View.GONE);
        enemyForcesLabelBattle.setVisibility(isBattleStarted() ? View.VISIBLE : View.GONE);
    }

    public void setBattleState(AODAdventureBattleState battleState) {
        this.battleState = battleState;
    }

    @Override
    public void refreshScreensFromResume() {



        AODAdventure adv = (AODAdventure) this.getActivity();
        SoldierListAdapter adapter = (SoldierListAdapter) soldiersList.getAdapter();

        int index = soldiersList.getFirstVisiblePosition();
        View v = soldiersList.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - soldiersList.getPaddingTop());

        if (isBattleStarted()) {
            soldiersList.setAdapter(new SoldierListAdapter(adv, getBattleSoldiers()));
        } else {
            soldiersList.setAdapter(new SoldierListAdapter(adv, adv.getSoldiers()));
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }


        enemyForcesQuantity.setText("" + enemyForces);
        enemyForcesQuantityBattle.setText("" + enemyForces);

        alternateButtonsLayout();


        soldiersList.setSelectionFromTop(index, top);

    }

    private List<SoldiersDivision> getBattleSoldiers() {
        List<SoldiersDivision> battleSoldiers = new ArrayList<>();
        for (String type : skirmishArmy.keySet()) {
            if(skirmishArmy.get(type) > 0) {
                battleSoldiers.add(new SoldiersDivision(type, 0));
            }
        }
        return battleSoldiers;
    }

    public enum AODAdventureBattleState {
        NORMAL,
        STAGING,
        STARTED,
        DAMAGE;

        AODAdventureBattleState() {
        }
    }

    private enum AODAdventureBattleBalance {
        EVEN("an even situation with your enemy"),
        SUPERIOR("a superior situation compared to your enemy"),
        INFERIOR("an inferior situation compared to your enemy");

        String textToDisplay;

        public String getTextToDisplay() {
            return textToDisplay;
        }

        public void setTextToDisplay(String textToDisplay) {
            this.textToDisplay = textToDisplay;
        }

        AODAdventureBattleBalance(String textToDisplay) {
            this.textToDisplay = textToDisplay;
        }
    }

    private enum AODAdventureBattleResults {


        A5(5, ARMY),
        A10(10, ARMY),
        A15(15, ARMY),
        E5(5, ENEMY),
        E10(10, ENEMY),
        E15(15, ENEMY);

        Integer quantity;
        String armtOrEnemy;

        AODAdventureBattleResults(Integer quantity, String armtOrEnemy) {
            this.quantity = quantity;
            this.armtOrEnemy = armtOrEnemy;
        }

        public Integer getQuantity() {
            return quantity;
        }


        public String getArmyOrEnemy() {
            return armtOrEnemy;
        }

        public void setArmyOrEnemy(String armyOrEnemy) {
            this.armtOrEnemy = armyOrEnemy;
        }
    }

    public void incrementTurnArmyLosses() {
        this.turnArmyLosses += 5;
    }

    public int getTurnArmyLosses() {
        return turnArmyLosses;
    }

    public int getTargetLosses() {
        return targetLosses;
    }
}
