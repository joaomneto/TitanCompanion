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
	protected TextView nameValue = null;
	protected TextView armorValue = null;
	protected TextView bonusValue = null;
	protected TextView skillValue = null;
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

		robotCombatPrepareRow = (LinearLayout) rootView.findViewById(R.id.robotCombatPrepareRow);
		robotCombatButtonUpperRow = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonUpperRow);
		robotCombatButtonLowerRow = (LinearLayout) rootView.findViewById(R.id.robotCombatButtonLowerRow);

		robotSpecialAbilityValue = (TextView) rootView.findViewById(R.id.robotSpecialAbilityValue);
		enemySpecialAbilityValue = (TextView) rootView.findViewById(R.id.enemySpecialAbilityValue);

		addRobotButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				addRobotButtonOnClick();
			}

		});

		refreshScreensFromResume();
	}

	protected void addRobotButtonOnClick() {
		addRobotButtonOnClick(R.layout.component_22rc_add_robot);
	}

	protected void addRobotButtonOnClick(int layoutId) {

		final RCAdventure adv = (RCAdventure) getActivity();

		final View addRobotView = adv.getLayoutInflater().inflate(R.layout.component_22rc_add_robot, null);

		final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

		CheckBox alternateForm = (CheckBox) addRobotView.findViewById(R.id.alternateFormValue);

		final RelativeLayout alternateStatsGroup = (RelativeLayout) addRobotView.findViewById(R.id.alternateStatsGroup);

		alternateForm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				alternateStatsGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		builder.setTitle("Add Robot").setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
				dialog.cancel();
			}
		});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);

				EditText nameAltValue = (EditText) addRobotView.findViewById(R.id.nameAltValue);
				EditText armorAltValue = (EditText) addRobotView.findViewById(R.id.armorAltValue);
				EditText combatBonusAltValue = (EditText) addRobotView.findViewById(R.id.bonusAltValue);
				Spinner speedAltValue = (Spinner) addRobotView.findViewById(R.id.speedAltValue);

				speedAltValue.setAdapter(new ArrayAdapter<RobotSpeed>(adv, android.R.layout.simple_spinner_item, RobotSpeed.values()));

				EditText nameValue = (EditText) addRobotView.findViewById(R.id.nameValue);
				EditText armorValue = (EditText) addRobotView.findViewById(R.id.armorValue);
				EditText combatBonusValue = (EditText) addRobotView.findViewById(R.id.bonusValue);
				Spinner speedValue = (Spinner) addRobotView.findViewById(R.id.speedValue);
				EditText specialAbilityValue = (EditText) addRobotView.findViewById(R.id.specialAbilityValue);
				CheckBox alternateForm = (CheckBox) addRobotView.findViewById(R.id.alternateFormValue);

				speedValue.setAdapter(new ArrayAdapter<RobotSpeed>(adv, android.R.layout.simple_spinner_item, RobotSpeed.values()));

				String armor = armorValue.getText().toString();
				String bonus = combatBonusValue.getText().toString();
				String specialAbility = specialAbilityValue.getText().toString();
				String armorAlt = armorAltValue.getText().toString();
				String bonusAlt = combatBonusAltValue.getText().toString();
				String name = nameValue.getText().toString();
				String nameAlt = nameAltValue.getText().toString();

				boolean valid = name.length() > 0 && armor.length() > 0 && bonus.length() > 0
						&& (!alternateForm.isChecked() || (alternateForm.isChecked() && armorAlt.length() > 0 && bonusAlt.length() > 0));
				if (valid) {
					if (alternateForm.isChecked())
						addRobot(name, Integer.parseInt(armor), Integer.parseInt(bonus), (RobotSpeed) speedValue.getSelectedItem(),
								specialAbility.length() > 0 ? Integer.parseInt(specialAbility) : null, nameAlt, Integer.parseInt(armorAlt),
								Integer.parseInt(bonusAlt), (RobotSpeed) speedAltValue.getSelectedItem());
					else
						addRobot(name, Integer.parseInt(armor), Integer.parseInt(bonus), (RobotSpeed) speedValue.getSelectedItem(),
								specialAbility.length() > 0 ? Integer.parseInt(specialAbility) : null);
				} else {
					Adventure.showAlert("At least the name, armor and bonus values must be filled.", adv);
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

	protected void addRobot(String name, Integer armor, Integer bonus, RobotSpeed speed, Integer specialAbility) {

		addRobot(name, armor, bonus, speed, specialAbility, null, null, null, null);
	}

	protected void addRobot(String name, Integer armor, Integer bonus, RobotSpeed speed, Integer specialAbility, String nameAlt, Integer armorAlt,
			Integer bonusAlt, RobotSpeed speedAlt) {

		RobotSpecialAbility abilityByReference = RobotSpecialAbility.getAbiliyByReference(specialAbility);

		Robot robotPosition = new Robot(name, armor, speed, bonus,
				(!RobotSpecialAbility.TROOPER_XI_HUMAN_SHIELD.equals(abilityByReference) || (RobotSpecialAbility.TROOPER_XI_HUMAN_SHIELD
						.equals(abilityByReference) && armor == 12)) ? abilityByReference : null);

		setEnemyRobot(robotPosition);

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
				robotSpecialAbilityValue.setText(adv.getCurrentRobot().getRobotSpecialAbility().getDescription());

			enemyRobotLayout.setVisibility(enemyRobot == null ? View.INVISIBLE : View.VISIBLE);

			robotCombatPrepareRow.setVisibility(combatStarted ? View.GONE : View.VISIBLE);
			robotCombatButtonUpperRow.setVisibility(!combatStarted ? View.GONE : View.VISIBLE);
			robotCombatButtonLowerRow.setVisibility(!combatStarted ? View.GONE : View.VISIBLE);

			if (enemyRobot!=null) {
				if (enemyRobot.getRobotSpecialAbility() != null)
					enemySpecialAbilityValue.setText(enemyRobot.getRobotSpecialAbility().getDescription());
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
