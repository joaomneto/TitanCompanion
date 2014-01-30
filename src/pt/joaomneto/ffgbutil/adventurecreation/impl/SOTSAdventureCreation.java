package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import android.view.View;

public class SOTSAdventureCreation extends TFODAdventureCreation {

	String skill;

	public static String SOTS20_KYUJUTSTU = "Kyujutsu";
	public static String SOTS20_IAIJUTSU = "Iaijutsu";
	public static String SOTS20_KARUMIJUTSTU = "Karumijutsu";
	public static String SOTS20_NITOKENJUTSU = "Ni-to-Kenjutsu";

	public SOTSAdventureCreation() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, "pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.VitalStatisticsFragment"));
		fragmentConfiguration.put(1, new AdventureFragmentRunner(R.string.title_adventure_creation_superpower,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.PotionsFragment.java"));
		fragmentConfiguration.put(1, new AdventureFragmentRunner(R.string.title_adventure_creation_superpower,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sots.SOTSAdventureCreationSkillFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("honour=3\n");
		bw.write("skill=" + skill + "\n");
		if (skill.equals(SOTS20_KYUJUTSTU)) {
			bw.write("willowLeafArrows=3\n");
			bw.write("bowelRakerArrows=3\n");
			bw.write("armourPiercerArrows=3\n");
			bw.write("hummingBulbArrows=3\n");
		}
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	

}
