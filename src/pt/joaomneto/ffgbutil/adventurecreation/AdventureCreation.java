package pt.joaomneto.ffgbutil.adventurecreation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Random;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventurecreation.fragments.PotionsFragment;
import pt.joaomneto.ffgbutil.adventurecreation.fragments.VitalStatisticsFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AdventureCreation extends FragmentActivity {

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
	

	private static Random random = new Random(System.currentTimeMillis()); 
	private int skill = -1;
	private int luck = -1;
	private int stamina = -1;
	private int potion = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adventure_creation);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adventure_creation, menu);
		return true;
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
			Fragment fragment;
			switch (position) {
			case 0:
				fragment = new VitalStatisticsFragment();
				break;
			case 1:
				fragment = new PotionsFragment();
				break;
			default:
				fragment = null;
				break;
			}
			
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_adventure_creation_vitalstats).toUpperCase(l);
			case 1:
				return getString(R.string.title_adventure_creation_potions).toUpperCase(l);
			}
			return null;
		}
	}
	

	
	public void rollStats(View view){
		skill = random.nextInt(5)+7;
		luck = random.nextInt(5)+7;
		stamina = random.nextInt(11)+13;
				
		TextView skillValue = (TextView) findViewById(R.id.skillValue);
		TextView staminaValue = (TextView) findViewById(R.id.staminaValue);
		TextView luckValue = (TextView) findViewById(R.id.luckValue);
		
		skillValue.setText(""+skill);
		staminaValue.setText(""+stamina);
		luckValue.setText(""+luck);
	}
	
	
	
	public void saveAdventure(View view){
		try {
			EditText et = (EditText) findViewById(R.id.adventureNameInput);
			
			File file = new File(this.getFilesDir(), "save_"+et.getText().toString().replace(' ', '-')+".xml");
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			bw.write("name="+et.getText().toString());
			bw.write("initial_skill="+skill);
			bw.write("initial_luck="+luck);
			bw.write("initial_stamina="+stamina);
			bw.write("current_skill="+skill);
			bw.write("current_luck="+luck);
			bw.write("current_stamina="+stamina);
			bw.write("standard_potion="+potion);
			
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized int getPotion() {
		return potion;
	}

	public synchronized void setPotion(int potion) {
		this.potion = potion;
	}
	

}
