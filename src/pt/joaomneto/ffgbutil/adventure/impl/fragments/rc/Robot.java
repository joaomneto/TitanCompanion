package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;
public class Robot {

		String name;
		Integer armor;
		RobotSpeed speed;
		Integer bonus;
		boolean active;
		Robot alternateForm;

		String location = "";
		RobotSpecialAbility robotSpecialAbility;

		public Robot(String name, Integer armor, RobotSpeed speed, Integer bonus,
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

		public RobotSpeed getSpeed() {
			return speed;
		}

		public void setSpeed(RobotSpeed speed) {
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public Robot getAlternateForm() {
			return alternateForm;
		}

		public void setAlternateForm(Robot alternateForm) {
			this.alternateForm = alternateForm;
		}
		
		

	}