package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

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
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.RCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll;
import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.DropdownStringAdapter;
import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.TranslatableEnumAdapter;
import pt.joaomneto.titancompanion.consts.CombatTurnresult;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class RCAdventureRobotCombatFragment extends AdventureFragment {

    private final static int FRAGMENT_ROBOTS = 1;
    private final static int FRAGMENT_ROBOTCOMBAT = 2;

    protected static Integer[] gridRows;

    protected Button addRobotButton = null;
    protected Button resetRobotCombatButton = null;
    protected Button resetRobotCombatButton2 = null;
    protected Button resetRobotCombatButton3 = null;
    protected Button testLuckButton = null;
    protected Button testLuckButton2 = null;
    protected Button robotCombatTurn = null;
    protected Button robotCombatTurn2 = null;
    protected Button robotCombatTurn3 = null;
    protected Button robotCombatTurnShovel = null;
    protected Button robotCombatTurnSonicShot = null;
    protected Button changeRobotForm = null;

    protected TextView nameValue = null;
    protected TextView armorValue = null;
    protected TextView bonusValue = null;
    protected TextView skillValue = null;

    protected TextView armorEnemyValue = null;
    protected TextView skillEnemyValue = null;
    protected TextView armorEnemyValue2 = null;
    protected TextView skillEnemyValue2 = null;


    protected TextView armorEnemy2Value = null;
    protected TextView skillEnemy2Value = null;

    protected Button minusArmorButton = null;
    protected Button plusArmorButton = null;
    protected Button minusEnemyArmorButton = null;
    protected Button plusEnemyArmorButton = null;
    protected Button minusEnemy2ArmorButton = null;
    protected Button plusEnemy2ArmorButton = null;
    protected Button minusEnemyArmorButton2 = null;
    protected Button plusEnemyArmorButton2 = null;

    protected RelativeLayout combatRobots = null;
    protected RelativeLayout enemyRobotLayout = null;
    protected LinearLayout enemyRobotDualLayout = null;
    protected LinearLayout robotCombatPrepareRow = null;
    protected LinearLayout robotCombatButtonUpperRow = null;
    protected LinearLayout robotCombatButtonUpperRowTransformer = null;

    protected LinearLayout robotCombatButtonLowerRow = null;
    protected LinearLayout robotCombatButtonLowerRowDigger = null;
    protected LinearLayout robotCombatButtonLowerRowSupertank = null;

    protected RelativeLayout enemyRobotDual1 = null;
    protected RelativeLayout enemyRobotDual2 = null;


    protected TextView robotSpecialAbilityValue = null;
    protected TextView enemySpecialAbilityValue = null;
    protected TextView combatResult = null;

    /*protected Robot getCurrentEnemy() = null;
    protected Robot enemyRobot2 = null;
    protected Robot currentEnemy = null;*/

    protected Robot[] enemies = new Robot[]{null, null};
    protected int currentEnemy = 0;
    protected boolean currentRobotLegged = false;

    protected RadioButton enemyRobot1Selected = null;
    protected RadioButton enemyRobot2Selected = null;
    protected View rootView = null;
    boolean serpentVIIPermDamage = false;
    boolean enemyRoboTankPermDamage = false;
    boolean parryOnlyNextTurn = false;
    boolean parryOnlyNextTurnCombat = false;
    boolean combatStarted = false;
    boolean simultaneousCombat = false;

    private CombatTurnresult combatTurnResult = null;

    public RCAdventureRobotCombatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_22rc_adventure_robotcombat, container, false);

        init();

        return rootView;
    }

    protected void init() {

        final RCAdventure adv = (RCAdventure) this.getActivity();


        addRobotButton = rootView.findViewById(R.id.addEnemyRobotButton);
        combatRobots = rootView.findViewById(R.id.combatRobots);
        enemyRobotLayout = rootView.findViewById(R.id.enemyRobot);
        enemyRobotDualLayout = rootView.findViewById(R.id.enemyRobotDual);

        nameValue = rootView.findViewById(R.id.nameCombatValue);
        armorValue = rootView.findViewById(R.id.armorCombatValue);
        bonusValue = rootView.findViewById(R.id.bonusCombatValue);
        skillValue = rootView.findViewById(R.id.skillCombatValue);

        armorEnemyValue = rootView.findViewById(R.id.enemyArmorValue);
        skillEnemyValue = rootView.findViewById(R.id.enemySkillValue);
        armorEnemyValue2 = rootView.findViewById(R.id.enemyArmorValue2);
        skillEnemyValue2 = rootView.findViewById(R.id.enemySkillValue2);


        armorEnemy2Value = rootView.findViewById(R.id.enemy2ArmorValue);
        skillEnemy2Value = rootView.findViewById(R.id.enemy2SkillValue);

        minusArmorButton = rootView.findViewById(R.id.minusArmorButton);
        plusArmorButton = rootView.findViewById(R.id.plusArmorButton);
        minusEnemyArmorButton = rootView.findViewById(R.id.minusEnemyArmorButton);
        plusEnemyArmorButton = rootView.findViewById(R.id.plusEnemyArmorButton);
        minusEnemy2ArmorButton = rootView.findViewById(R.id.minusEnemy2ArmorButton);
        plusEnemy2ArmorButton = rootView.findViewById(R.id.plusEnemy2ArmorButton);
        minusEnemyArmorButton2 = rootView.findViewById(R.id.minusEnemyArmorButton2);
        plusEnemyArmorButton2 = rootView.findViewById(R.id.plusEnemyArmorButton2);

        enemyRobot1Selected = rootView.findViewById(R.id.enemyRobot1Selected);
        enemyRobot2Selected = rootView.findViewById(R.id.enemyRobot2Selected);

        resetRobotCombatButton = rootView.findViewById(R.id.resetRobotCombatButton);
        resetRobotCombatButton2 = rootView.findViewById(R.id.resetRobotCombatButton2);
        resetRobotCombatButton3 = rootView.findViewById(R.id.resetRobotCombatButton3);
        changeRobotForm = rootView.findViewById(R.id.changeRobotForm);
        testLuckButton = rootView.findViewById(R.id.useLuck);
        testLuckButton2 = rootView.findViewById(R.id.useLuck2);

        combatResult = rootView.findViewById(R.id.combatResult);

        robotCombatTurn = rootView.findViewById(R.id.robotCombatTurn);
        robotCombatTurn2 = rootView.findViewById(R.id.robotCombatTurn2);
        robotCombatTurn3 = rootView.findViewById(R.id.robotCombatTurn3);
        robotCombatTurnShovel = rootView.findViewById(R.id.robotCombatTurnShovel);
        robotCombatTurnSonicShot = rootView.findViewById(R.id.robotCombatTurnSonicShot);

        robotCombatPrepareRow = rootView.findViewById(R.id.robotCombatPrepareRow);
        robotCombatButtonUpperRow = rootView.findViewById(R.id.robotCombatButtonUpperRow);
        robotCombatButtonUpperRowTransformer = rootView.findViewById(R.id.robotCombatButtonUpperRowTransformer);
        robotCombatButtonLowerRow = rootView.findViewById(R.id.robotCombatButtonLowerRow);
        robotCombatButtonLowerRowDigger = rootView.findViewById(R.id.robotCombatButtonLowerRowDigger);
        robotCombatButtonLowerRowSupertank = rootView.findViewById(R.id.robotCombatButtonLowerRowSuperTank);
        enemyRobotDual1 = rootView.findViewById(R.id.enemyRobotDual1);
        enemyRobotDual2 = rootView.findViewById(R.id.enemyRobotDual2);

        robotSpecialAbilityValue = rootView.findViewById(R.id.robotSpecialAbilityValue);
        enemySpecialAbilityValue = rootView.findViewById(R.id.enemySpecialAbilityValue);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                resetCombat();
            }


        };

        resetRobotCombatButton.setOnClickListener(onClickListener);
        resetRobotCombatButton2.setOnClickListener(onClickListener);
        resetRobotCombatButton3.setOnClickListener(onClickListener);

        OnClickListener combatTurnClickListener = new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                combatTurn();
            }
        };

        robotCombatTurn.setOnClickListener(combatTurnClickListener);
        robotCombatTurn2.setOnClickListener(combatTurnClickListener);
        robotCombatTurn3.setOnClickListener(combatTurnClickListener);

        robotCombatTurnShovel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                combatTurnShovel();
            }
        });

        robotCombatTurnShovel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                combatTurnSonicShot();
            }
        });

        changeRobotForm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRobotForm();
            }
        });

        final Robot robot = adv.getCurrentRobot();

        minusArmorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                robot.setArmor(Math.max(robot.getArmor() - 1, 0));
                refreshScreensFromResume();
            }
        });

        plusArmorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                robot.setArmor(robot.getArmor() + 1);
                refreshScreensFromResume();
            }
        });

        OnClickListener minusEnemyArmorListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemies[0].setArmor(Math.max(enemies[0].getArmor() - 1, 0));
                refreshScreensFromResume();
            }
        };

        OnClickListener plusEnemyArmorListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemies[0].setArmor(enemies[0].getArmor() + 1);
                refreshScreensFromResume();
            }
        };


        minusEnemyArmorButton.setOnClickListener(minusEnemyArmorListener);
        plusEnemyArmorButton.setOnClickListener(plusEnemyArmorListener);
        minusEnemyArmorButton2.setOnClickListener(minusEnemyArmorListener);
        plusEnemyArmorButton2.setOnClickListener(plusEnemyArmorListener);

        minusEnemy2ArmorButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enemies[1].setArmor(Math.max(0, enemies[1].getArmor() - 1));
                refreshScreensFromResume();
            }
        });

        plusEnemy2ArmorButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enemies[1].setArmor(enemies[1].getArmor() + 1);
                refreshScreensFromResume();
            }
        });


        robotSpecialAbilityValue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (adv.getCurrentRobot().getRobotSpecialAbility() != null)
                    Adventure.Companion.showAlert(adv.getCurrentRobot().getRobotSpecialAbility().getName(), adv.getCurrentRobot().getRobotSpecialAbility()
                            .getDescription(), adv);
            }
        });

        enemySpecialAbilityValue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (enemies[0].getRobotSpecialAbility() != null)
                    Adventure.Companion.showAlert(enemies[0].getRobotSpecialAbility().getName(), enemies[0].getRobotSpecialAbility().getDescription(), adv);
            }
        });

        addRobotButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                addRobotButtonOnClick();
            }

        });

        OnClickListener testLuckListener = view -> {

            boolean lucky = adv.testLuckInternal();

            switch (combatTurnResult) {
                case INFLICTED_DAMAGE:
                    if (lucky) {
                        getOtherEnemy().subtractFromArmor(1);
                        combatResult.append(getString(R.string.rcLuckyCombatInflicted));
                    } else {
                        getOtherEnemy().addToArmor(1);
                        combatResult.append(getString(R.string.rcUnluckyCombatInflicted));
                    }
                    break;
                case SUFFERED_DAMAGE:
                    if (lucky) {
                        adv.getCurrentRobot().addToArmor(1);
                        combatResult.append(getString(R.string.rcLuckyCombatSuffered));
                    } else {
                        adv.getCurrentRobot().subtractFromArmor(1);
                        combatResult.append(getString(R.string.rcUnluckyCombatSuffered));
                    }
                    break;
                default:
            }
            refreshScreensFromResume();
        };

        testLuckButton.setOnClickListener(testLuckListener);
        testLuckButton2.setOnClickListener(testLuckListener);

        refreshScreensFromResume();
    }

    protected void changeRobotForm() {
        final RCAdventure adv = (RCAdventure) getActivity();
        Robot alt = adv.getCurrentRobot().getAlternateForm();
        alt.setActive(true);
        adv.getCurrentRobot().setActive(false);
        adv.setCurrentRobot(alt);

        nameValue.setText("" + adv.getCurrentRobot().getName());
        armorValue.setText("" + adv.getCurrentRobot().getArmor());
        bonusValue.setText("" + adv.getCurrentRobot().getBonus());
        if (adv.getCurrentRobot().getRobotSpecialAbility() != null) {
            robotSpecialAbilityValue.setText("" + adv.getCurrentRobot().getRobotSpecialAbility().getName());
        }
        parryOnlyNextTurn = true;


        combatResult.setText(R.string.rcRobotSwitchedConfig);
        changeRobotForm.setEnabled(false);

        RCAdventureRobotFragment rcarf = ((RCAdventure) getActivity()).getFragment(RCAdventureRobotFragment.class);
        rcarf.refreshScreensFromResume();

        refreshScreensFromResume();


    }

    protected void addRobotButtonOnClick() {

        final RCAdventure adv = (RCAdventure) getActivity();

        final View addRobotView = adv.getLayoutInflater().inflate(R.layout.component_22rc_add_enemy_robot, null);

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        final CheckBox secondEnemy = addRobotView.findViewById(R.id.addEnemyRobot_secondEnemyValue);
        final View secondEnemyLayout = addRobotView.findViewById(R.id.addEnemyRobot_adventureVitalStats2);

        secondEnemy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (secondEnemy.isChecked()) {
                    secondEnemyLayout.setVisibility(View.VISIBLE);
                } else {
                    secondEnemyLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        builder.setTitle(R.string.rcAddEnemyRobot).setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                EditText specialAbilityValue = addRobotView.findViewById(R.id.addEnemyRobot_specialAbilityValue);

                if (specialAbilityValue.getText().toString().equals("400")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                    builder.setTitle(R.string.rcAnkylosaurus);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            currentRobotLegged = true;
                            addRobotClickOk(adv, addRobotView, mgr);

                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
                            dialog.cancel();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.setMessage(getString(R.string.rcRobotHaveLegs));
                    alert.show();

                } else {
                    addRobotClickOk(adv, addRobotView, mgr);
                }

            }

            private void addRobotClickOk(final RCAdventure adv, final View addRobotView, final InputMethodManager mgr) {
                mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);

                EditText armorValue = addRobotView.findViewById(R.id.addEnemyRobot_armorValue);
                CheckBox airborneValue = addRobotView.findViewById(R.id.addEnemyRobot_airborneValue);
                EditText skillValue = addRobotView.findViewById(R.id.addEnemyRobot_skillValue);
                Spinner speedValue = addRobotView.findViewById(R.id.addEnemyRobot_speedValue);
                Spinner typeValue = addRobotView.findViewById(R.id.addEnemyRobot_typeValue);
                EditText specialAbilityValue = addRobotView.findViewById(R.id.addEnemyRobot_specialAbilityValue);

                EditText armorValue2 = addRobotView.findViewById(R.id.addEnemyRobot2_armorValue);
                CheckBox airborneValue2 = addRobotView.findViewById(R.id.addEnemyRobot2_airborneValue);
                EditText skillValue2 = addRobotView.findViewById(R.id.addEnemyRobot2_skillValue);
                Spinner speedValue2 = addRobotView.findViewById(R.id.addEnemyRobot2_speedValue);
                Spinner typeValue2 = addRobotView.findViewById(R.id.addEnemyRobot2_typeValue);
                CheckBox simulCombatValue = addRobotView.findViewById(R.id.addEnemyRobot2_simulCombatValue);

                final TranslatableEnumAdapter adapter = new TranslatableEnumAdapter(getContext(), android.R.layout.simple_list_item_1, RobotSpeed.values());
                speedValue.setAdapter(adapter);
                speedValue2.setAdapter(adapter);

                String armor = armorValue.getText().toString();
                String skill = skillValue.getText().toString();
                String specialAbility = specialAbilityValue.getText().toString();
                String type = (String) typeValue.getSelectedItem();


                String armor2 = armorValue2.getText().toString();
                String skill2 = skillValue2.getText().toString();
                String type2 = (String) typeValue2.getSelectedItem();


                boolean valid = armor.length() > 0 && skill.length() > 0 && type.length() > 0;
                if (secondEnemy.isChecked())
                    valid &= armor2.length() > 0 && skill2.length() > 0 && type2.length() > 0;

                if (valid) {
                    addRobot(Integer.parseInt(armor), (RobotSpeed) speedValue.getSelectedItem(), specialAbility.length() > 0 ? Integer.parseInt(specialAbility)
                            : null, Integer.parseInt(skill), type, airborneValue.isChecked(), false);
                    if (secondEnemy.isChecked()) {
                        simultaneousCombat = simulCombatValue.isChecked();
                        addRobot(Integer.parseInt(armor2), (RobotSpeed) speedValue2.getSelectedItem(), null, Integer.parseInt(skill2), type, airborneValue2.isChecked(), true);
                    }
                    combatResult.setText("");
                } else {
                    Adventure.Companion.showAlert(getString(R.string.rcNameArmorSkillMandatory), adv);
                }
            }

        });

        AlertDialog alert = builder.create();


        Spinner speedValue = addRobotView.findViewById(R.id.addEnemyRobot_speedValue);
        Spinner speed2Value = addRobotView.findViewById(R.id.addEnemyRobot2_speedValue);
        Spinner typeValue = addRobotView.findViewById(R.id.addEnemyRobot_typeValue);
        Spinner typeValue2 = addRobotView.findViewById(R.id.addEnemyRobot2_typeValue);

        DropdownStringAdapter adapter = new DropdownStringAdapter(adv, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.robotSpeeds));
        DropdownStringAdapter adapterType = new DropdownStringAdapter(adv, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.robotTypes));

        speedValue.setAdapter(adapter);
        speed2Value.setAdapter(adapter);
        typeValue.setAdapter(adapterType);
        typeValue2.setAdapter(adapterType);

        alert.setView(addRobotView);

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        speedValue.requestFocus();

        alert.show();
    }


    protected void addRobot(Integer armor, RobotSpeed speed, Integer specialAbility, Integer skill, String type, boolean airborne, boolean secondEnemy) {

        RobotSpecialAbility abilityByReference = RobotSpecialAbility.getAbiliyByReference(specialAbility);

        Robot robotPosition = new Robot(null, armor, speed, null, abilityByReference);
        robotPosition.setSkill(skill);
        robotPosition.setAirborne(airborne);

        if (type.equals(getString(R.string.rcDinosaur))) {
            robotPosition.setDinosaur(true);
        }

        if (!secondEnemy) {
            enemies[0] = robotPosition;
        } else {
            enemies[1] = robotPosition;
        }

        currentEnemy = 0;
        enemyRobot1Selected.setChecked(true);

        refreshScreensFromResume();
    }

    @Override
    public void refreshScreensFromResume() {
        RCAdventure adv = (RCAdventure) this.getActivity();

        if (adv.getCurrentRobot() != null) {
            nameValue.setText("" + adv.getCurrentRobot().getName());
            armorValue.setText("" + adv.getCurrentRobot().getArmor());
            bonusValue.setText("" + adv.getCurrentRobot().getBonus());
            skillValue.setText("" + adv.getCurrentSkill());

            if (adv.getCurrentRobot().getRobotSpecialAbility() != null)
                robotSpecialAbilityValue.setText(adv.getCurrentRobot().getRobotSpecialAbility().getName());

            manageEnemyRobotLayouts();

            robotCombatPrepareRow.setVisibility(enemies[0] != null || enemies[1] != null ? View.GONE : View.VISIBLE);
            robotCombatButtonUpperRow.setVisibility(enemies[0] == null && enemies[1] == null ? View.GONE : View.VISIBLE);
            robotCombatButtonLowerRow.setVisibility(enemies[0] == null && enemies[1] == null ? View.GONE : View.VISIBLE);

            if (adv.getCurrentRobot().getAlternateForm() != null) {

                robotCombatButtonUpperRow.setVisibility(View.GONE);
                robotCombatButtonUpperRowTransformer.setVisibility(enemies[0] == null ? View.GONE : View.VISIBLE);
            }

            if (RobotSpecialAbility.ROBOTANK_SONIC_SHOT.equals(adv.getCurrentRobot().getRobotSpecialAbility())) {

                robotCombatButtonLowerRow.setVisibility(View.GONE);
                robotCombatButtonLowerRowSupertank.setVisibility(enemies[0] == null ? View.GONE : View.VISIBLE);
            }

            if (RobotSpecialAbility.DIGGER_ROBOT_SHOVEL.equals(adv.getCurrentRobot().getRobotSpecialAbility())) {

                robotCombatButtonLowerRow.setVisibility(View.GONE);
                robotCombatButtonLowerRowDigger.setVisibility(enemies[0] == null ? View.GONE : View.VISIBLE);
            }

            combatRobots.setVisibility(View.VISIBLE);

        } else {
            combatRobots.setVisibility(View.INVISIBLE);
            robotCombatPrepareRow.setVisibility(View.GONE);
            robotCombatButtonUpperRow.setVisibility(View.GONE);
            robotCombatButtonLowerRow.setVisibility(View.GONE);
        }


    }

    public boolean isSimultaneousCombat() {
        return simultaneousCombat;
    }

    public void setSimultaneousCombat(boolean simultaneousCombat) {
        this.simultaneousCombat = simultaneousCombat;
    }


    public void combatTurn() {
        combatTurn(false, false);
    }

    public void combatTurnSonicShot() {
        combatTurn(false, true);
    }

    public void combatTurnShovel() {
        combatTurn(true, false);
    }

    public void combatTurn(boolean shovelAttack, boolean sonicShotAttack) {

        StringBuilder rolls = new StringBuilder();
        StringBuilder enemyRolls = new StringBuilder();

        combatStarted = true;

        int playerDamage = 2;
        int enemyDamage = 2;
        int enemyExtraDamage = 0;
        int playerExtraDamage = 0;

        StringBuilder combatStatus = new StringBuilder();

        RCAdventure adv = (RCAdventure) getActivity();

        DiceRoll playerRoll = DiceRoller.roll2D6();
        DiceRoll enemyRoll = DiceRoller.roll2D6();

        Robot currentRobot = adv.getCurrentRobot();
        int playerCombatScore = playerRoll.getSum() + adv.getCurrentSkill() + currentRobot.getBonus();
        int enemyCombatScore = enemyRoll.getSum() + getCurrentEnemy().getSkill();


        rolls.append("(" + playerRoll.getSum() + "+" + adv.getCurrentSkill() + "+" + currentRobot.getBonus());
        enemyRolls.append("(" + enemyRoll.getSum() + "+" + getCurrentEnemy().getSkill());

        if (currentRobot.fasterThan(getCurrentEnemy())) {
            playerCombatScore++;
            rolls.append("+1");
        }

        if (currentRobot.slowerThan(getCurrentEnemy())) {
            enemyCombatScore++;
            enemyRolls.append("+1");
        }

        if (currentRobot.getRobotSpecialAbility() != null) {
            switch (currentRobot.getRobotSpecialAbility()) {
                case SUPER_COWBOY_ROBOT_SONIC_SCREAM: {
                    if (getCurrentEnemy().isDinosaur()) {
                        combatStatus.append(getString(R.string.rcSuperCowboySonicScream));
                        enemyCombatScore--;
                        enemyRolls.append("-1");
                    }
                    break;
                }
                case WASP_FIGHTER_SPECIAL_ATTACK: {
                    if (playerCombatScore - enemyCombatScore > 4) {
                        combatStatus.append(getString(R.string.rcWaspFighterSpecial));
                        enemyDamage = 4;
                    }
                    break;
                }
                case TROOPER_XI_HUMAN_SHIELD: {
                    if (playerCombatScore >= 18) {
                        combatStatus.append(getString(R.string.rcTrooperXISpecial));
                        playerDamage = 0;
                    }
                    break;
                }
                case SERPENT_VII_COIL: {
                    if (playerCombatScore >= 16) {
                        combatStatus.append(getString(R.string.rcSerpencVIISpecial));
                        serpentVIIPermDamage = true;
                    }
                    break;

                }
                case HEDGEHOG_ANTI_AIR: {
                    if (getCurrentEnemy().isAirborne()) {
                        combatStatus.append(getString(R.string.rcHedgehogSpecial));
                        playerCombatScore += 3;
                        rolls.append("+3");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        if (getCurrentEnemy().getRobotSpecialAbility() != null) {
            switch (getCurrentEnemy().getRobotSpecialAbility()) {
                case ENEMY_BATTLEMAN_EXTRA_DAMAGE: {
                    if (enemyCombatScore - playerCombatScore >= 4) {
                        combatStatus.append(getString(R.string.rcBattlemanSpecial));
                        playerDamage += 1;
                    }
                    break;
                }
                case ENEMY_CRUSHER_DOUBLE_ATTACK: {
                    combatStatus.append(getString(R.string.rcCrusherSpecial));
                    playerDamage = 4;
                    break;
                }
                case ENEMY_WASP_FIGHTER_SPECIAL_ATTACK: {
                    if (enemyCombatScore - playerCombatScore > 4) {
                        combatStatus.append(getString(R.string.rcWaspSpecial));
                        playerDamage = 4;
                    }
                    break;
                }
                case ENEMY_SUPERTANK_SMALL_WEAPONS: {
                    playerExtraDamage = 1;
                    combatStatus.append(getString(R.string.rcSupertankSpecila));
                    break;
                }
                default:
                    break;
            }
        }

        if (serpentVIIPermDamage) {
            combatStatus.append(getString(R.string.rcSerpentVIISpecial2));
        }

        if (sonicShotAttack) {
            if (getCurrentEnemy().isDinosaur()) {
                enemyExtraDamage = DiceRoller.roll2D6().getSum();
            } else {
                enemyExtraDamage = DiceRoller.rollD6();
            }
            combatStatus.append(getString(R.string.rcSonicShot, enemyExtraDamage));
        }

        if (shovelAttack) {
            combatStatus.append(getString(R.string.rcShovel));
            playerCombatScore -= 2;
            rolls.append("-2");
            enemyDamage = 6;
        }

        if (playerExtraDamage > 0) {
            adv.getCurrentRobot().setArmor(Math.max(adv.getCurrentRobot().getArmor() - playerExtraDamage, 0));
            if (adv.getCurrentRobot().getArmor() == 0) {
                combatStatus.append(getString(R.string.rcLostRobot));
                destroyCurrentEnemy();
                adv.destroyCurrentRobot();
            }
        }

        if (enemyExtraDamage > 0 && !parryOnlyNextTurn) {
            getCurrentEnemy().setArmor(Math.max(getCurrentEnemy().getArmor() - enemyExtraDamage, 0));
            if (getCurrentEnemy().getArmor() == 0) {
                combatStatus.append(getString(R.string.rcDefeatedEnemy));
                destroyCurrentEnemy();
            }
        }

        rolls.append("=" + playerCombatScore + ")");
        enemyRolls.append("=" + enemyCombatScore + ")");

        if (getCurrentEnemy().getArmor() > 0 && adv.getCurrentRobot().getArmor() > 0 && playerCombatScore > enemyCombatScore) {
            if (parryOnlyNextTurn) {
                combatTurnResult = CombatTurnresult.PARRY;
                combatStatus.append(getString(R.string.rcParryAttack));
                parryOnlyNextTurn = false;
            } else if (parryOnlyNextTurnCombat) {
                combatTurnResult = CombatTurnresult.PARRY;
                combatStatus.append(getString(R.string.rcParryAttack));
            } else {
                combatTurnResult = CombatTurnresult.INFLICTED_DAMAGE;
                combatStatus.append(getString(R.string.rcHitEnemy, enemyDamage));
                getCurrentEnemy().setArmor(Math.max(getCurrentEnemy().getArmor() - enemyDamage, 0));
                if (getCurrentEnemy().getArmor() == 0) {
                    combatStatus.append(R.string.rcDefeatedEnemy);
                    destroyCurrentEnemy();
                }


            }
        } else if (playerCombatScore < enemyCombatScore) {
            combatTurnResult = CombatTurnresult.SUFFERED_DAMAGE;
            combatStatus.append(getString(R.string.rcEnemyHitYou, playerDamage));
            adv.getCurrentRobot().setArmor(Math.max(adv.getCurrentRobot().getArmor() - playerDamage, 0));
            if (adv.getCurrentRobot().getArmor() == 0) {
                combatStatus.append(R.string.rcLostRobot);
                adv.destroyCurrentRobot();
                destroyCurrentEnemy();
            } else {
                if (RobotSpecialAbility.ENEMY_ANKYLOSAURUS_SPECIAL_ATTACK.equals(getCurrentEnemy().getRobotSpecialAbility()) && currentRobotLegged) {
                    combatStatus.append(getString(R.string.rcAnkylosaurusKO));
                    parryOnlyNextTurn = true;
                }
            }
        } else if (playerCombatScore == enemyCombatScore) {
            combatTurnResult = CombatTurnresult.BOTH_MISSED;
            combatStatus.append(getString(R.string.rcBothMissed));
        }

        if (simultaneousCombat) {
            if (enemies[0] == null || enemies[1] == null) {
                simultaneousCombat = false;
                parryOnlyNextTurnCombat = false;
            } else {
                currentEnemy = currentEnemy == 0 ? 1 : 0;
                parryOnlyNextTurnCombat = !parryOnlyNextTurnCombat;
            }
        }

        combatStatus.append(" ");
        combatStatus.append(rolls);
        combatStatus.append(" vs ");
        combatStatus.append(enemyRolls);
        combatStatus.append(". ");

        combatResult.setText(combatStatus.toString());
        changeRobotForm.setEnabled(true);
        RCAdventure activity = (RCAdventure) this.getActivity();
        RCAdventureRobotFragment rcarf = activity.getFragment(RCAdventureRobotFragment.class);
        rcarf.refreshScreensFromResume();
        refreshScreensFromResume();

    }

    private void resetCombat() {
        enemies[0] = null;
        enemies[1] = null;
        simultaneousCombat = false;
        serpentVIIPermDamage = false;
        enemyRoboTankPermDamage = false;
        parryOnlyNextTurn = false;
        parryOnlyNextTurnCombat = false;
        currentRobotLegged = false;
        combatStarted = false;

        combatResult.setText("");

        refreshScreensFromResume();
    }

    private void manageEnemyRobotLayouts() {

        if (enemies[0] == null && enemies[1] == null) {
            enemyRobotDualLayout.setVisibility(View.GONE);
            enemyRobotLayout.setVisibility(View.GONE);
        } else if (!combatStarted) {
            if (enemies[1] != null) {
                enemyRobotDualLayout.setVisibility(View.VISIBLE);
                enemyRobotDual1.setVisibility(View.VISIBLE);
                enemyRobotDual2.setVisibility(View.VISIBLE);
                enemyRobotLayout.setVisibility(View.GONE);
            } else {
                enemyRobotDualLayout.setVisibility(View.GONE);
                enemyRobotLayout.setVisibility(View.VISIBLE);
            }
        }

        if (enemies[0] != null) {
            if (enemies[0].getRobotSpecialAbility() != null)
                enemySpecialAbilityValue.setText(enemies[0].getRobotSpecialAbility().getName());
            armorEnemyValue.setText("" + enemies[0].getArmor());
            skillEnemyValue.setText("" + enemies[0].getSkill());
            armorEnemyValue2.setText("" + enemies[0].getArmor());
            skillEnemyValue2.setText("" + enemies[0].getSkill());
        }


        if (enemies[1] != null) {
            armorEnemy2Value.setText("" + enemies[1].getArmor());
            skillEnemy2Value.setText("" + enemies[1].getSkill());
        }

        enemyRobot1Selected.setChecked(getAndModifyCurrentEnemyIndex() == 0);
        enemyRobot2Selected.setChecked(getAndModifyCurrentEnemyIndex() == 1);

    }

    private int getAndModifyCurrentEnemyIndex() {
        if (enemies[0] != null && enemies[1] != null) {
        } else if (enemies[1] != null) {
            currentEnemy = 1;
        } else {
            currentEnemy = 0;
        }

        return currentEnemy;
    }

    private Robot getCurrentEnemy() {
        return enemies[getAndModifyCurrentEnemyIndex()];
    }

    public Robot getOtherEnemy() {
        int current = getAndModifyCurrentEnemyIndex();
        return enemies[enemies.length > 1 ? (current == 0 ? 1 : 0) : 0];
    }


    private void destroyCurrentEnemy() {
        enemies[getAndModifyCurrentEnemyIndex()] = null;

        if (enemies[0] == null && enemies[1] == null) {
            combatStarted = false;
        }

        if (currentEnemy == 0)
            enemyRobotDual1.setVisibility(View.INVISIBLE);
        else
            enemyRobotDual2.setVisibility(View.INVISIBLE);
    }
}
