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

public class RCAdventureRobotCombatFragment extends AdventureFragment {

	protected static Integer[] gridRows;

	protected Button addRobotButton = null;
	protected Button resetRobotCombatButton = null;
	protected Button resetRobotCombatButton2 = null;
	protected Button robotCombatTurn = null;

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
	protected LinearLayout robotCombatPrepareRow = null;
	protected LinearLayout robotCombatButtonUpperRow = null;
	protected LinearLayout robotCombatButtonLowerRow = null;

	protected TextView robotSpecialAbilityValue = null;
	protected TextView enemySpecialAbilityValue = null;

	protected Robot enemyRobot = null;

	boolean serpentVIIPermDamage = false;
	boolean enemyRoboTankPermDamage = false;
	boolean parryOnlyNextTurn = false;

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

		robotCombatTurn = (Button) rootView.findViewById(R.id.robotCombatTurn);

		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyRobot = null;
				serpentVIIPermDamage = false;
				enemyRoboTankPermDamage = false;
				parryOnlyNextTurn = false;
				refreshScreensFromResume();
			}
		};

		resetRobotCombatButton.setOnClickListener(onClickListener);
		resetRobotCombatButton2.setOnClickListener(onClickListener);

		robotCombatTurn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				combatTurn();
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
		robotCombatButtonLowerRow = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonLowerRow);

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

	protected void addRobotButtonOnClick() {

		final RCAdventure adv = (RCAdventure) getActivity();

		final View addRobotView = adv.getLayoutInflater().inflate(R.layout.component_22rc_add_enemy_robot, null);

		final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		builder.setTitle("Add Enemy Robot").setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
				dialog.cancel();
			}
		});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);

				EditText armorValue = (EditText) addRobotView.findViewById(R.id.armorValue);
				CheckBox airborneValue = (CheckBox) addRobotView.findViewById(R.id.airborneValue);
				EditText skillValue = (EditText) addRobotView.findViewById(R.id.skillValue);
				Spinner speedValue = (Spinner) addRobotView.findViewById(R.id.speedValue);
				Spinner typeValue = (Spinner) addRobotView.findViewById(R.id.typeValue);
				EditText specialAbilityValue = (EditText) addRobotView.findViewById(R.id.specialAbilityValue);

				speedValue.setAdapter(new ArrayAdapter<RobotSpeed>(adv, android.R.layout.simple_spinner_item, RobotSpeed.values()));

				String armor = armorValue.getText().toString();
				String skill = skillValue.getText().toString();
				String specialAbility = specialAbilityValue.getText().toString();
				String type = (String) typeValue.getSelectedItem();

				boolean valid = armor.length() > 0 && skill.length() > 0 && type.length() > 0;

				if (valid) {
					addRobot(Integer.parseInt(armor), (RobotSpeed) speedValue.getSelectedItem(), specialAbility.length() > 0 ? Integer.parseInt(specialAbility)
							: null, Integer.parseInt(skill), type, airborneValue.isChecked());
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

	protected void addRobot(Integer armor, RobotSpeed speed, Integer specialAbility, Integer skill, String type, boolean airborne) {

		RobotSpecialAbility abilityByReference = RobotSpecialAbility.getAbiliyByReference(specialAbility);

		Robot robotPosition = new Robot(null, armor, speed, null, abilityByReference);
		robotPosition.setSkill(skill);
		robotPosition.setAirborne(airborne);

		if (type.equals("Dinosaur")) {
			robotPosition.setDinosaur(true);
		}

		setEnemyRobot(robotPosition);
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

			robotCombatPrepareRow.setVisibility(enemyRobot != null ? View.GONE : View.VISIBLE);
			robotCombatButtonUpperRow.setVisibility(enemyRobot == null ? View.GONE : View.VISIBLE);
			robotCombatButtonLowerRow.setVisibility(enemyRobot == null ? View.GONE : View.VISIBLE);

			if (enemyRobot != null) {
				if (enemyRobot.getRobotSpecialAbility() != null)
					enemySpecialAbilityValue.setText(enemyRobot.getRobotSpecialAbility().getName());
				armorEnemyValue.setText(enemyRobot.getArmor());
				skillEnemyValue.setText(enemyRobot.getSkill());
			}

		} else {
			combatRobots.setVisibility(View.GONE);
			robotCombatPrepareRow.setVisibility(View.GONE);
			robotCombatButtonUpperRow.setVisibility(View.GONE);
			robotCombatButtonLowerRow.setVisibility(View.GONE);
		}

	}

	public Robot getEnemyRobot() {
		return enemyRobot;
	}

	public void setEnemyRobot(Robot enemyRobot) {
		this.enemyRobot = enemyRobot;
	}

	public void combatTurn() {

		int playerDamage = 2;
		int enemyDamage = 2;

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

		switch (currentRobot.getRobotSpecialAbility()) {
		case SUPER_COWBOY_ROBOT_SONIC_SCREAM: {
			if (enemyRobot.isDinosaur()) {
				enemyCombatScore--;
			}
			break;
		}
		case WASP_FIGHTER_SPECIAL_ATTACK: {
			if (playerCombatScore - enemyCombatScore > 4) {
				enemyDamage = 4;
			}
			break;
		}
		case TROOPER_XI_HUMAN_SHIELD: {
			if (playerCombatScore >= 18) {
				playerDamage = 0;
			}
			break;
		}
		case SERPENT_VII_COIL: {
			if (playerCombatScore >= 16) {
				serpentVIIPermDamage = true;
			}
			break;

		}
		case HEDGEHOG_ANTI_AIR: {
			if (enemyRobot.isAirborne()) {
				playerCombatScore += 3;
			}
			break;
		}
		default:
			break;
		}

		switch (enemyRobot.getRobotSpecialAbility()) {
		case ENEMY_BATTLEMAN_EXTRA_DAMAGE: {
			if (enemyCombatScore - playerCombatScore >= 4) {
				playerDamage +=1;
			}
			break;
		}
		case ENEMY_CRUSHER_DOUBLE_ATTACK: {
			playerDamage = 4;
			break;
		}
		case ENEMY_WASP_FIGHTER_SPECIAL_ATTACK: {
			if (enemyCombatScore - playerCombatScore > 4) {
				playerDamage = 4;
			}
			break;
		}
		default:
			break;
		}
	}
}
