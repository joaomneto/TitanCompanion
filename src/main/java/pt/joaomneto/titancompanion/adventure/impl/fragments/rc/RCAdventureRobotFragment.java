package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.RCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.adapter.RobotListAdapter;
import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.DropdownStringAdapter;

public class RCAdventureRobotFragment extends AdventureFragment {

    protected static Integer[] gridRows;

    protected ListView robotListView = null;
    protected Button addRobotButton = null;
    protected View rootView = null;

    public RCAdventureRobotFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_22rc_adventure_robots, container, false);

        init();

        registerForContextMenu(robotListView);

        return rootView;
    }

    protected void init() {

        final RCAdventure adv = (RCAdventure) this.getActivity();

        addRobotButton = rootView.findViewById(R.id.addRobotButton);
        robotListView = rootView.findViewById(R.id.robotList);
        robotListView.setAdapter(new RobotListAdapter(adv, adv.getRobots()));

        robotListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                return false;
            }
        });

        registerForContextMenu(robotListView);

        addRobotButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                addRobotButtonOnClick();
            }

        });

        refreshScreensFromResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final RCAdventure adv = (RCAdventure) this.getActivity();

        final Robot robot = adv.getRobots().get(info.position);

        MenuItem delete = menu.add(R.string.remove);
        MenuItem location = menu.add(R.string.setLocation);

        if (robot.getRobotSpecialAbility() != null) {
            MenuItem specialAbility = menu.add(R.string.rcSpecialAbilityDesc);

            specialAbility.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem arg0) {
                    Adventure.Companion.showAlert(robot.getRobotSpecialAbility().getName(), robot.getRobotSpecialAbility()
                            .getDescription(), adv);
                    return true;
                }
            });

        }


        delete.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.rcRemoveRobot).setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int which) {
                        adv.getRobots().remove(robot);
                        if (robot.getAlternateForm() != null) {
                            adv.getRobots().remove(robot.getAlternateForm());
                        }
                        adv.setCurrentRobot(null);
                        adv.fullRefresh();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

        location.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adv);

                alert.setTitle(R.string.setLocation);

                // Set an EditText view to get user input
                final EditText input = new EditText(adv);
                InputMethodManager imm = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                input.requestFocus();
                alert.setView(input);


                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        robot.setLocation(value);
                        ((ArrayAdapter<String>) robotListView.getAdapter()).notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();

                return true;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return false;
    }

    protected void addRobotButtonOnClick() {
        addRobotButtonOnClick(R.layout.component_22rc_add_robot);
    }

    protected void addRobotButtonOnClick(int layoutId) {

        final RCAdventure adv = (RCAdventure) getActivity();

        final View addRobotView = adv.getLayoutInflater().inflate(R.layout.component_22rc_add_robot, null);

        final InputMethodManager mgr = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);

        CheckBox alternateForm = addRobotView.findViewById(R.id.alternateFormValue);

        final RelativeLayout alternateStatsGroup = addRobotView.findViewById(R.id.alternateStatsGroup);

        alternateForm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alternateStatsGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(adv);

        builder.setTitle(R.string.rcAddRobot).setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                mgr.hideSoftInputFromWindow(addRobotView.getWindowToken(), 0);

                EditText nameAltValue = addRobotView.findViewById(R.id.nameAltValue);
                EditText armorAltValue = addRobotView.findViewById(R.id.armorAltValue);
                EditText combatBonusAltValue = addRobotView.findViewById(R.id.bonusAltValue);
                Spinner speedAltValue = addRobotView.findViewById(R.id.speedAltValue);


                EditText nameValue = addRobotView.findViewById(R.id.nameValue);
                EditText armorValue = addRobotView.findViewById(R.id.armorValue);
                EditText combatBonusValue = addRobotView.findViewById(R.id.bonusValue);
                Spinner speedValue = addRobotView.findViewById(R.id.speedValue);
                EditText specialAbilityValue = addRobotView.findViewById(R.id.specialAbilityValue);
                CheckBox alternateForm = addRobotView.findViewById(R.id.alternateFormValue);


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
                        addRobot(name, Integer.parseInt(armor), Integer.parseInt(bonus), RobotSpeed.getSpeedForId((int) speedValue.getSelectedItemId()),
                                specialAbility.length() > 0 ? Integer.parseInt(specialAbility) : null, nameAlt, Integer.parseInt(armorAlt),
                                Integer.parseInt(bonusAlt), RobotSpeed.getSpeedForId((int) speedAltValue.getSelectedItemId()));
                    else
                        addRobot(name, Integer.parseInt(armor), Integer.parseInt(bonus), RobotSpeed.getSpeedForId((int) speedValue.getSelectedItemId()),
                                specialAbility.length() > 0 ? Integer.parseInt(specialAbility) : null);
                } else {
                    Adventure.Companion.showAlert(getString(R.string.rcNameArmorBonusMandatory), adv);
                }

            }

        });

        AlertDialog alert = builder.create();

        Spinner speedValue = addRobotView.findViewById(R.id.speedValue);
        Spinner speedAltValue = addRobotView.findViewById(R.id.speedAltValue);

        DropdownStringAdapter adapter = new DropdownStringAdapter(adv, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.robotSpeeds));

        speedValue.setAdapter(adapter);
        speedAltValue.setAdapter(adapter);

        alert.setView(addRobotView);

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        speedValue.requestFocus();

        alert.show();
    }

    protected void addRobot(String name, Integer armor, Integer bonus, RobotSpeed speed, Integer specialAbility) {

        addRobot(name, armor, bonus, speed, specialAbility, null, null, null, null);
    }

    protected void addRobot(String name, Integer armor, Integer bonus, RobotSpeed speed, Integer specialAbility, String nameAlt, Integer armorAlt,
                            Integer bonusAlt, RobotSpeed speedAlt) {

        RCAdventure adv = (RCAdventure) this.getActivity();

        for (Robot r : adv.getRobots()) {
            r.setActive(false);

        }

        RobotSpecialAbility abilityByReference = RobotSpecialAbility.getAbiliyByReference(specialAbility);

        Robot robotPosition = new Robot(name, armor, speed, bonus,
                (!RobotSpecialAbility.TROOPER_XI_HUMAN_SHIELD.equals(abilityByReference) || (RobotSpecialAbility.TROOPER_XI_HUMAN_SHIELD
                        .equals(abilityByReference) && armor == 12)) ? abilityByReference : null);

        adv.getRobots().add(robotPosition);
        adv.setCurrentRobot(robotPosition);

        if (armorAlt != null) {
            Robot robotPositionAlt = new Robot(nameAlt, armorAlt, speedAlt, bonusAlt,
                    (RobotSpecialAbility.TROOPER_XI_HUMAN_SHIELD.equals(abilityByReference) && armorAlt == 12) ? abilityByReference : null);
            robotPositionAlt.setAlternateForm(robotPosition);
            robotPosition.setAlternateForm(robotPositionAlt);
            adv.getRobots().add(robotPositionAlt);
        }

        adv.getRobots().get(adv.getRobots().size() - 1).setActive(true);
        adv.fullRefresh();
    }

    @Override
    public void refreshScreensFromResume() {
        RCAdventure adv = (RCAdventure) this.getActivity();
        RobotListAdapter adapter = (RobotListAdapter) robotListView.getAdapter();
        adapter.notifyDataSetChanged();

        if (adapter.getCurrentRobot() != null)
            adv.setCurrentRobot(adapter.getCurrentRobot());
    }

}
