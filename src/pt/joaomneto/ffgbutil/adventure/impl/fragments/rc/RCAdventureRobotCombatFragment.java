package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.RCAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.util.DiceRoll;
import pt.joaomneto.ffgbutil.util.DiceRoller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import static pt.joaomneto.ffgbutil.adventure.impl.RCAdventure.FRAGMENT_ROBOTCOMBAT;

public class RCAdventureRobotCombatFragment extends AdventureFragment {

    protected static Integer[] gridRows;

    protected Button addRobotButton = null;
    protected Button resetRobotCombatButton = null;
    protected Button resetRobotCombatButton2 = null;
    protected Button resetRobotCombatButton3 = null;
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

    protected Button minusArmorButton = null;
    protected Button plusArmorButton = null;
    protected Button minusEnemyArmorButton = null;
    protected Button plusEnemyArmorButton = null;

    protected RelativeLayout combatRobots = null;
    protected RelativeLayout enemyRobotLayout = null;
    protected RelativeLayout enemyRobot2Layout = null;
    protected LinearLayout robotCombatPrepareRow = null;
    protected LinearLayout robotCombatButtonUpperRow = null;
    protected LinearLayout robotCombatButtonUpperRowTransformer = null;

    protected LinearLayout robotCombatButtonLowerRow = null;
    protected LinearLayout robotCombatButtonLowerRowDigger = null;
    protected LinearLayout robotCombatButtonLowerRowSupertank = null;

    protected TextView robotSpecialAbilityValue = null;
    protected TextView enemySpecialAbilityValue = null;
    protected TextView combatResult = null;

    protected Robot enemyRobot = null;
    protected Robot enemyRobot2 = null;
    protected boolean currentRobotLegged = false;

    boolean serpentVIIPermDamage = false;
    boolean enemyRoboTankPermDamage = false;
    boolean parryOnlyNextTurn = false;

    boolean sequentialCombat = false;

    protected View rootView = null;

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

        addRobotButton = (Button) rootView.findViewById(R.id.addEnemyRobotButton);
        combatRobots = (RelativeLayout) rootView.findViewById(R.id.combatRobots);
        enemyRobotLayout = (RelativeLayout) rootView.findViewById(R.id.enemyRobot);
        enemyRobot2Layout = (RelativeLayout) rootView.findViewById(R.id.enemyRobot2);

        nameValue = (TextView) rootView.findViewById(R.id.nameCombatValue);
        armorValue = (TextView) rootView.findViewById(R.id.armorCombatValue);
        bonusValue = (TextView) rootView.findViewById(R.id.bonusCombatValue);
        skillValue = (TextView) rootView.findViewById(R.id.skillCombatValue);

        armorEnemyValue = (TextView) rootView.findViewById(R.id.enemyArmorValue);
        skillEnemyValue = (TextView) rootView.findViewById(R.id.enemySkillValue);

        minusArmorButton = (Button) rootView.findViewById(R.id.minusArmorButton);
        plusArmorButton = (Button) rootView.findViewById(R.id.plusArmorButton);
        minusEnemyArmorButton = (Button) rootView.findViewById(R.id.minusEnemyArmorButton);
        plusEnemyArmorButton = (Button) rootView.findViewById(R.id.plusEnemyArmorButton);
        resetRobotCombatButton = (Button) rootView.findViewById(R.id.resetRobotCombatButton);
        resetRobotCombatButton2 = (Button) rootView.findViewById(R.id.resetRobotCombatButton2);
        resetRobotCombatButton3 = (Button) rootView.findViewById(R.id.resetRobotCombatButton3);
        changeRobotForm = (Button) rootView.findViewById(R.id.changeRobotForm);

        combatResult = (TextView) rootView.findViewById(R.id.combatResult);

        robotCombatTurn = (Button) rootView.findViewById(R.id.robotCombatTurn);
        robotCombatTurn2 = (Button) rootView.findViewById(R.id.robotCombatTurn2);
        robotCombatTurn3 = (Button) rootView.findViewById(R.id.robotCombatTurn3);
        robotCombatTurnShovel = (Button) rootView.findViewById(R.id.robotCombatTurnShovel);
        robotCombatTurnSonicShot = (Button) rootView.findViewById(R.id.robotCombatTurnSonicShot);

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

        minusEnemyArmorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemyRobot.setArmor(Math.max(enemyRobot.getArmor() - 1, 0));
                refreshScreensFromResume();
            }
        });

        plusEnemyArmorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemyRobot.setArmor(enemyRobot.getArmor() + 1);
                refreshScreensFromResume();
            }
        });

        robotCombatPrepareRow = (LinearLayout) rootView.findViewById(R.id.robotCombatPrepareRow);
        robotCombatButtonUpperRow = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonUpperRow);
        robotCombatButtonUpperRowTransformer = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonUpperRowTransformer);
        robotCombatButtonLowerRow = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonLowerRow);
        robotCombatButtonLowerRowDigger = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonLowerRowDigger);
        robotCombatButtonLowerRowSupertank = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonLowerRowSuperTank);

        robotSpecialAbilityValue = (TextView) rootView.findViewById(R.id.robotSpecialAbilityValue);
        enemySpecialAbilityValue = (TextView) rootView.findViewById(R.id.enemySpecialAbilityValue);

        robotSpecialAbilityValue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (adv.getCurrentRobot().getRobotSpecialAbility() != null)
                    Adventure.showAlert(adv.getCurrentRobot().getRobotSpecialAbility().getName(), adv.getCurrentRobot().getRobotSpecialAbility()
                            .getDescription(), adv);
            }
        });

        enemySpecialAbilityValue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (enemyRobot.getRobotSpecialAbility() != null)
                    Adventure.showAlert(enemyRobot.getRobotSpecialAbility().getName(), enemyRobot.getRobotSpecialAbility().getDescription(), adv);
            }
        });

        addRobotButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                addRobotButtonOnClick();
            }

        });

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


        combatResult.setText("Your robot has switched to alternate configuration.");
        changeRobotForm.setEnabled(false);

        RCAdventureRobotFragment rcarf = (RCAdventureRobotFragment) adv.getFragments().get(FRAGMENT_ROBOTCOMBAT);
        rcarf.refreshScreensFromResume();

        refreshScreensFromResume();


    }

    protected void addRobotButtonOnClick() {

        final RCAdventure adv = (RCAdventure) getActivity();

        final View addRobotView = adv.getLayoutInflater().inflate(R.layout.component_22rc_add_enemy_robot, null);

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        final CheckBox secondEnemy = (CheckBox) addRobotView.findViewById(R.id.addEnemyRobot_secondEnemyValue);
        final View secondEnemyLayout = (View) addRobotView.findViewById(R.id.addEnemyRobot_adventureVitalStats2);

        secondEnemy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondEnemy.isChecked()){
                    secondEnemyLayout.setVisibility(View.VISIBLE);
                }else{
                    secondEnemyLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        builder.setTitle("Add Enemy Robot").setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                EditText specialAbilityValue = (EditText) addRobotView.findViewById(R.id.specialAbilityValue);

                if (specialAbilityValue.getText().toString().equals("400")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                    builder.setTitle("Ankylosaurus");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            currentRobotLegged = true;
                            addRobotClickOk(adv, addRobotView, mgr);

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
                            dialog.cancel();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.setMessage("Does your robot have legs?");
                    alert.show();

                } else {
                    addRobotClickOk(adv, addRobotView, mgr);
                }

            }

            private void addRobotClickOk(final RCAdventure adv, final View addRobotView, final InputMethodManager mgr) {
                mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);

                EditText armorValue = (EditText) addRobotView.findViewById(R.id.addEnemyRobot_armorValue);
                CheckBox airborneValue = (CheckBox) addRobotView.findViewById(R.id.addEnemyRobot_airborneValue);
                EditText skillValue = (EditText) addRobotView.findViewById(R.id.addEnemyRobot_skillValue);
                Spinner speedValue = (Spinner) addRobotView.findViewById(R.id.addEnemyRobot_speedValue);
                Spinner typeValue  = (Spinner) addRobotView.findViewById(R.id.addEnemyRobot_typeValue);
                EditText specialAbilityValue = (EditText) addRobotView.findViewById(R.id.addEnemyRobot_specialAbilityValue);

                EditText armorValue2 = (EditText) addRobotView.findViewById(R.id.addEnemyRobot2_armorValue);
                CheckBox airborneValue2 = (CheckBox) addRobotView.findViewById(R.id.addEnemyRobot2_airborneValue);
                EditText skillValue2 = (EditText) addRobotView.findViewById(R.id.addEnemyRobot2_skillValue);
                Spinner speedValue2 = (Spinner) addRobotView.findViewById(R.id.addEnemyRobot2_speedValue);
                Spinner typeValue2  = (Spinner) addRobotView.findViewById(R.id.addEnemyRobot2_typeValue);
                EditText specialAbilityValue2 = (EditText) addRobotView.findViewById(R.id.addEnemyRobot2_specialAbilityValue);

                speedValue.setAdapter(new ArrayAdapter<RobotSpeed>(adv, android.R.layout.simple_spinner_item, RobotSpeed.values()));
                speedValue2.setAdapter(new ArrayAdapter<RobotSpeed>(adv, android.R.layout.simple_spinner_item, RobotSpeed.values()));

                String armor = armorValue.getText().toString();
                String skill = skillValue.getText().toString();
                String specialAbility = specialAbilityValue.getText().toString();
                String type = (String) typeValue.getSelectedItem();


                String armor2 = armorValue2.getText().toString();
                String skill2 = skillValue2.getText().toString();
                String specialAbility2 = specialAbilityValue2.getText().toString();
                String type2 = (String) typeValue2.getSelectedItem();

                boolean valid = armor.length() > 0 && skill.length() > 0 && type.length() > 0 && armor2.length() > 0 && skill2.length() > 0 && type2.length() > 0;

                if (valid) {
                    addRobot(Integer.parseInt(armor), (RobotSpeed) speedValue.getSelectedItem(), specialAbility.length() > 0 ? Integer.parseInt(specialAbility)
                            : null, Integer.parseInt(skill), type, airborneValue.isChecked(), false);
                    if(secondEnemy.isChecked()){
                        addRobot(Integer.parseInt(armor2), (RobotSpeed) speedValue2.getSelectedItem(), specialAbility2.length() > 0 ? Integer.parseInt(specialAbility2)
                                : null, Integer.parseInt(skill2), type, airborneValue2.isChecked(), true);
                    }
                    combatResult.setText("");
                } else {
                    Adventure.showAlert("At least the name, armor and skill values must be filled.", adv);
                }
            }

        });

        AlertDialog alert = builder.create();

        Spinner speed = (Spinner) addRobotView.findViewById(R.id.speedValue);

        alert.setView(addRobotView);

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        speed.requestFocus();

        alert.show();
    }

    protected void addRobot(Integer armor, RobotSpeed speed, Integer specialAbility, Integer skill, String type, boolean airborne, boolean secondEnemy) {

        RobotSpecialAbility abilityByReference = RobotSpecialAbility.getAbiliyByReference(specialAbility);

        Robot robotPosition = new Robot(null, armor, speed, null, abilityByReference);
        robotPosition.setSkill(skill);
        robotPosition.setAirborne(airborne);

        if (type.equals("Dinosaur")) {
            robotPosition.setDinosaur(true);
        }

        if(secondEnemy) {
            setEnemyRobot(robotPosition);
        }else{
            setEnemyRobot2(robotPosition);
        }
        refreshScreensFromResume();
    }

    @Override
    public void refreshScreensFromResume() {
        RCAdventure adv = (RCAdventure) this.getActivity();

        if (adv.getCurrentRobot() != null) {
            combatRobots.setVisibility(View.VISIBLE);
            nameValue.setText("" + adv.getCurrentRobot().getName());
            armorValue.setText("" + adv.getCurrentRobot().getArmor());
            bonusValue.setText("" + adv.getCurrentRobot().getBonus());
            skillValue.setText("" + adv.getCurrentSkill());

            if (adv.getCurrentRobot().getRobotSpecialAbility() != null)
                robotSpecialAbilityValue.setText(adv.getCurrentRobot().getRobotSpecialAbility().getName());

            enemyRobotLayout.setVisibility(enemyRobot == null ? View.INVISIBLE : View.VISIBLE);
            enemyRobot2Layout.setVisibility(enemyRobot2 == null ? View.INVISIBLE : View.VISIBLE);

            robotCombatPrepareRow.setVisibility(enemyRobot != null ? View.GONE : View.VISIBLE);
            robotCombatButtonUpperRow.setVisibility(enemyRobot == null ? View.GONE : View.VISIBLE);
            robotCombatButtonLowerRow.setVisibility(enemyRobot == null ? View.GONE : View.VISIBLE);

            if (adv.getCurrentRobot().getAlternateForm() != null) {

                robotCombatButtonUpperRow.setVisibility(View.GONE);
                robotCombatButtonUpperRowTransformer.setVisibility(enemyRobot == null ? View.GONE : View.VISIBLE);
            }

            if (RobotSpecialAbility.ROBOTANK_SONIC_SHOT.equals(adv.getCurrentRobot().getRobotSpecialAbility())) {

                robotCombatButtonLowerRow.setVisibility(View.GONE);
                robotCombatButtonLowerRowSupertank.setVisibility(enemyRobot == null ? View.GONE : View.VISIBLE);
            }

            if (RobotSpecialAbility.DIGGER_ROBOT_SHOVEL.equals(adv.getCurrentRobot().getRobotSpecialAbility())) {

                robotCombatButtonLowerRow.setVisibility(View.GONE);
                robotCombatButtonLowerRowDigger.setVisibility(enemyRobot == null ? View.GONE : View.VISIBLE);
            }

            if (enemyRobot != null) {
                if (enemyRobot.getRobotSpecialAbility() != null)
                    enemySpecialAbilityValue.setText(enemyRobot.getRobotSpecialAbility().getName());
                armorEnemyValue.setText("" + enemyRobot.getArmor());
                skillEnemyValue.setText("" + enemyRobot.getSkill());
            }

        } else {
            combatRobots.setVisibility(View.GONE);
            robotCombatPrepareRow.setVisibility(View.GONE);
            robotCombatButtonUpperRow.setVisibility(View.GONE);
            robotCombatButtonLowerRow.setVisibility(View.GONE);
        }

    }

    public boolean isSequentialCombat() {
        return sequentialCombat;
    }

    public void setSequentialCombat(boolean sequentialCombat) {
        this.sequentialCombat = sequentialCombat;
    }

    public Robot getEnemyRobot() {
        return enemyRobot;
    }

    public void setEnemyRobot(Robot enemyRobot) {
        this.enemyRobot = enemyRobot;
    }


    public Robot getEnemyRobot2() {
        return enemyRobot2;
    }

    public void setEnemyRobot2(Robot enemyRobot2) {
        this.enemyRobot2 = enemyRobot2;
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
        int enemyCombatScore = enemyRoll.getSum() + adv.getCurrentSkill();

        if (currentRobot.fasterThan(enemyRobot)) {
            playerCombatScore++;
        }

        if (currentRobot.slowerThan(enemyRobot)) {
            enemyCombatScore++;
        }

        if (currentRobot.getRobotSpecialAbility() != null) {
            switch (currentRobot.getRobotSpecialAbility()) {
                case SUPER_COWBOY_ROBOT_SONIC_SCREAM: {
                    if (enemyRobot.isDinosaur()) {
                        combatStatus.append("Super Cowboy using Sonic Scream. ");
                        enemyCombatScore--;
                    }
                    break;
                }
                case WASP_FIGHTER_SPECIAL_ATTACK: {
                    if (playerCombatScore - enemyCombatScore > 4) {
                        combatStatus.append("Wasp Fighter deals 4 DP. ");
                        enemyDamage = 4;
                    }
                    break;
                }
                case TROOPER_XI_HUMAN_SHIELD: {
                    if (playerCombatScore >= 18) {
                        combatStatus.append("Trooper XI uses shield. ");
                        playerDamage = 0;
                    }
                    break;
                }
                case SERPENT_VII_COIL: {
                    if (playerCombatScore >= 16) {
                        combatStatus.append("Serpent VII has coiled around the enemy. ");
                        serpentVIIPermDamage = true;
                    }
                    break;

                }
                case HEDGEHOG_ANTI_AIR: {
                    if (enemyRobot.isAirborne()) {
                        combatStatus.append("Hedgehog attacking airborne target. ");
                        playerCombatScore += 3;
                    }
                    break;
                }
                default:
                    break;
            }
        }

        if (enemyRobot.getRobotSpecialAbility() != null) {
            switch (enemyRobot.getRobotSpecialAbility()) {
                case ENEMY_BATTLEMAN_EXTRA_DAMAGE: {
                    if (enemyCombatScore - playerCombatScore >= 4) {
                        combatStatus.append("Battleman deals an extra DP. ");
                        playerDamage += 1;
                    }
                    break;
                }
                case ENEMY_CRUSHER_DOUBLE_ATTACK: {
                    combatStatus.append("Crusher deals double damage. ");
                    playerDamage = 4;
                    break;
                }
                case ENEMY_WASP_FIGHTER_SPECIAL_ATTACK: {
                    if (enemyCombatScore - playerCombatScore > 4) {
                        combatStatus.append("Wasp Fighter deals 4 DP. ");
                        playerDamage = 4;
                    }
                    break;
                }
                case ENEMY_SUPERTANK_SMALL_WEAPONS: {
                    playerExtraDamage = 1;
                    combatStatus.append("Supertank deals 1 damage. ");
                    break;
                }
                default:
                    break;
            }
        }

        if (serpentVIIPermDamage) {
            combatStatus.append("Serpent VII deals 1 DP. ");
        }

        if (sonicShotAttack) {
            combatStatus.append("Using sonic shot (");
            if (enemyRobot.isDinosaur()) {
                enemyExtraDamage = DiceRoller.roll2D6().getSum();
            } else {
                enemyExtraDamage = DiceRoller.rollD6();
            }
            combatStatus.append(enemyExtraDamage);
            combatStatus.append(" DP)");
        }

        if (shovelAttack) {
            combatStatus.append("Using shovel (6 DP) ");
            playerCombatScore -= 2;
            enemyDamage = 6;
        }

        if (playerExtraDamage > 0) {
            adv.getCurrentRobot().setArmor(Math.max(adv.getCurrentRobot().getArmor() - playerExtraDamage, 0));
            if (adv.getCurrentRobot().getArmor() == 0) {
                combatStatus.append("You've lost your robot! ");
                enemyRobot = null;
                adv.destroyCurrentRobot();
            }
        }

        if (enemyExtraDamage > 0) {
            enemyRobot.setArmor(Math.max(enemyRobot.getArmor() - enemyExtraDamage, 0));
            if (enemyRobot.getArmor() == 0) {
                combatStatus.append("You've defeated the enemy! ");
                enemyRobot = null;
            }
        }

        if (enemyRobot.getArmor() > 0 && adv.getCurrentRobot().getArmor() > 0 && playerCombatScore > enemyCombatScore) {
            if (parryOnlyNextTurn) {
                combatStatus.append("You have parried the enemy attack. ");
                parryOnlyNextTurn = false;
            } else {
                combatStatus.append("You've hit the enemy! (" + enemyDamage + " DP) ");
                enemyRobot.setArmor(Math.max(enemyRobot.getArmor() - enemyDamage, 0));
                if (enemyRobot.getArmor() == 0) {
                    combatStatus.append("You've defeated the enemy! ");
                    enemyRobot = null;
                }
            }
        } else if (playerCombatScore < enemyCombatScore) {
            combatStatus.append("The enemy has hit you! (" + playerDamage + " DP) ");
            adv.getCurrentRobot().setArmor(Math.max(adv.getCurrentRobot().getArmor() - playerDamage, 0));
            if (adv.getCurrentRobot().getArmor() == 0) {
                combatStatus.append("You've lost your robot! ");
                enemyRobot = null;
                adv.destroyCurrentRobot();
            } else {
                if (RobotSpecialAbility.ENEMY_ANKYLOSAURUS_SPECIAL_ATTACK.equals(enemyRobot.getRobotSpecialAbility()) && currentRobotLegged) {
                    combatStatus.append("The Ankylosaurus has knocked you out! ");
                    parryOnlyNextTurn = true;
                }
            }
        } else if (playerCombatScore == enemyCombatScore) {
            combatStatus.append("Both you and the enemy have missed ");
        }

        combatResult.setText(combatStatus.toString());
        changeRobotForm.setEnabled(true);
        refreshScreensFromResume();

    }

    private void resetCombat() {
        enemyRobot = null;
        serpentVIIPermDamage = false;
        enemyRoboTankPermDamage = false;
        parryOnlyNextTurn = false;
        currentRobotLegged = false;

        combatResult.setText("");

        refreshScreensFromResume();
    }
}
