package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSMartialArt;

import android.view.View;

public class SOTSAdventureCreation extends AdventureCreation {

	SOTSMartialArt skill;

	public SOTSAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
		fragmentConfiguration.put(1, new AdventureFragmentRunner(R.string.title_adventure_creation_skill,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSAdventureCreationSkillFragment"));

	}

	@Override
	protected void rollGamebookSpecificStats(View view) {

	}



	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("honour=3\n");
		bw.write("skill=" + skill.name() + "\n");
		bw.write("provisions=10\n");
		bw.write("provisionsValue=4\n");
		bw.write("gold=0\n");
		if (SOTSMartialArt.KYUJUTSU == skill) {
			bw.write("willowLeafArrows=3\n");
			bw.write("bowelRakerArrows=3\n");
			bw.write("armourPiercerArrows=3\n");
			bw.write("hummingBulbArrows=3\n");
		}
	}


	public SOTSMartialArt getSkill() {
		return skill;
	}

	public void setSkill(SOTSMartialArt skill) {
		this.skill = skill;
	}


	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.skill == null){
			sb.append("Martial Art");
		}
		return  sb.toString();
	}

}
