package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.Robot;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.RobotSpecialAbility;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.RobotSpeed;

public class RCAdventure extends TFODAdventure {

	protected Robot currentRobot = null;
	protected List<Robot> robots = new ArrayList<>();

	public List<Robot> getRobots() {
		return robots;
	}

	public void setRobots(List<Robot> robots) {
		this.robots = robots;
	}

	private static final int FRAGMENT_ROBOTS = 1;
	private static final int FRAGMENT_ROBOTCOMBAT = 5;
	private static final int FRAGMENT_COMBAT = 2;
	private static final int FRAGMENT_EQUIPMENT = 3;
	private static final int FRAGMENT_NOTES = 4;

	public RCAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.RCAdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_ROBOTS, new AdventureFragmentRunner(R.string.robots,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.RCAdventureRobotFragment"));
//		fragmentConfiguration.put(FRAGMENT_ROBOTCOMBAT, new AdventureFragmentRunner(R.string.robotFights,
//				"pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.RCAdventureRobotCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureNotesFragment"));
	}

	public Robot getCurrentRobot() {
		return currentRobot;
	}

	public void setCurrentRobot(Robot currentRobot) {
		this.currentRobot = currentRobot;
	}

	@Override
	public void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		super.storeAdventureSpecificValuesInFile(bw);

		String robotsS = "";

		for (Robot r : robots) {
			String name = r.getName();
			String location = r.getLocation() != null ? r.getLocation() : "";
			RobotSpeed speed = r.getSpeed();
			Integer armor = r.getArmor();
			String ability = r.getRobotSpecialAbility() != null ? r.getRobotSpecialAbility().getReference() + "" : "";
			String alternateName = r.getAlternateForm() != null ? r.getAlternateForm().getName() : "";
			robotsS += name + "§" + location + "§" + speed.name() + "§" + armor + "§" + ability + "§" + r.getBonus() + "§" + alternateName + "§" + r.isActive()
					+ "#";
		}
		robotsS = robotsS.substring(0, robotsS.length() - 1);

		bw.write("standardPotion=" + getStandardPotion() + "\n");
		bw.write("robots=" + robotsS + "\n");
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		super.loadAdventureSpecificValuesFromFile();

		String robotsS = new String(getSavedGame().getProperty("robots").getBytes(java.nio.charset.Charset.forName("UTF-8")));

		Map<String, Robot> robotCache = new HashMap<>();
		Map<Robot, String> robotAltCache = new HashMap<>();

		if (robotsS != null) {
			robots = new ArrayList<Robot>();
			List<String> list = Arrays.asList(robotsS.split("#", -1));
			for (String string : list) {
				if (!string.isEmpty()) {
					String[] split = string.split("§", -1);
					String specialAbility = split[4];
					String alternateFormName = split[6];
					boolean active = Boolean.parseBoolean(split[7]);
					Robot robot = new Robot(split[0], Integer.parseInt(split[3]), RobotSpeed.valueOf(split[2]), Integer.parseInt(split[5]),
							RobotSpecialAbility.getAbiliyByReference(specialAbility.isEmpty() ? null : Integer.parseInt(specialAbility)));
					robot.setLocation(split[1]);
					robot.setActive(active);
					currentRobot = robot;
					robotCache.put(robot.getName(), robot);
					if (!alternateFormName.isEmpty())
						robotAltCache.put(robot, alternateFormName);
					robots.add(robot);
				}
			}
		}

		for (Robot r : robots) {
			if (robotAltCache.containsKey(r)) {
				r.setAlternateForm(robotCache.get(robotAltCache.get(r)));
			}
		}

	}

}
