package pt.joaomneto.titancompanion.adventure.impl.fragments.trok;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import kotlin.jvm.functions.Function0;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll;
import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.DropdownStringAdapter;
import pt.joaomneto.titancompanion.util.DiceRoller;

import java.util.Arrays;
import java.util.List;

public class TROKAdventureCombatFragment extends AdventureCombatFragment {

    public static final String TROK15_GUNFIGHT = "TROK15_GUNFIGHT";
    String overrideDamage = null;
    private Spinner damageSpinner = null;
    private TextView damageText = null;
    private List<String> damageList = Arrays.asList("4", "5", "6", "1D6");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(inflater.inflate(R.layout.fragment_15trok_adventure_combat, container, false));

        damageSpinner = getRootView().findViewById(R.id.damageSpinner);
        damageText = getRootView().findViewById(R.id.damageText);

        if (damageSpinner != null) {
            DropdownStringAdapter adapter = new DropdownStringAdapter(getActivity(), android.R.layout.simple_list_item_1, damageList);
            damageSpinner.setAdapter(adapter);
        }

        if (overrideDamage != null) {
            damageSpinner.setSelection(damageList.indexOf(overrideDamage));
        } else {
            damageSpinner.setSelection(0);
        }

        damageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                overrideDamage = damageList.get(arg2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                overrideDamage = null;

            }
        });

        init();

        return getRootView();
    }

    protected void combatTurn() {
        if (getCombatPositions().size() == 0)
            return;

        if (getCombatStarted() == false) {
            setCombatStarted(true);
            getCombatTypeSwitch().setClickable(false);
        }
        if (getCombatMode().equals(TROK15_GUNFIGHT)) {
            gunfightCombatTurn();
        } else {
            standardCombatTurn();
        }

    }

    @Override
    protected Function0<Integer> getDamage() {
        if (getCombatMode().equals(Companion.getNORMAL())) {
            return () -> 2;
        } else if (getCombatMode().equals(TROK15_GUNFIGHT)) {
            return () -> Companion.convertDamageStringToInteger(overrideDamage);
        }

        return () -> 2;
    }

    protected String combatTypeSwitchBehaviour(boolean isChecked) {
        setCombatMode(isChecked ? TROK15_GUNFIGHT : Companion.getNORMAL());
        return getCombatMode();
    }

    public String getOntext() {
        return getString(R.string.gunfight);
    }

    @Override
    protected void resetCombat(boolean clearResults) {
        super.resetCombat(clearResults);
    }

    protected void switchLayoutCombatStarted() {

        damageSpinner.setVisibility(View.GONE);
        damageText.setVisibility(View.GONE);

        super.switchLayoutCombatStarted();
    }

    protected void switchLayoutReset(boolean clearResult) {
        damageSpinner.setVisibility(View.VISIBLE);
        damageText.setVisibility(View.VISIBLE);

        super.switchLayoutReset(clearResult);
    }

    protected int getKnockoutStamina() {
        return -1;
    }

    protected void addCombatButtonOnClick() {

        Adventure adv = (Adventure) getActivity();

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (getCombatStarted())
            return;


        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        final View addCombatantView = adv.getLayoutInflater().inflate(
                getCombatMode() == null || getCombatMode().equals(Companion.getNORMAL()) ? R.layout.component_add_combatant : R.layout.component_add_combatant_damage, null);

        final EditText damageValue = addCombatantView.findViewById(R.id.enemyDamage);
        if (damageValue != null) {
            damageValue.setText(getDefaultEnemyDamage());
            damageValue.setRawInputType(Configuration.KEYBOARD_12KEY);
        }

        builder.setTitle(R.string.addEnemy).setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(), 0);

                EditText enemySkillValue = addCombatantView.findViewById(R.id.enemySkillValue);
                EditText enemyStaminaValue = addCombatantView.findViewById(R.id.enemyStaminaValue);
                EditText handicapValue = addCombatantView.findViewById(R.id.handicapValue);

                Integer skill = Integer.valueOf(enemySkillValue.getText().toString());
                Integer stamina = Integer.valueOf(enemyStaminaValue.getText().toString());
                Integer handicap = Integer.valueOf(handicapValue.getText().toString());

                addCombatant(skill, stamina, handicap, damageValue == null ? getDefaultEnemyDamage() : damageValue.getText().toString());

            }

        });

        AlertDialog alert = builder.create();

        EditText skillValue = addCombatantView.findViewById(R.id.enemySkillValue);

        alert.setView(addCombatantView);

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        skillValue.requestFocus();

        alert.show();
    }

    @Override
    protected void startCombat() {
        if (getCombatMode().equals(TROK15_GUNFIGHT)) {
            for (int i = 0; i < getCombatPositions().size(); i++) {
                Combatant c = getCombatPositions().get(i);
                if (c != null)
                    c.setDamage("4");
            }
        }
        super.startCombat();
    }

    protected String getDefaultEnemyDamage() {
        return "4";
    }

    protected void gunfightCombatTurn() {

        Combatant position = getCurrentEnemy();
        Adventure adv = (Adventure) getActivity();

        // if (!finishedCombats.contains(currentCombat)) {
        setDraw(false);
        setLuckTest(false);
        setHit(false);
        DiceRoll diceRoll = DiceRoller.roll2D6();
        int skill = adv.getCombatSkillValue();
        boolean hitEnemy = diceRoll.getSum() <= skill;
        if (hitEnemy) {
            int damage = getDamage().invoke();
            position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
            setHit(true);
            getCombatResult().setText(getString(R.string.hitEnemyDamage, damage));
        } else {
            setDraw(true);
            getCombatResult().setText(R.string.missedTheEnemy);
        }

        if (position.getCurrentStamina() == 0) {
            removeAndAdvanceCombat(position);
            getCombatResult().setText(getCombatResult().getText() + "\n" + getString(R.string.defeatedEnemy));

        }

        for (int i = 0; i < getCombatPositions().size(); i++) {
            Combatant enemy = getCombatPositions().get(i);
            if (enemy != null && enemy.getCurrentStamina() > 0) {
                if (DiceRoller.roll2D6().getSum() <= enemy.getCurrentSkill()) {
                    int damage = Companion.convertDamageStringToInteger(enemy.getDamage());
                    getCombatResult().setText(getCombatResult().getText() + "\n" + getString(R.string.saCombatText2, enemy.getCurrentSkill(), enemy.getCurrentStamina(), damage));
                    adv.setCurrentStamina(Math.max(0, adv.getCurrentStamina() - damage));
                } else {
                    getCombatResult().setText(getCombatResult().getText() + "\n" + getString(R.string.saCombatText3, enemy.getCurrentSkill(), enemy.getCurrentStamina()));
                }
            }
        }

        if (adv.getCurrentStamina() <= 0) {
            getCombatResult().setText(R.string.youveDied);
        }

        refreshScreensFromResume();
    }

    @Override
    public void refreshScreensFromResume() {
        getCombatantListAdapter().notifyDataSetChanged();
        getCombatTypeSwitch().setEnabled(getCombatPositions().isEmpty());
    }

}
