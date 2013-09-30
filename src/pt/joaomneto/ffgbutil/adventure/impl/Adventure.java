package pt.joaomneto.ffgbutil.adventure.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
	List<String> equipment = new ArrayList<String>();
	List<String> notes = new ArrayList<String>();
	Integer currentReference = -1;

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
				.getFragments().get(0);
		return adventureVitalStatsFragment;
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

	public void testSkill(View v) {

		boolean result = DiceRoller.roll2D6() < currentSkill;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Result")
				.setMessage(result ? "Success!" : "Failed...")
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

	public void testLuck(View v) {

		boolean result = DiceRoller.roll2D6() < currentLuck;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		setCurrentLuck(currentLuck--);

		builder.setTitle("Result")
				.setMessage(result ? "Success!" : "Failed...")
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
			case 0:
				fragment = new AdventureVitalStatsFragment();
				break;
			case 1:
				fragment = new AdventureVitalStatsFragment();
				break;
			case 2:
				fragment = new AdventureVitalStatsFragment();
				break;
			case 3:
				fragment = new AdventureVitalStatsFragment();
				break;
			case 4:
				fragment = new AdventureVitalStatsFragment();
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
				return getString(R.string.potionsProvisions).toUpperCase(l);
			case 2:
				return getString(R.string.goldEquipment).toUpperCase(l);
			case 3:
				return getString(R.string.fights).toUpperCase(l);
			case 4:
				return getString(R.string.notes).toUpperCase(l);
			}
			return null;
		}

	}
}
