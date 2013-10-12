package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;

public class TWOFMAdventureCreation extends AdventureCreation {

	
	
	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {
		bw.write("standardPotion=" + potion + "\n");
		bw.write("standardPotionValue=2\n");
		bw.write("provisions=10\n");
		bw.write("gold=0\n");
	}

}
