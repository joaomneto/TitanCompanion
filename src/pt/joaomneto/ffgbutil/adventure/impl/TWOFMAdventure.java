package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import pt.joaomneto.ffgbutil.LoadAdventureActivity;
import pt.joaomneto.ffgbutil.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

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
	
	private File dir = null;
	private int gamebook = -1;
	private String name = null;

	

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
			String relDir = getIntent().getStringExtra(LoadAdventureActivity.ADVENTURE_DIR);
			name = relDir;
			dir = new File(Environment.getExternalStorageDirectory().getPath()+"/ffgbutil/"+relDir);
			
			Properties savedGame = new Properties();
			savedGame.load(new FileInputStream(new File(dir, fileName)));
			
			gamebook = Integer.valueOf(savedGame.getProperty("gamebook"));
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
				equipment = new ArrayList<String>();
				List<String> list = Arrays.asList(equipmentS.split("#"));
				for (String string : list) {
					if(!string.isEmpty())
						equipment.add(string);
				}
			}

			if(notesS!=null){
				notes = new ArrayList<String>();
				List<String> list = Arrays.asList(notesS.split("#"));
				for (String string : list) {
					if(!string.isEmpty())
						notes.add(string);
				}
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

	public void savepoint(View v){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Current Reference?");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
		input.requestFocus();
		alert.setView(input);

		alert.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						
						try {
							String ref = input.getText().toString();
							File file = new File(dir, ref+".xml");

							BufferedWriter bw = new BufferedWriter(new FileWriter(file));
							
							String equipmentS = "";
							String notesS = "";
							
							if (!notes.isEmpty()) {
								for (String note : notes) {
									notesS += note + "#";
								}
								notesS = notesS.substring(0,
										notesS.length() - 1);
							}
							
							if (!equipment.isEmpty()) {
								for (String eq : equipment) {
									equipmentS += eq + "#";
								}
								equipmentS = equipmentS.substring(0,
										equipmentS.length() - 1);
							}
							bw.write("gamebook=" + gamebook + "\n");
							bw.write("name=" + name + "\n");
							bw.write("initialSkill=" + initialSkill + "\n");
							bw.write("initialLuck=" + initialSkill + "\n");
							bw.write("initialStamina=" + initialStamina + "\n");
							bw.write("currentSkill=" + currentSkill + "\n");
							bw.write("currentLuck=" + currentLuck + "\n");
							bw.write("currentStamina=" + currentStamina + "\n");
							bw.write("standardPotion=" + standardPotion + "\n");
							bw.write("standardPotionValue="+ standardPotionValue + "\n");
							bw.write("provisions=" + provisions + "\n");
							bw.write("gold=" + gold + "\n");
							bw.write("currentReference=" + ref + "\n");
							bw.write("equipment=" + equipmentS + "\n");
							bw.write("notes=" + notesS + "\n");

							bw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}
	

}
