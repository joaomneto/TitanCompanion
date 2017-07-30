package pt.joaomneto.titancompanion.adventurecreation.impl;

		import java.io.BufferedWriter;
		import java.io.IOException;

public class COTSAdventureCreation extends BaseAdventureCreation {


	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {
		bw.write("gold=0\n");
	}

}
