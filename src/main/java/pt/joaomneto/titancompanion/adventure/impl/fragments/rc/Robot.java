package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

public class Robot {

    String name;
    Integer armor;
    RobotSpeed speed;
    Integer bonus;
    boolean active;
    Robot alternateForm;
    Integer skill;
    boolean legged = false;
    boolean dinosaur = false;
    boolean airborne = false;

    String location = "";
    RobotSpecialAbility robotSpecialAbility;

    public Robot(String name, Integer armor, RobotSpeed speed, Integer bonus, RobotSpecialAbility robotSpecialAbility) {
        this.name = name;
        this.armor = armor;
        this.speed = speed;
        this.bonus = bonus;
        this.robotSpecialAbility = robotSpecialAbility;
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

    public void setRobotSpecialAbility(RobotSpecialAbility robotSpecialAbility) {
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

    public Integer getSkill() {
        return skill;
    }

    public void setSkill(Integer skill) {
        this.skill = skill;
    }

    public boolean isLegged() {
        return legged;
    }

    public void setLegged(boolean legged) {
        this.legged = legged;
    }

    public boolean isDinosaur() {
        return dinosaur;
    }

    public void setDinosaur(boolean dinosaur) {
        this.dinosaur = dinosaur;
    }

    public boolean fasterThan(Robot r) {
        return this.getSpeed().gt(r.getSpeed());
    }

    public boolean slowerThan(Robot r) {
        return this.getSpeed().lt(r.getSpeed());
    }

    public boolean isAirborne() {
        return airborne;
    }

    public void setAirborne(boolean airborne) {
        this.airborne = airborne;
    }

    public void addToArmor(int value) {
        setArmor(getArmor() + value);
    }

    public void subtractFromArmor(int value) {
        Integer armor = getArmor();
        setArmor(Math.max(0, (armor - value)));
    }


}