package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import pt.joaomneto.ffgbutil.LoadAdventureActivity;
import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureProvisionsFragment;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class Adventure extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	StandardSectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	Integer initialSkill = -1;
	Integer initialLuck = -1;
	Integer initialStamina = -1;
	Integer currentSkill = -1;
	Integer currentLuck = -1;
	Integer currentStamina = -1;
	Integer standardPotion = -1;
	Integer gold = -1;
	Integer provisions = -1;
	Integer standardPotionValue = -1;
	List<String> equipment = new ArrayList<String>();
	List<String> notes = new ArrayList<String>();
	Integer currentReference = -1;

	File dir = null;
	int gamebook = -1;
	String name = null;
	Properties savedGame;

	protected static final int FRAGMENT_VITAL_STATS = 0;
	protected static final int FRAGMENT_COMBAT = 1;
	protected static final int FRAGMENT_PROVISIONS = 2;
	protected static final int FRAGMENT_EQUIPMENT = 3;
	protected static final int FRAGMENT_NOTES = 4;

	protected static Map<Integer, AdventureFragmentRunner> fragmentConfiguration = new HashMap<Integer, Adventure.AdventureFragmentRunner>();

	public Adventure() {
		super();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS,
				new AdventureFragmentRunner(R.string.vitalStats,
						"AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(
				R.string.fights, "AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_PROVISIONS,
				new AdventureFragmentRunner(R.string.potionsProvisions,
						"AdventureProvisionsFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT,
				new AdventureFragmentRunner(R.string.goldEquipment,
						"AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(
				R.string.notes, "AdventureNotesFragment"));
	}

	public static class AdventureFragmentRunner {
		int titleId;
		String className;

		public AdventureFragmentRunner(int titleId, String className) {
			super();
			this.titleId = titleId;
			this.className = className;
		}

		public int getTitleId() {
			return titleId;
		}

		public String getClassName() {
			return className;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			// Create the adapter that will return a fragment for each of the
			// three
			// primary sections of the app.
			mSectionsPagerAdapter = new StandardSectionsPagerAdapter(
					getSupportFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);

			String fileName = getIntent().getStringExtra(
					LoadAdventureActivity.ADVENTURE_FILE);
			String relDir = getIntent().getStringExtra(
					LoadAdventureActivity.ADVENTURE_DIR);
			name = relDir;
			dir = new File(Environment.getExternalStorageDirectory().getPath()
					+ "/ffgbutil/" + relDir);

			 savedGame = new Properties();
			savedGame.load(new FileInputStream(new File(dir, fileName)));

			gamebook = Integer.valueOf(savedGame.getProperty("gamebook"));
			initialSkill = Integer.valueOf(savedGame.getProperty("initialSkill"));
			initialLuck = Integer.valueOf(savedGame.getProperty("initialLuck"));
			initialStamina = Integer.valueOf(savedGame
					.getProperty("initialStamina"));
			currentSkill = Integer.valueOf(savedGame.getProperty("currentSkill"));
			currentLuck = Integer.valueOf(savedGame.getProperty("currentLuck"));
			currentStamina = Integer.valueOf(savedGame
					.getProperty("currentStamina"));
			
			String equipmentS = new String(savedGame.getProperty("equipment")
					.getBytes(java.nio.charset.Charset.forName("ISO-8859-1")));
			String notesS = new String(savedGame.getProperty("notes").getBytes(
					java.nio.charset.Charset.forName("ISO-8859-1")));
			currentReference = Integer.valueOf(savedGame
					.getProperty("currentReference"));

			if (equipmentS != null) {
				equipment = new ArrayList<String>();
				List<String> list = Arrays.asList(equipmentS.split("#"));
				for (String string : list) {
					if (!string.isEmpty())
						equipment.add(string);
				}
			}

			if (notesS != null) {
				notes = new ArrayList<String>();
				List<String> list = Arrays.asList(notesS.split("#"));
				for (String string : list) {
					if (!string.isEmpty())
						notes.add(string);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getInitialSkill() {
		return initialSkill;
	}

	public void setInitialSkill(Integer initialSkill) {
		this.initialSkill = initialSkill;
	}

	public Integer getInitialLuck() {
		return initialLuck;
	}

	public void setInitialLuck(Integer initialLuck) {
		this.initialLuck = initialLuck;
	}

	public Integer getInitialStamina() {
		return initialStamina;
	}

	public void setInitialStamina(Integer initialStamina) {
		this.initialStamina = initialStamina;
	}

	public Integer getCurrentSkill() {
		return currentSkill;
	}

	public void setCurrentSkill(Integer currentSkill) {
		AdventureVitalStatsFragment adventureVitalStatsFragment = getVitalStatsFragment();
		adventureVitalStatsFragment.setSkillValue(currentSkill);
		this.currentSkill = currentSkill;
	}

	public Integer getCurrentLuck() {
		return currentLuck;
	}

	public void setCurrentLuck(Integer currentLuck) {
		AdventureVitalStatsFragment adventureVitalStatsFragment = getVitalStatsFragment();
		adventureVitalStatsFragment.setLuckValue(currentLuck);
		this.currentLuck = currentLuck;
	}

	private AdventureVitalStatsFragment getVitalStatsFragment() {
		AdventureVitalStatsFragment adventureVitalStatsFragment = (AdventureVitalStatsFragment) getSupportFragmentManager()
				.getFragments().get(FRAGMENT_VITAL_STATS);
		return adventureVitalStatsFragment;
	}

	private AdventureProvisionsFragment getProvisionsFragment() {
		AdventureProvisionsFragment adventureProvisionsFragment = (AdventureProvisionsFragment) getSupportFragmentManager()
				.getFragments().get(FRAGMENT_PROVISIONS);
		return adventureProvisionsFragment;
	}

	public Integer getCurrentStamina() {
		return currentStamina;
	}

	public void setCurrentStamina(Integer currentStamina) {
		AdventureVitalStatsFragment adventureVitalStatsFragment = getVitalStatsFragment();
		adventureVitalStatsFragment.setStaminaValue(currentStamina);
		this.currentStamina = currentStamina;
	}

	public Integer getStandardPotion() {
		return standardPotion;
	}

	public void setStandardPotion(Integer standardPotion) {
		this.standardPotion = standardPotion;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getProvisions() {
		return provisions;
	}

	public void setProvisions(Integer provisions) {
		this.provisions = provisions;
	}

	public List<String> getEquipment() {
		return equipment;
	}

	public void setEquipment(List<String> equipment) {
		this.equipment = equipment;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public Integer getCurrentReference() {
		return currentReference;
	}

	public void setCurrentReference(Integer currentReference) {
		this.currentReference = currentReference;
	}

	public Integer getStandardPotionValue() {
		return standardPotionValue;
	}

	public void setStandardPotionValue(Integer standardPotionValue) {
		this.standardPotionValue = standardPotionValue;
	}

	public void testSkill(View v) {

		boolean result = DiceRoller.roll2D6() < currentSkill;

		String message = result ? "Success!" : "Failed...";
		showAlert(message);
	}

	public void testLuck(View v) {

		boolean result = testLuckInternal();

		String message = result ? "Success!" : "Failed...";
		showAlert(message);
	}

	public boolean testLuckInternal() {
		boolean result = DiceRoller.roll2D6() < currentLuck;

		setCurrentLuck(--currentLuck);
		return result;
	}

	public void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Result")
				.setMessage(message)
				.setCancelable(false)
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void consumePotion(View view) {
		if (standardPotionValue == 0) {
			showAlert("You have no potion left...");
		} else {
			AdventureProvisionsFragment adventureProvisionsFragment = getProvisionsFragment();
			adventureProvisionsFragment.setPotionValue(--standardPotionValue);
			String message = "";
			switch (standardPotion) {
			case 0:
				message = "You have replenished your Skill level!";
				setCurrentSkill(initialSkill);
				break;
			case 1:
				message = "You have replenished your Stamina level!";
				setCurrentStamina(initialStamina);
				break;
			case 2:
				message = "You have replenished your Luck level (+1)!";
				setCurrentLuck(initialLuck + 1);
				break;
			}
			showAlert(message);
		}
	}

	public void consumeProvision(View view) {
		if (provisions == 0) {
			showAlert("You have no provisions left...");
		} else if (currentStamina == initialStamina) {
			showAlert("You are already at maximum Stamina!");
		} else {
			AdventureProvisionsFragment adventureProvisionsFragment = getProvisionsFragment();
			adventureProvisionsFragment.setProvisionsValue(--provisions);
			setCurrentStamina(currentStamina + 4);
			if (currentStamina > initialStamina)
				setCurrentStamina(initialStamina);
			showAlert("You have gained 4 Stamina points!");
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class StandardSectionsPagerAdapter extends FragmentPagerAdapter {

		public StandardSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			try {
				return (Fragment) Class.forName(
						fragmentConfiguration.get(position).getClassName())
						.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public int getCount() {
			return fragmentConfiguration.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();

			return getString(fragmentConfiguration.get(position).getTitleId())
					.toUpperCase(l);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adventure, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.rolld6:
			showAlert(DiceRoller.rollD6() + "");
			return true;
		case R.id.roll2d6:
			showAlert(DiceRoller.roll2D6() + "");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
