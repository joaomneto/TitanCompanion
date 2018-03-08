package pt.joaomneto.titancompanion.adventure.impl.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.fragments.adapter.CombatantListAdapter;
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll;
import pt.joaomneto.titancompanion.util.DiceRoller;

import java.util.ArrayList;
import java.util.List;

public class AdventureCombatFragment extends AdventureFragment {

    public static final String NORMAL = "NORMAL";
    public static final String SEQUENCE = "SEQUENCE";
    protected TextView combatResult = null;
    protected Button startCombatButton = null;
    protected Button combatTurnButton = null;
    protected Button addCombatButton = null;
    protected Button testLuckButton = null;
    protected Button resetButton = null;
    protected Button resetButton2 = null;
    protected Switch combatTypeSwitch = null;
    protected View rootView = null;
    protected List<Combatant> combatPositions = new ArrayList<AdventureCombatFragment.Combatant>();
    protected CombatantListAdapter combatantListAdapter = null;
    protected ListView combatantsListView = null;
    protected String combatMode = NORMAL;
    protected int handicap = 0;

    protected boolean draw = false;
    protected boolean luckTest = false;
    protected boolean hit = false;

    protected boolean combatStarted = false;

    protected int staminaLoss = 0;

    public AdventureCombatFragment() {

    }

    protected static int convertDamageStringToInteger(String damage) {
        if (damage.equals("1D6")) {
            return DiceRoller.rollD6();
        } else {
            return Integer.parseInt(damage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_adventure_combat, container, false);

        init();

        return rootView;
    }

    protected synchronized void combatTurn() {
        if (combatPositions.size() == 0)
            return;

        if (combatStarted == false) {
            combatStarted = true;
            combatTypeSwitch.setClickable(false);
        }

        if (combatMode.equals(SEQUENCE)) {
            sequenceCombatTurn();
        } else {
            standardCombatTurn();
        }


        if (combatPositions.isEmpty()) {
            resetCombat(false);
        }
    }

    protected void switchLayoutCombatStarted() {
        addCombatButton.setVisibility(View.GONE);
        combatTypeSwitch.setVisibility(View.GONE);
        startCombatButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);
        resetButton2.setVisibility(View.VISIBLE);
        testLuckButton.setVisibility(View.VISIBLE);
        combatTurnButton.setVisibility(View.VISIBLE);

    }

    public String getOfftext() {
        return getString(R.string.normal);
    }

    public String getOntext() {
        return getString(R.string.sequence);
    }

