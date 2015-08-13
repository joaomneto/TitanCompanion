package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;

public class RCAdventureRobotFragment extends AdventureFragment {

	protected Button addRobotButton = null;
	protected View rootView = null;

	protected SparseArray<Robot> robotPositions = new SparseArray<RCAdventureRobotFragment.Robot>();



	protected static Integer[] gridRows;

	static {
		gridRows = new Integer[] { R.id.robot0, R.id.robot1, R.id.robot2, R.id.robot3, R.id.robot4, R.id.robot5 };
	}

	protected int row = 0;
	protected int maxRows = 6;

	public RCAdventureRobotFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_22rc_adventure_robots, container, false);

		init();

		return rootView;
	}




	protected void init() {

		addRobotButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				addRobotButtonOnClick();
			}

		});

		refreshScreensFromResume();
	}



	protected void removeCombatant(LinearLayout row, int position) {
		row.removeAllViews();
		finishedCombats.add(position);
		robotPositions.remove(position);

		for (int i = 0; i < maxRows; i++) {
			Combatant cp = robotPositions.get(i);
			if (cp != null) {
				cp.setDefenseOnly(false);
				break;
			}
		}
		if (robotPositions.size() == 0) {
			resetCombat();
		}
	}

	protected void removeCombatant(LinearLayout row) {
		removeCombatant(row, currentCombat);
	}



	protected void addRobotButtonOnClick() {
		addRobotButtonOnClick(R.layout.component_add_robot);
	}

	protected void addRobotButtonOnClick(int layoutId) {

		Adventure adv = (Adventure) getActivity();

		final View addRobotView = adv.getLayoutInflater().inflate(R.layout.component_add_robot, null);

		final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (row >= maxRows) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		builder.setTitle("Add Robot").setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
				dialog.cancel();
			}
		});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				int currentRow = getNextRow();

				mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);

				EditText armorValue = (EditText) addRobotView.findViewById(R.id.robotArmorValue);
				Spinner speedValue = (Spinner) addRobotView.findViewById(R.id.robotSpeedValue);
				EditText combatBonusValue = (EditText) addRobotView.findViewById(R.id.combatBonusValue);

				Integer armor = Integer.valueOf(armorValue.getText().toString());
				Integer speed = (Integer) speedValue.getSelectedItem();
				String combatBonus = combatBonusValue.getText().toString();

				addRobot(rootView, currentRow, armor, speed, combatBonus);

			}

		});

		AlertDialog alert = builder.create();

		EditText skillValue = (EditText) addRobotView.findViewById(R.id.enemySkillValue);

		alert.setView(addRobotView);

		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		skillValue.requestFocus();

		alert.show();
	}

	protected void addRobot(final View rootView, int currentRow, Integer armor, Integer speed, String combatBonus) {
		Adventure adv = (Adventure) getActivity();

		final View robotView = adv.getLayoutInflater().inflate(R.layout.component_robot, null);

		final TextView robotTextArmor = (TextView) robotView.getRootView().findViewById(R.id.robotTextArmorValue);

		final TextView robotTextSpeed = (TextView) robotView.getRootView().findViewById(R.id.robotTextSpeedValue);

		final TextView robotTextBonus = (TextView) robotView.getRootView().findViewById(R.id.robotTextBonusValue);

		if (robotPositions.size() == 0) {

			RadioButton radio = (RadioButton) robotView.getRootView().findViewById(R.id.robotSelected);
			radio.setChecked(true);
		}

		LinearLayout grid = (LinearLayout) rootView.findViewById(gridRows[currentRow]);

		final Robot robotPosition = new Robot(armor, speed, combatBonus);

		robotPositions.put(currentRow, robotPosition);

		robotTextArmor.setText("" + robotPosition.getArmor());
		robotTextSpeed.setText("" + robotPosition.getSpeed());
		robotTextBonus.setText(robotPosition.getBonus());

		Button minusCombatArmor = (Button) robotView.findViewById(R.id.minusRobotArmorButton);
		Button plusCombatArmor = (Button) robotView.findViewById(R.id.plusRobotArmorButton);

		minusCombatArmor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				robotPosition.setArmor(Math.max(0, robotPosition.getArmor() - 1));
				robotTextArmor.setText("" + robotPosition.getArmor());
			}
		});
		plusCombatArmor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				robotPosition.setArmor(robotPosition.getArmor() + 1);
				robotTextArmor.setText("" + robotPosition.getArmor());
			}
		});


		grid.addView(robotView);
	}

	@Override
	public void refreshScreensFromResume() {
		for (int i = 0; i < maxRows; i++) {
			LinearLayout ll = (LinearLayout) rootView.findViewById(gridRows[i]);
			RadioButton combatSelected = (RadioButton) ll.findViewById(R.id.combatSelected);
			TextView combatStaminaText = (TextView) ll.findViewById(R.id.combatTextStaminaValue);
			TextView combatSkillText = (TextView) ll.findViewById(R.id.combatTextSkillValue);

			if (combatSelected != null)
				combatSelected.setChecked(i == currentCombat);

			if (combatStaminaText != null)
				combatStaminaText.setText("" + robotPositions.get(i).getCurrentStamina());

			if (combatSkillText != null)
				combatSkillText.setText("" + robotPositions.get(i).getCurrentSkill());
		}
	}

	protected int getNextRow() {
		return row++;
	}



	protected class Robot {

		Integer armor;
		Integer speed;
		String bonus;
		String location = "";

		public Robot(Integer armor, Integer speed, String bonus) {
			this.armor = armor;
			this.speed = speed;
			this.bonus = bonus;
		}


		public CharSequence toGridString() {
			return ("Armor:" + armor + " Speed:" + speed + "\nCombat Bonus: "+ bonus +"\nLocation: "+ location);
		}

		public Integer getArmor() {
			return armor;
		}

		public void setArmor(Integer armor) {
			this.armor = armor;
		}

		public Integer getSpeed() {
			return speed;
		}

		public void setSpeed(Integer speed) {
			this.speed = speed;
		}

		public String getBonus() {
			return bonus;
		}

		public void setBonus(String bonus) {
			this.bonus = bonus;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
	}


}
