package pt.joaomneto.ffgbutil.adventure.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureNotesFragment;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureProvisionsFragment;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class Adventure extends FragmentActivity {

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
	
	private final int FRAGMENT_VITAL_STATS = 0;
	private final int FRAGMENT_COMBAT = 1;
	private final int FRAGMENT_PROVISIONS = 2;
	private final int FRAGMENT_EQUIPMENT = 3;
	private final int FRAGMENT_NOTES = 4;
	

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
	
	public void showAlert(String message){
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
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			switch (position) {
			case FRAGMENT_VITAL_STATS:
				fragment = new AdventureVitalStatsFragment();
				break;
			case FRAGMENT_COMBAT:
				fragment = new AdventureCombatFragment();
				break;
			case FRAGMENT_PROVISIONS:
				fragment = new AdventureProvisionsFragment();
				break;
			case FRAGMENT_EQUIPMENT:
				fragment = new AdventureEquipmentFragment();
				break;
			case FRAGMENT_NOTES:
				fragment = new AdventureNotesFragment();
				break;
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.vitalStats).toUpperCase(l);
			case 1:
				return getString(R.string.fights).toUpperCase(l);
			case 2:
				return getString(R.string.potionsProvisions).toUpperCase(l);
			case 3:
				return getString(R.string.goldEquipment).toUpperCase(l);
			case 4:
				return getString(R.string.notes).toUpperCase(l);
			}
			return null;
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
	    	showAlert(DiceRoller.rollD6()+"");
	        return true;
	    case R.id.roll2d6:
	    	showAlert(DiceRoller.roll2D6()+"");
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
