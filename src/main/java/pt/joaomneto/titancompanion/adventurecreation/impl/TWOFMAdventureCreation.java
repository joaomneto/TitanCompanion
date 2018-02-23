package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import android.view.View;

public class TWOFMAdventureCreation extends AdventureCreation {

	protected int potion = -1;
	protected int potionDoses = -1;

	public TWOFMAdventureCreation() {
		super();
		Companion.getFragmentConfiguration().put(0, new AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"));
		Companion.getFragmentConfiguration().put(1, new AdventureFragmentRunner(R.string.title_adventure_creation_potions,
				"pt.joaomneto.titancompanion.adventurecreation.impl.fragments.PotionsFragment"));

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("standardPotion=" + potion + "\n");
		bw.write("standardPotionValue=" + potionDoses + "\n");
		bw.write("provisions=10\n");
		bw.write("provisionsValue=4\n");
		bw.write("gold=0\n");
	}

	@Override
	public String validateCreationSpecificParameters() {
		StringBuilder sb = new StringBuilder();
		if(this.potion < 0){
			sb.append(getString(R.string.potion));
		}
		return  sb.toString();
	}


	@Override
	protected void rollGamebookSpecificStats(View view) {

	}


	public void setPotion(int potion) {
		this.potion = potion;
	}

	public void setPotionDoses(int potionDoses) {
		this.potionDoses = potionDoses;
	}
}
