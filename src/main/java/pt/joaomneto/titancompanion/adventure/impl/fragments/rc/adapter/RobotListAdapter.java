package pt.joaomneto.titancompanion.adventure.impl.fragments.rc.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.RCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.Robot;

public class RobotListAdapter extends ArrayAdapter<Robot> implements View.OnCreateContextMenuListener {

	private final Context context;
	private final List<Robot> values;
	private RCAdventure adv;

	public RobotListAdapter(Context context, List<Robot> values) {
		super(context, -1, values);
		this.context = context;
		this.values = values;
		this.adv = (RCAdventure) context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View robotView = inflater.inflate(R.layout.component_22rc_robot, parent, false);

		final TextView robotTextName = robotView.getRootView().findViewById(R.id.robotTextNameValue);
		final TextView robotTextArmor = robotView.getRootView().findViewById(R.id.robotTextArmorValue);
		final TextView robotTextSpeed = robotView.getRootView().findViewById(R.id.robotTextSpeedValue);
		final TextView robotTextBonus = robotView.getRootView().findViewById(R.id.robotTextBonusValue);
		final TextView robotTextLocation = robotView.getRootView().findViewById(R.id.robotTextLocationValue);
		final TextView robotTextSpecialAbility = robotView.getRootView().findViewById(R.id.robotTextSpecialAbilityValue);
		// final Button removeRobotButton = (Button)
		// robotView.getRootView().findViewById(R.id.removeRobotButton);

		final Robot robotPosition = values.get(position);

		robotTextName.setText(robotPosition.getName());
		robotTextArmor.setText("" + robotPosition.getArmor());
		robotTextSpeed.setText("" + robotPosition.getSpeed());
		robotTextBonus.setText("" + robotPosition.getBonus());
		robotTextLocation.setText(robotPosition.getLocation());
		if (robotPosition.getRobotSpecialAbility() != null) {
			robotTextSpecialAbility.setText(robotPosition.getRobotSpecialAbility().getName());
		}

		Button minusCombatArmor = robotView.findViewById(R.id.minusRobotArmorButton);
		Button plusCombatArmor = robotView.findViewById(R.id.plusRobotArmorButton);

		RadioButton radio = robotView.getRootView().findViewById(R.id.robotSelected);
		radio.setChecked(robotPosition.isActive());

		if (robotPosition.isActive()) {
			setCurrentRobot(robotPosition);
		}

		final RobotListAdapter adapter = this;


		radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				for (Robot r : values) {
					r.setActive(false);
				}
				robotPosition.setActive(true);
				adapter.notifyDataSetChanged();
				setCurrentRobot(robotPosition);

				adv.fullRefresh();

			}
		});

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

		robotView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				return false;
			}
		});
		
		return robotView;
	}


    public Robot getCurrentRobot() {
		return adv.getCurrentRobot();
	}

	public void setCurrentRobot(Robot currentRobot) {
		adv.setCurrentRobot(currentRobot);
	}

	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
		System.out.println();
	}

}
