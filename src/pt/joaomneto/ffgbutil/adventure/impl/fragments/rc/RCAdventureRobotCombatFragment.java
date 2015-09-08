package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.RCAdventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.adapter.RobotListAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class RCAdventureRobotCombatFragment extends AdventureFragment {

	protected static Integer[] gridRows;

	protected Button addRobotButton = null;
	protected Button resetRobotCombatButton = null;
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

	protected boolean combatStarted = false;

	protected Robot enemyRobot = null;

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

		resetRobotCombatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enemyRobot = null;
				refreshScreensFromResume();
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

				EditText nameValue = (EditText) addRobotView.findViewById(R.id.nameValue);
				EditText armorValue = (EditText) addRobotView.findViewById(R.id.armorValue);
				EditText skillValue = (EditText) addRobotView.findViewById(R.id.skillValue);
				Spinner speedValue = (Spinner) addRobotView.findViewById(R.id.speedValue);
				EditText specialAbilityValue = (EditText) addRobotView.findViewById(R.id.specialAbilityValue);

				speedValue.setAdapter(new ArrayAdapter<RobotSpeed>(adv, android.R.layout.simple_spinner_item, RobotSpeed.values()));

				String armor = armorValue.getText().toString();
				String skill = skillValue.getText().toString();
				String specialAbility = specialAbilityValue.getText().toString();

				boolean valid = armor.length() > 0 && skill.length() > 0;

				if (valid) {
					addRobot(Integer.parseInt(armor), (RobotSpeed) speedValue.getSelectedItem(), specialAbility.length() > 0 ? Integer.parseInt(specialAbility)
							: null, Integer.parseInt(skill));
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

	protected void addRobot(Integer armor, RobotSpeed speed, Integer specialAbility, Integer skill) {

		RobotSpecialAbility abilityByReference = RobotSpecialAbility.getAbiliyByReference(specialAbility);

		Robot robotPosition = new Robot(null, armor, speed, null, abilityByReference);
		robotPosition.setSkill(skill);

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

			robotCombatPrepareRow.setVisibility(combatStarted ? View.GONE : View.VISIBLE);
			robotCombatButtonUpperRow.setVisibility(!combatStarted ? View.GONE : View.VISIBLE);
			robotCombatButtonLowerRow.setVisibility(!combatStarted ? View.GONE : View.VISIBLE);

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

}
