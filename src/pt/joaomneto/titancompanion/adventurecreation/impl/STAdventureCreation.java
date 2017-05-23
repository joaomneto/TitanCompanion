package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.st.STCrewAndShipVitalStatisticsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;
import android.view.View;

public class STAdventureCreation extends AdventureCreation {

	private final static int FRAGMENT_ST_SHIPCREW = 1;
	
	private int scienceOfficerSkill = -1;
	private int medicalOfficerSkill = -1;
	private int engineeringOfficerSkill = -1;
	private int securityOfficerSkill = -1;
	private int securityGuard1Skill = -1;
	private int securityGuard2Skill = -1;
	private int shipWeapons = -1;

	private int scienceOfficerStamina = -1;
	private int medicalOfficerStamina = -1;
	private int engineeringOfficerStamina = -1;
	private int securityOfficerStamina = -1;
	private int securityGuard1Stamina = -1;
	private int securityGuard2Stamina = -1;
	private int shipShields = -1;
	
	public STAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
		fragmentConfiguration.put(FRAGMENT_ST_SHIPCREW, new AdventureFragmentRunner(
				R.string.shipCrewStats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.st.STCrewAndShipVitalStatisticsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {


		bw.write("scienceOfficerSkill="+scienceOfficerSkill+"\n");
		bw.write("scienceOfficerStamina="+scienceOfficerStamina+"\n");
		bw.write("medicalOfficerSkill="+medicalOfficerSkill+"\n");
		bw.write("medicalOfficerStamina="+medicalOfficerStamina+"\n");
		bw.write("engineeringOfficerSkill="+engineeringOfficerSkill+"\n");
		bw.write("engineeringOfficerStamina="+engineeringOfficerStamina+"\n");
		bw.write("securityOfficerSkill="+securityOfficerSkill+"\n");
		bw.write("securityOfficerStamina="+securityOfficerStamina+"\n");
		bw.write("securityGuard1Skill="+securityGuard1Skill+"\n");
		bw.write("securityGuard1Stamina="+securityGuard1Stamina+"\n");
		bw.write("securityGuard2Skill="+securityGuard2Skill+"\n");
		bw.write("securityGuard2Stamina="+securityGuard2Stamina+"\n");
		bw.write("shipWeapons="+shipWeapons+"\n");
		bw.write("shipShields="+shipShields+"\n");
		
		bw.write("scienceOfficerInitialSkill="+scienceOfficerSkill+"\n");
		bw.write("scienceOfficerInitialStamina="+scienceOfficerStamina+"\n");
		bw.write("medicalOfficerInitialSkill="+medicalOfficerSkill+"\n");
		bw.write("medicalOfficerInitialStamina="+medicalOfficerStamina+"\n");
		bw.write("engineeringOfficerInitialSkill="+engineeringOfficerSkill+"\n");
		bw.write("engineeringOfficerInitialStamina="+engineeringOfficerStamina+"\n");
		bw.write("securityOfficerInitialSkill="+securityOfficerSkill+"\n");
		bw.write("securityOfficerInitialStamina="+securityOfficerStamina+"\n");
		bw.write("securityGuard1InitialSkill="+securityGuard1Skill+"\n");
		bw.write("securityGuard1InitialStamina="+securityGuard1Stamina+"\n");
		bw.write("securityGuard2InitialSkill="+securityGuard2Skill+"\n");
		bw.write("securityGuard2InitialStamina="+securityGuard2Stamina+"\n");
		bw.write("shipInitialWeapons="+shipWeapons+"\n");
		bw.write("shipInitialShields="+shipShields+"\n");
	}
	
	private STCrewAndShipVitalStatisticsFragment getSTCrewAndShipVitalStatisticsFragment() {
		STCrewAndShipVitalStatisticsFragment stCrewAndShipVitalStatisticsFragment = (STCrewAndShipVitalStatisticsFragment) getFragments().get(FRAGMENT_ST_SHIPCREW);
		return stCrewAndShipVitalStatisticsFragment;
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		scienceOfficerSkill=DiceRoller.rollD6()+6;
		scienceOfficerStamina=DiceRoller.roll2D6().getSum()+12;
		medicalOfficerSkill=DiceRoller.rollD6()+6;
		medicalOfficerStamina=DiceRoller.roll2D6().getSum()+12;
		engineeringOfficerSkill=DiceRoller.rollD6()+6;
		engineeringOfficerStamina=DiceRoller.roll2D6().getSum()+12;
		securityOfficerSkill=DiceRoller.rollD6()+6;
		securityOfficerStamina=DiceRoller.roll2D6().getSum()+12;
		securityGuard1Skill=DiceRoller.rollD6()+6;
		securityGuard1Stamina=DiceRoller.roll2D6().getSum()+12;
		securityGuard2Skill=DiceRoller.rollD6()+6;
		securityGuard2Stamina=DiceRoller.roll2D6().getSum()+12;
		shipWeapons=DiceRoller.rollD6()+6;
		shipShields=DiceRoller.roll2D6().getSum()+12;
		getSTCrewAndShipVitalStatisticsFragment().updateFields();	
	}

	public int getScienceOfficerSkill() {
		return scienceOfficerSkill;
	}

	public void setScienceOfficerSkill(int scienceOfficerSkill) {
		this.scienceOfficerSkill = scienceOfficerSkill;
	}

	public int getMedicalOfficerSkill() {
		return medicalOfficerSkill;
	}

	public void setMedicalOfficerSkill(int medicalOfficerSkill) {
		this.medicalOfficerSkill = medicalOfficerSkill;
	}

	public int getEngineeringOfficerSkill() {
		return engineeringOfficerSkill;
	}

	public void setEngineeringOfficerSkill(int engineeringOfficerSkill) {
		this.engineeringOfficerSkill = engineeringOfficerSkill;
	}

	public int getSecurityOfficerSkill() {
		return securityOfficerSkill;
	}

	public void setSecurityOfficerSkill(int securityOfficerSkill) {
		this.securityOfficerSkill = securityOfficerSkill;
	}

	public int getSecurityGuard1Skill() {
		return securityGuard1Skill;
	}

	public void setSecurityGuard1Skill(int securityGuard1Skill) {
		this.securityGuard1Skill = securityGuard1Skill;
	}

	public int getSecurityGuard2Skill() {
		return securityGuard2Skill;
	}

	public void setSecurityGuard2Skill(int securityGuard2Skill) {
		this.securityGuard2Skill = securityGuard2Skill;
	}

	public int getShipWeapons() {
		return shipWeapons;
	}

	public void setShipWeapons(int shipWeapons) {
		this.shipWeapons = shipWeapons;
	}

	public int getScienceOfficerStamina() {
		return scienceOfficerStamina;
	}

	public void setScienceOfficerStamina(int scienceOfficerStamina) {
		this.scienceOfficerStamina = scienceOfficerStamina;
	}

	public int getMedicalOfficerStamina() {
		return medicalOfficerStamina;
	}

	public void setMedicalOfficerStamina(int medicalOfficerStamina) {
		this.medicalOfficerStamina = medicalOfficerStamina;
	}

	public int getEngineeringOfficerStamina() {
		return engineeringOfficerStamina;
	}

	public void setEngineeringOfficerStamina(int engineeringOfficerStamina) {
		this.engineeringOfficerStamina = engineeringOfficerStamina;
	}

	public int getSecurityOfficerStamina() {
		return securityOfficerStamina;
	}

	public void setSecurityOfficerStamina(int securityOfficerStamina) {
		this.securityOfficerStamina = securityOfficerStamina;
	}

	public int getSecurityGuard1Stamina() {
		return securityGuard1Stamina;
	}

	public void setSecurityGuard1Stamina(int securityGuard1Stamina) {
		this.securityGuard1Stamina = securityGuard1Stamina;
	}

	public int getSecurityGuard2Stamina() {
		return securityGuard2Stamina;
	}

	public void setSecurityGuard2Stamina(int securityGuard2Stamina) {
		this.securityGuard2Stamina = securityGuard2Stamina;
	}

	public int getShipShields() {
		return shipShields;
	}

	public void setShipShields(int shipShields) {
		this.shipShields = shipShields;
	}

	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.shipShields < 0){
			sb.append(getString(R.string.shipCrewStatsMandatory));
		}
		return  sb.toString();
	}
	

}
