package pt.joaomneto.titancompanion.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import android.view.View;

public class TFODAdventureCreation extends TWOFMAdventureCreation {

		
	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {
		bw.write("standardPotion=" + potion + "\n");
		bw.write("standardPotionValue=1\n");
		bw.write("provisionsValue=4\n");
		bw.write("gold=0\n");
	}

	@Override
	protected void rollGamebookSpecificStats(View view) {
		
	}

}
