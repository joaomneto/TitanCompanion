package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.GamebookSelectionActivity;
import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class TCOCAdventureCreation extends AdventureCreation {

	private int spellValue = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twofm_adventure_creation);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		gamebook = getIntent().getIntExtra(
				GamebookSelectionActivity.GAMEBOOK_ID, -1);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.twofm_creation_pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {
		bw.write("spellValue="+spellValue+"\n");
		bw.write("spells=\n");
		bw.write("gold=0\n");
	}

}
