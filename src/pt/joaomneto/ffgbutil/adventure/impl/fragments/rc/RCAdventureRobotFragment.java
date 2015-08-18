package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.RCAdventure;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class RCAdventureRobotFragment extends AdventureFragment {

	protected static Integer[] gridRows;

	static {
		gridRows = new Integer[] { R.id.robot0, R.id.robot1, R.id.robot2,
				R.id.robot3, R.id.robot4, R.id.robot5 };

	}

	protected Button addRobotButton = null;
	protected View rootView = null;
	protected int currentRobot;
	protected SparseArray<Robot> robotPositions = new SparseArray<RCAdventureRobotFragment.Robot>();
	protected int row = 0;
	protected int maxRows = 6;

	public RCAdventureRobotFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_22rc_adventure_robots,
				container, false);

		init();

		return rootView;
	}

	protected void init() {
		addRobotButton = (Button) rootView.findViewById(R.id.addRobotButton);

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
		robotPositions.remove(position);
	}

	protected void removeCombatant(LinearLayout row) {
		removeCombatant(row, currentRobot);
	}

	protected void addRobotButtonOnClick() {
		addRobotButtonOnClick(R.layout.component_22rc_add_robot);
	}

	protected void addRobotButtonOnClick(int layoutId) {

		final RCAdventure adv = (RCAdventure) getActivity();

		final View addRobotView = adv.getLayoutInflater().inflate(
				R.layout.component_22rc_add_robot, null);

		final InputMethodManager mgr = (InputMethodManager) adv
				.getSystemService(Context.INPUT_METHOD_SERVICE);

        CheckBox alternateForm = (CheckBox) addRobotView
                .findViewById(R.id.alternateFormValue);

        final EditText armorAltValue = (EditText) addRobotView
                .findViewById(R.id.armorAltValue);
        final EditText combatBonusAltValue = (EditText) addRobotView
                .findViewById(R.id.bonusAltValue);
        final Spinner speedAltValue = (Spinner) addRobotView
                .findViewById(R.id.speedAltValue);

        alternateForm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    armorAltValue.setVisibility(View.VISIBLE);
                    combatBonusAltValue.setVisibility(View.VISIBLE);
                    speedAltValue.setVisibility(View.VISIBLE);
                }else{
                    armorAltValue.setVisibility(View.GONE);
                    combatBonusAltValue.setVisibility(View.GONE);
                    speedAltValue.setVisibility(View.GONE);
                }
            }
        });

		if (row >= maxRows) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		builder.setTitle("Add Robot")
				.setCancelable(false)
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mgr.hideSoftInputFromWindow(
										addRobotView.getWindowToken(), 0);
								dialog.cancel();
							}
						});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				int currentRow = getNextRow();

				mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);

				EditText nameValue = (EditText) addRobotView
						.findViewById(R.id.nameValue);
				EditText armorValue = (EditText) addRobotView
						.findViewById(R.id.armorValue);
				EditText combatBonusValue = (EditText) addRobotView
						.findViewById(R.id.bonusValue);
				Spinner speedValue = (Spinner) addRobotView
						.findViewById(R.id.speedValue);
				EditText specialAbilityValue = (EditText) addRobotView
						.findViewById(R.id.specialAbilityValue);
				CheckBox alternateForm = (CheckBox) addRobotView
						.findViewById(R.id.alternateFormValue);




				String armor = armorValue.getText().toString();
				String bonus = combatBonusValue.getText().toString();
				String specialAbility = specialAbilityValue.getText()
						.toString();
				String armorAlt = armorAltValue.getText().toString();
				String bonusAlt = combatBonusAltValue.getText().toString();
				String name = nameValue.getText().toString();

				boolean valid = name.length() > 0
						&& armor.length() > 0
						&& bonus.length() > 0
						&& (!alternateForm.isChecked() || (alternateForm
								.isChecked() && armorAlt.length() > 0 && bonusAlt
								.length() > 0));
				if (valid) {
					if (alternateForm.isChecked())
						addRobot(rootView, currentRow, name,
								Integer.parseInt(armor),
								Integer.parseInt(bonus),
								(String) speedValue.getSelectedItem(),
								Integer.parseInt(specialAbility),
								Integer.parseInt(armorAlt),
								Integer.parseInt(bonusAlt),
								(String) speedAltValue.getSelectedItem());
					else
						addRobot(rootView, currentRow, name,
								Integer.parseInt(armor),
								Integer.parseInt(bonus),
								(String) speedValue.getSelectedItem(),
								Integer.parseInt(specialAbility));
				} else {
					adv.showAlert("At least the name, armor and bonus values must be filled.");
				}

			}

		});

		AlertDialog alert = builder.create();

		Spinner speed = (Spinner) addRobotView
				.findViewById(R.id.speedValue);

		alert.setView(addRobotView);

		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
		speed.requestFocus();

		alert.show();
	}

	protected void addRobot(final View rootView, int currentRow, String name,
			Integer armor, Integer bonus, String speed, Integer specialAbility) {
		
		addRobot(rootView, currentRow, name, armor, bonus, speed, specialAbility, null, null, null);
	}
		protected void addRobot(final View rootView, int currentRow, String name,
				Integer armor, Integer bonus, String speed, Integer specialAbility,
				Integer armorAlt, Integer bonusAlt, String speedAlt) {
		Adventure adv = (Adventure) getActivity();

		final View robotView = adv.getLayoutInflater().inflate(
				R.layout.component_22rc_robot, null);

		final TextView robotTextName = (TextView) robotView.getRootView()
				.findViewById(R.id.robotTextNameValue);
		final TextView robotTextArmor = (TextView) robotView.getRootView()
				.findViewById(R.id.robotTextArmorValue);
		final TextView robotTextSpeed = (TextView) robotView.getRootView()
				.findViewById(R.id.robotTextSpeedValue);
		final TextView robotTextBonus = (TextView) robotView.getRootView()
				.findViewById(R.id.robotTextBonusValue);
		final TextView robotTextLocation = (TextView) robotView.getRootView()
				.findViewById(R.id.robotTextLocationValue);
		final TextView robotTextSpecialAbility = (TextView) robotView
				.getRootView().findViewById(R.id.robotTextSpecialAbilityValue);

		if (robotPositions.size() == 0) {

			RadioButton radio = (RadioButton) robotView.getRootView()
					.findViewById(R.id.robotSelected);
			radio.setChecked(true);
		}

		LinearLayout grid = (LinearLayout) rootView
				.findViewById(gridRows[currentRow]);

		final Robot robotPosition = new Robot(
				name,
				armor,
				speed,
				bonus,
				RobotSpecialAbility.getAbiliyByReference(specialAbility));

		robotPositions.put(currentRow, robotPosition);

		robotTextArmor.setText("" + robotPosition.getArmor());
		robotTextSpeed.setText("" + robotPosition.getSpeed());
		robotTextBonus.setText("" + robotPosition.getBonus());

		Button minusCombatArmor = (Button) robotView
				.findViewById(R.id.minusRobotArmorButton);
		Button plusCombatArmor = (Button) robotView
				.findViewById(R.id.plusRobotArmorButton);

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
			RadioButton robotSelected = (RadioButton) ll
					.findViewById(R.id.combatSelected);
			TextView robotTextArmorValue = (TextView) ll
					.findViewById(R.id.robotTextArmorValue);
			TextView robotTextBonusValue = (TextView) ll
					.findViewById(R.id.robotTextBonusValue);
			TextView robotTextSpeedValue = (TextView) ll
					.findViewById(R.id.robotTextSpeedValue);
			TextView robotTextLocationValue = (TextView) ll
					.findViewById(R.id.robotTextLocationValue);

			if (robotSelected != null)
				robotSelected.setChecked(i == currentRobot);

			if (robotTextArmorValue != null)
				robotTextArmorValue.setText(""
						+ robotPositions.get(i).getArmor());
			if (robotTextBonusValue != null)
				robotTextBonusValue.setText(""
						+ robotPositions.get(i).getBonus());
			if (robotTextSpeedValue != null)
				robotTextSpeedValue.setText(""
						+ robotPositions.get(i).getSpeed());
			if (robotTextLocationValue != null)
				robotTextLocationValue.setText(""
						+ robotPositions.get(i).getLocation());
		}
	}

	protected int getNextRow() {
		return row++;
	}

	protected class Robot {

		String name;
		Integer armor;
		String speed;
		Integer bonus;

		String location = "";
		RobotSpecialAbility robotSpecialAbility;

		public Robot(String name, Integer armor, String speed, Integer bonus,
				RobotSpecialAbility robotSpecialAbility) {
			this.name = name;
			this.armor = armor;
			this.speed = speed;
			this.bonus = bonus;
			this.robotSpecialAbility = robotSpecialAbility;
		}

		public CharSequence toGridString() {
			return ("Armor:" + armor + " Speed:" + speed + "\nCombat Bonus: "
					+ bonus + "\nLocation: " + location);
		}

		public Integer getArmor() {
			return armor;
		}

		public void setArmor(Integer armor) {
			this.armor = armor;
		}

		public String getSpeed() {
			return speed;
		}

		public void setSpeed(String speed) {
			this.speed = speed;
		}

		public Integer getBonus() {
			return bonus;
		}

		public void setBonus(Integer bonus) {
			this.bonus = bonus;
		}

		public RobotSpecialAbility getRobotSpecialAbility() {
			return robotSpecialAbility;
		}

		public void setRobotSpecialAbility(
				RobotSpecialAbility robotSpecialAbility) {
			this.robotSpecialAbility = robotSpecialAbility;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
	}

}
