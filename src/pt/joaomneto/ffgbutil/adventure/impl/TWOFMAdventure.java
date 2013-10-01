package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

import pt.joaomneto.ffgbutil.LoadAdventureActivity;
import pt.joaomneto.ffgbutil.R;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class TWOFMAdventure extends Adventure {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_twofm_adventure);

			// Create the adapter that will return a fragment for each of the three
			// primary sections of the app.
			mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			
			String fileName = getIntent().getStringExtra(LoadAdventureActivity.ADVENTURE_FILE);
			
			File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/ffgbutil/");
			
			Properties savedGame = new Properties();
			savedGame.load(new FileInputStream(new File(dir, fileName)));
			
			initialSkill = Integer.valueOf(savedGame.getProperty("initialSkill"));
			initialLuck = Integer.valueOf(savedGame.getProperty("initialLuck"));
			initialStamina = Integer.valueOf(savedGame.getProperty("initialStamina"));
			currentSkill = Integer.valueOf(savedGame.getProperty("currentSkill"));
			currentLuck = Integer.valueOf(savedGame.getProperty("currentLuck"));
			currentStamina = Integer.valueOf(savedGame.getProperty("currentStamina"));
			standardPotion = Integer.valueOf(savedGame.getProperty("standardPotion"));
			standardPotionValue = Integer.valueOf(savedGame.getProperty("standardPotionValue"));
			gold = Integer.valueOf(savedGame.getProperty("gold"));
			provisions = Integer.valueOf(savedGame.getProperty("provisions"));
			String equipmentS = savedGame.getProperty("equipment");
			String notesS = savedGame.getProperty("notes");
			currentReference = Integer.valueOf(savedGame.getProperty("currentReference"));
			
			if(equipmentS!=null){
				equipment = Arrays.asList(equipmentS.split("#"));
			}

			if(notesS!=null){
				notes = Arrays.asList(notesS.split("#"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.twofmadventure, menu);
		return true;
	}

	
	

}
