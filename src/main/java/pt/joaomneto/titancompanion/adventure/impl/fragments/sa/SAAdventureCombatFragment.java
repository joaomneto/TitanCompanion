package pt.joaomneto.titancompanion.adventure.impl.fragments.sa;

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
import android.widget.Spinner;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.SAAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll;
import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.TranslatableEnumAdapter;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeapon;
import pt.joaomneto.titancompanion.util.DiceRoller;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SAAdventureCombatFragment extends AdventureCombatFragment {

    public static final String SA12_GUNFIGHT = "SA12_GUNFIGHT";

    Button grenadeButton = null;

    boolean assaultBlaster = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(inflater.inflate(R.layout.fragment_12sa_adventure_combat, container, false));

        grenadeButton = getRootView().findViewById(R.id.grenadeButton);

        grenadeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SAAdventure adv = (SAAdventure) getActivity();

                int idx = adv.getWeapons().indexOf(getString(R.string.saGrenade));

                if (idx < 0)
                    return;
                else {
                    adv.getWeapons().remove(idx);
                    adv.getFragment(SAAdventureWeaponsFragment.class).refreshScreensFromResume();
                }

                String result = "";

                for (Combatant enemy : getCombatPositions()) {

                    if (enemy != null && enemy.getCurrentStamina() > 0) {
                        int damage = DiceRoller.rollD6();
                        enemy.setCurrentStamina(Math.max(enemy.getCurrentStamina() - damage, 0));
                        String text = getString(R.string.saCombatText1, enemy.getCurrentSkill(), enemy.getCurrentStamina(), damage);
                        if (result.isEmpty()) {
                            result = text;
                        } else {
                            result += "\n" + text;
                        }

                        if (enemy.getCurrentStamina() == 0) {
                            getCombatPositions().remove(enemy);

                        }
                        getCombatResult().setText(result);
                        refreshScreensFromResume();
                    }

                }

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

        if (getCombatMode().equals(SA12_GUNFIGHT)) {
            gunfightCombatTurn();
        } else {
            standardCombatTurn();
        }
    }

    protected void gunfightCombatTurn() {

        Combatant position = getCurrentEnemy();
        SAAdventure adv = (SAAdventure) getActivity();

        assaultBlaster = adv.getWeapons().contains(SAWeapon.ASSAULT_BLASTER);

        setDraw(false);
        setLuckTest(false);
        setHit(false);
        DiceRoll diceRoll = DiceRoller.roll2D6();
        int skill = adv.getCurrentSkill();
        boolean hitEnemy = diceRoll.getSum() <= skill;

        if (hitEnemy) {
            int damage = assaultBlaster ? DiceRoller.rollD6() : 2;
            position.setCurrentStamina(Math.max(0, position.getCurrentStamina() - damage));
            setHit(true);
            getCombatResult().setText(getString(R.string.saHitEnemy, damage));
        } else {
            setDraw(true);
            getCombatResult().setText(R.string.missedTheEnemy);
        }

        if (position.getCurrentStamina() == 0) {
            getCombatPositions().remove(position);
            getCombatResult().setText(getCombatResult().getText() + "\n" + getString(R.string.defeatedEnemy));

        }

        for (Combatant enemy : getCombatPositions()) {
            if (enemy != null && enemy.getCurrentStamina() > 0) {
                if (DiceRoller.roll2D6().getSum() <= enemy.getCurrentSkill()) {
                    int damage = enemy.getDamage().equals("2") ? 2 : DiceRoller.rollD6();
                    getCombatResult().setText(getCombatResult().getText() + "\n" + getString(R.string.saCombatText2, enemy.getCurrentSkill(), enemy.getCurrentStamina(), damage));
                    adv.setCurrentStamina(Math.max(0, adv.getCurrentStamina() - damage));
                } else {
                    getCombatResult().setText(getCombatResult().getText() + getString(R.string.saCombatText3, enemy.getCurrentSkill(), enemy.getCurrentStamina()));
                }
            }
        }

        if (adv.getCurrentStamina() <= 0) {
            getCombatResult().setText(R.string.youveDied);
        }

        if (getCombatPositions().size() == 0) {
            resetCombat(false);
        }

        refreshScreensFromResume();
    }

    protected void addCombatButtonOnClick() {

        Adventure adv = (Adventure) getActivity();

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (getCombatStarted())
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        final View addCombatantView = adv.getLayoutInflater().inflate(
                getCombatMode() == null || getCombatMode().equals(Companion.getNORMAL()) ? R.layout.component_add_combatant : R.layout.component_12sa_add_combatant, null);

        SAWeapon[] allWeapons = {SAWeapon.ELECTRIC_LASH, SAWeapon.ASSAULT_BLASTER};

        final Spinner weaponSpinner = addCombatantView.findViewById(R.id.weaponSpinner);
        if (weaponSpinner != null) {
            TranslatableEnumAdapter adapter = new TranslatableEnumAdapter(adv, android.R.layout.simple_list_item_1, allWeapons);
            weaponSpinner.setAdapter(adapter);
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

                addCombatant(skill, stamina, handicap,
                        weaponSpinner == null ? "2" : weaponSpinner.getSelectedItem().equals(SAWeapon.ASSAULT_BLASTER) ? "1d6" : "2");

            }

        });

        AlertDialog alert = builder.create();

        EditText skillValue = addCombatantView.findViewById(R.id.enemySkillValue);

        alert.setView(addCombatantView);

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        skillValue.requestFocus();

        alert.show();
    }

    protected String combatTypeSwitchBehaviour(boolean isChecked) {
        getCombatPositions().clear();
        refreshScreensFromResume();
        setCombatMode(isChecked ? SA12_GUNFIGHT : Companion.getNORMAL());
        return getCombatMode();
    }

    protected void switchLayoutCombatStarted() {
        grenadeButton.setVisibility(getCombatMode().equals(SA12_GUNFIGHT) ? VISIBLE : GONE);
        super.switchLayoutCombatStarted();

    }

    protected void switchLayoutReset(boolean clearResult) {
        grenadeButton.setVisibility(GONE);
        super.switchLayoutReset(clearResult);
    }

    public String getOntext() {
        return getString(R.string.saWeapons);
    }

    @Override
    public void refreshScreensFromResume() {
        super.refreshScreensFromResume();
        grenadeButton.setEnabled(((SAAdventure) getContext()).getWeapons().contains(SAWeapon.GRENADE));
    }
}
