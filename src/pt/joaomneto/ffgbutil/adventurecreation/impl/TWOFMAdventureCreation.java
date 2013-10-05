package pt.joaomneto.ffgbutil.adventurecreation.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import pt.joaomneto.ffgbutil.GamebookSelectionActivity;
import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

public class TWOFMAdventureCreation extends AdventureCreation {

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

	public void saveAdventure(View view) {
		try {
			EditText et = (EditText) findViewById(R.id.adventureNameInput);

			File dir = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/ffgbutil/save_twofm_"
					+ et.getText().toString().replace(' ', '-'));
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(dir, "initial.xml");

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			bw.write("gamebook=" + gamebook + "\n");
			bw.write("name=" + et.getText().toString() + "\n");
			bw.write("initialSkill=" + skill + "\n");
			bw.write("initialLuck=" + luck + "\n");
			bw.write("initialStamina=" + stamina + "\n");
			bw.write("currentSkill=" + skill + "\n");
			bw.write("currentLuck=" + luck + "\n");
			bw.write("currentStamina=" + stamina + "\n");
			bw.write("standardPotion=" + potion + "\n");
			bw.write("standardPotionValue=2\n");
			bw.write("provisions=10\n");
			bw.write("gold=0\n");
			bw.write("currentReference=1\n");
			bw.write("equipment=\n");
			bw.write("notes=\n");

			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