    protected void init() {

        combatResult = rootView.findViewById(R.id.combatResult);
        combatTurnButton = rootView.findViewById(R.id.attackButton);
        startCombatButton = rootView.findViewById(R.id.startCombat);
        addCombatButton = rootView.findViewById(R.id.addCombatButton);
        combatTypeSwitch = rootView.findViewById(R.id.combatType);
        resetButton = rootView.findViewById(R.id.resetCombat);
        resetButton2 = rootView.findViewById(R.id.resetCombat2);
        testLuckButton = rootView.findViewById(R.id.testLuckButton);

        combatTypeSwitch.setTextOff(getOfftext());
        combatTypeSwitch.setTextOn(getOntext());
        combatTypeSwitch.setOnCheckedChangeListener(new CombatTypeSwitchChangeListener());

        combatantsListView = rootView.findViewById(R.id.combatants);
        combatantListAdapter = new CombatantListAdapter(this.getActivity(), combatPositions);
        combatantsListView.setAdapter(combatantListAdapter);

        addCombatButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                addCombatButtonOnClick();
            }

        });

        resetButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                resetCombat(true);
                refreshScreensFromResume();
            }
        });

        resetButton2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                resetCombat(true);
                refreshScreensFromResume();
            }
        });

        combatTurnButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                combatTurn();
            }

        });

        startCombatButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startCombat();

            }

        });

        testLuckButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Adventure adv = (Adventure) getActivity();

                if (draw || luckTest)
                    return;
                luckTest = true;
                boolean result = adv.testLuckInternal();
                if (result) {
                    combatResult.setText(R.string.youreLucky);
                    if (hit) {
                        Combatant combatant = getCurrentEnemy();
                        combatant.setCurrentStamina(combatant.getCurrentStamina() - 1);
                        combatant.setStaminaLoss(combatant.getStaminaLoss() + 1);
                        int enemyStamina = combatant.getCurrentStamina();
                        if (enemyStamina <= 0 || (getKnockoutStamina() != null && staminaLoss >= getKnockoutStamina())) {
                            enemyStamina = 0;
                            Adventure.Companion.showAlert(getString(R.string.defeatedOpponent), adv);
                            removeAndAdvanceCombat(combatant);
                        }
                    } else {
                        adv.setCurrentStamina(adv.getCurrentStamina() + 1);
                        staminaLoss--;
                    }
                } else {
                    combatResult.setText(R.string.youreUnlucky);
                    if (hit) {
                        Combatant combatant = getCurrentEnemy();
                        combatant.setCurrentStamina(combatant.getCurrentStamina() + 1);
                        combatant.setStaminaLoss(combatant.getStaminaLoss() + 1);

                    } else {
                        adv.setCurrentStamina(adv.getCurrentStamina() - 1);
                        staminaLoss++;
                    }

                    if (getKnockoutStamina() != null && adv.getCurrentStamina() <= getKnockoutStamina()) {
                        Adventure.Companion.showAlert(getString(R.string.knockedOut), adv);
                    }

                    if (adv.getCurrentStamina() == 0) {
                        Adventure.Companion.showAlert(getString(R.string.youreDead), adv);
                    }
                }
                refreshScreensFromResume();
            }
        });

        refreshScreensFromResume();
    }

    protected Integer getKnockoutStamina() {
        return null;
    }

    protected void sequenceCombatTurn() {

        Combatant position = getCurrentEnemy();

        draw = false;
        luckTest = false;
        hit = false;
        Adventure adv = (Adventure) getActivity();
        DiceRoll diceRoll = DiceRoller.roll2D6();
        int skill = adv.getCombatSkillValue();
        int attackStrength = diceRoll.getSum() + skill + position.getHandicap();
        DiceRoll enemyDiceRoll = DiceRoller.roll2D6();
        int enemyAttackStrength = enemyDiceRoll.getSum() + position.getCurrentSkill();
        String combatResultText = "";
        if (attackStrength > enemyAttackStrength) {
            if (!position.isDefenseOnly()) {
                Boolean suddenDeath = suddenDeath(diceRoll, enemyDiceRoll);
                if (suddenDeath == null || !suddenDeath) {
                    int damage = getDamage();
                    position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
                    hit = true;
                    combatResultText += (getString(R.string.hitEnemy) + " (" + diceRoll.getSum() + " + " + skill
                            + (position.getHandicap() >= 0 ? (" + " + position.getHandicap()) : "") + ") vs (" + enemyDiceRoll.getSum() + " + "
                            + position.getCurrentSkill() + "). (-" + damage + getString(R.string.staminaInitials) + ")");
                } else {
                    position.setCurrentStamina(0);
                    Adventure.Companion.showAlert(getString(R.string.defeatSuddenDeath), adv);
                }
            } else {
                draw = true;
                combatResultText += (getString(R.string.blockedAttack) + " (" + diceRoll.getSum() + " + " + skill
                        + (position.getHandicap() >= 0 ? (" + " + position.getHandicap()) : "") + ") vs (" + enemyDiceRoll.getSum() + " + " + position.getCurrentSkill()
                        + ")");
            }
        } else if (attackStrength < enemyAttackStrength) {
            int damage = convertDamageStringToInteger(position.getDamage());
            adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina() - damage)));
            combatResultText += (getString(R.string.youWereHit) + " (" + diceRoll.getSum() + " + " + skill + (position.getHandicap() >= 0 ? (" + " + position.getHandicap()) : "")
                    + ") vs (" + enemyDiceRoll.getSum() + " + " + position.getCurrentSkill() + "). (-" + damage + R.string.staminaInitials + ")");
        } else {

            combatResultText += (R.string.bothMissed);
            draw = true;
        }

        if (position.getCurrentStamina() == 0) {
            removeAndAdvanceCombat(position);
            combatResultText += "\n";
            combatResultText += getString(R.string.defeatedEnemy);
        } else {
            advanceCombat(position);
        }

        if (adv.getCurrentStamina() == 0) {
            removeAndAdvanceCombat(position);
            combatResultText += "\n";
            combatResultText += getString(R.string.youveDied);
        }

        combatResult.setText(combatResultText);

        refreshScreensFromResume();

    }

    protected Combatant advanceCombat(Combatant combatant) {
        int index = combatPositions.indexOf(combatant);
        Combatant currentEnemy = null;

        if (!combatPositions.isEmpty()) {
            if (index <= combatPositions.size() - 2) {
                currentEnemy = combatPositions.get(index + 1);
            } else {
                currentEnemy = combatPositions.get(0);
            }
            changeActiveCombatant(currentEnemy);
        } else {
            resetCombat(false);
        }

        return currentEnemy;
    }

    private void changeActiveCombatant(Combatant currentEnemy) {
        for (Combatant combatant : combatPositions) {
            combatant.setActive(false);
        }
        currentEnemy.setActive(true);
    }


    protected void removeAndAdvanceCombat(Combatant combatant) {
        combatPositions.remove(combatant);
        if (!combatPositions.isEmpty()) {
            Combatant currentEnemy = advanceCombat(combatant);
            currentEnemy.setDefenseOnly(false);
        }
    }

    protected void standardCombatTurn() {
        Combatant position = getCurrentEnemy();

        // if (!finishedCombats.contains(currentCombat)) {
        draw = false;
        luckTest = false;
        hit = false;
        Adventure adv = (Adventure) getActivity();
        DiceRoll diceRoll = DiceRoller.roll2D6();
        int skill = adv.getCombatSkillValue();
        int attackStrength = diceRoll.getSum() + skill + position.getHandicap();
        DiceRoll enemyDiceRoll = DiceRoller.roll2D6();
        int enemyAttackStrength = enemyDiceRoll.getSum() + position.getCurrentSkill();
        String combatResultText = "";
        if (attackStrength > enemyAttackStrength) {
            Boolean suddenDeath = suddenDeath(diceRoll, enemyDiceRoll);
            if (suddenDeath == null || !suddenDeath) {
                int damage = getDamage();

                position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - getDamage()));
                position.setStaminaLoss(position.getStaminaLoss() + damage);
                hit = true;
                combatResultText += (getString(R.string.hitEnemy) + " (" + diceRoll.getSum() + " + " + skill
                        + (position.getHandicap() >= 0 ? (" + " + position.getHandicap()) : "") + ") vs (" + enemyDiceRoll.getSum() + " + " + position.getCurrentSkill()
                        + ")");
            } else {
                position.setCurrentStamina(0);
                Adventure.Companion.showAlert(R.string.defeatSuddenDeath, adv);
            }

        } else if (attackStrength < enemyAttackStrength) {
            int damage = convertDamageStringToInteger(position.getDamage());
            staminaLoss += damage;
            adv.setCurrentStamina((Math.max(0, adv.getCurrentStamina() - damage)));
            combatResultText += (getString(R.string.youWereHit) + " (" + diceRoll.getSum() + " + " + skill + (position.getHandicap() >= 0 ? (" + " + position.getHandicap()) : "")
                    + ") vs (" + enemyDiceRoll.getSum() + " + " + position.getCurrentSkill() + ")");
        } else {

            combatResult.setText(R.string.bothMissed);
            draw = true;
        }

        if (position.getCurrentStamina() == 0 || (getKnockoutStamina() != null && position.getStaminaLoss() >= getKnockoutStamina())) {
            removeAndAdvanceCombat(position);
            combatResultText += "\n";
            combatResultText += getString(R.string.defeatedEnemy);
        }

        if (getKnockoutStamina() != null && staminaLoss >= getKnockoutStamina()) {
            removeAndAdvanceCombat(position);
            Adventure.Companion.showAlert(R.string.knockedOut, adv);
        }

        if (adv.getCurrentStamina() == 0) {
            removeAndAdvanceCombat(position);
            Adventure.Companion.showAlert(R.string.youreDead, adv);
        }

        combatResult.setText(combatResultText);

        refreshScreensFromResume();

    }

    protected void addCombatButtonOnClick() {
        addCombatButtonOnClick(R.layout.component_add_combatant);
    }

    protected void addCombatButtonOnClick(int layoutId) {

        Adventure adv = (Adventure) getActivity();

        final View addCombatantView = adv.getLayoutInflater().inflate(layoutId, null);

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (combatStarted)
            return;


        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        builder.setTitle(R.string.addEnemy).setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                confirmCombatAction(mgr, addCombatantView);


            }

        });

        AlertDialog alert = builder.create();

        EditText skillValue = addCombatantView.findViewById(R.id.enemySkillValue);

        alert.setView(addCombatantView);

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        skillValue.requestFocus();

        alert.show();
    }

    protected void confirmCombatAction(InputMethodManager mgr, View addCombatantView) {
        mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);

        EditText enemySkillValue = addCombatantView.findViewById(R.id.enemySkillValue);
        EditText enemyStaminaValue = addCombatantView.findViewById(R.id.enemyStaminaValue);
        EditText handicapValue = addCombatantView.findViewById(R.id.handicapValue);

        String skillS = enemySkillValue.getText().toString();
        String staminaS = enemyStaminaValue.getText().toString();
        Integer skill;
        Integer stamina;
        Integer handicap;
        try {
            skill = Integer.valueOf(skillS);
            stamina = Integer.valueOf(staminaS);
            handicap = handicapValue.getText() != null && handicapValue.getText().length() > 0 ? Integer.valueOf(handicapValue.getText().toString()) : 0;
        } catch (NumberFormatException e) {
            Adventure.Companion.showAlert(getString(R.string.youMustFillSkillAndStamina), AdventureCombatFragment.this.getActivity());
            return;
        }


        addCombatant(rootView, skill, stamina, handicap, getDefaultEnemyDamage());
    }

    protected void addCombatant(final View rootView, Integer skill, Integer stamina, Integer handicap, String damage) {

        Combatant combatPosition = new Combatant(stamina, skill, handicap, combatPositions.size() > 0, damage, combatPositions.size() == 0);
        if (!combatPositions.isEmpty())
            combatPosition.setDefenseOnly(true);
        combatPositions.add(combatPosition);
        refreshScreensFromResume();

    }

    @Override
    public void refreshScreensFromResume() {
        if (combatantListAdapter != null)
            combatantListAdapter.notifyDataSetChanged();
    }

    protected int getDamage() {
        return 2;
    }

    protected void resetCombat(boolean clearResult) {

        staminaLoss = 0;

        combatPositions.clear();
        combatantListAdapter.notifyDataSetChanged();
        combatMode = NORMAL;
        combatStarted = false;
        if (clearResult) {
            combatResult.setText("");
        }

        combatTypeSwitch.setClickable(true);
        combatTypeSwitch.setChecked(false);

        switchLayoutReset(clearResult);

        refreshScreensFromResume();
    }

    protected void switchLayoutReset(boolean clearResult) {
        addCombatButton.setVisibility(View.VISIBLE);
        combatTypeSwitch.setVisibility(View.VISIBLE);
        startCombatButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(clearResult ? View.GONE : View.VISIBLE);

        testLuckButton.setVisibility(View.GONE);
        combatTurnButton.setVisibility(View.GONE);
        resetButton2.setVisibility(View.GONE);
    }

    protected String combatTypeSwitchBehaviour(boolean isChecked) {
        return combatMode = isChecked ? SEQUENCE : NORMAL;
    }

    protected void startCombat() {
        if (!combatPositions.isEmpty()) {
            combatTurn();

            switchLayoutCombatStarted();
        }
    }

    protected String getDefaultEnemyDamage() {
        return "2";
    }

    protected Boolean suddenDeath(DiceRoll diceRoll, DiceRoll enemyDiceRoll) {
        return false;
    }

    protected Combatant getCurrentEnemy() {
        for (Combatant combatant : combatPositions) {
            if (combatant.isActive()) {
                return combatant;
            }
        }
        throw new IllegalStateException("No active enemy combatant found.");
    }

    public class Combatant {

        Integer currentStamina;
        Integer currentSkill;
        Integer handicap;
        String damage;
        boolean defenseOnly;
        Integer staminaLoss = 0;
        boolean active;

        public Combatant(Integer stamina, Integer skill, Integer handicap, boolean defenseOnly, String damage, boolean active) {
            this.currentStamina = stamina;
            this.currentSkill = skill;
            this.handicap = handicap;
            this.defenseOnly = defenseOnly;
            this.damage = damage;
            this.active = active;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public Integer getCurrentStamina() {
            return currentStamina;
        }

        public void setCurrentStamina(Integer currentStamina) {
            this.currentStamina = currentStamina;
        }

        public Integer getCurrentSkill() {
            return currentSkill;
        }

        public void setCurrentSkill(Integer currentSkill) {
            this.currentSkill = currentSkill;
        }

        public boolean isDefenseOnly() {
            return defenseOnly;
        }

        public void setDefenseOnly(boolean defenseOnly) {
            this.defenseOnly = defenseOnly;
        }

        public Integer getHandicap() {
            return handicap;
        }

        public void setHandicap(Integer handicap) {
            this.handicap = handicap;
        }

        public String getDamage() {
            return damage;
        }

        public void setDamage(String damage) {
            this.damage = damage;
        }

        public Integer getStaminaLoss() {
            return staminaLoss;
        }

        public void setStaminaLoss(Integer staminaLoss) {
            this.staminaLoss = staminaLoss;
        }

    }

    private class CombatTypeSwitchChangeListener implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            combatTypeSwitchBehaviour(isChecked);

        }

    }

}
