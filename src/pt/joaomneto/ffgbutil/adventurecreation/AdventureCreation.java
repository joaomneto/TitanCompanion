package pt.joaomneto.ffgbutil.adventurecreation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pt.joaomneto.ffgbutil.GamebookSelectionActivity;
import pt.joaomneto.ffgbutil.LoadAdventureActivity;
import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.Adventure.AdventureFragmentRunner;
import pt.joaomneto.ffgbutil.consts.Constants;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public abstract class AdventureCreation extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	protected SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	protected ViewPager mViewPager;

	protected int skill = -1;
	protected int luck = -1;
	protected int stamina = -1;
	protected int gamebook = -1;

	protected static SparseArray<Adventure.AdventureFragmentRunner> fragmentConfiguration = new SparseArray<Adventure.AdventureFragmentRunner>();
	final private static Map<Integer, Fragment> fragments = new HashMap<>();

	public AdventureCreation() {
		super();
		fragmentConfiguration.put(0, new AdventureFragmentRunner(
				R.string.title_adventure_creation_vitalstats,
				"pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.VitalStatisticsFragment"));

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adventure_creation);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		gamebook = getIntent().getIntExtra(
				GamebookSelectionActivity.GAMEBOOK_ID, -1);

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

		@SuppressWarnings("unchecked")
		@Override
		public Fragment getItem(int position) {
			try {
				Class<Fragment> fragmentClass;
				fragmentClass = (Class<Fragment>) Class.forName(fragmentConfiguration.get(position).getClassName());
				Fragment fragment = fragmentClass.newInstance();

				fragments.put(position, fragment);

				return fragment;
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
			return getString(fragmentConfiguration.get(position).getTitleId()).toUpperCase(l);
		}
	}

	public void rollStats(View view) {
		skill = DiceRoller.rollD6() + 6;
		luck = DiceRoller.rollD6() + 6;
		stamina = DiceRoller.roll2D6().getSum() + 12;

		
		
		rollGamebookSpecificStats(view);
		
		TextView skillValue = (TextView) findViewById(R.id.skillValue);
		TextView staminaValue = (TextView) findViewById(R.id.staminaValue);
		TextView luckValue = (TextView) findViewById(R.id.luckValue);

		skillValue.setText("" + skill);
		staminaValue.setText("" + stamina);
		luckValue.setText("" + luck);
	}

	protected abstract void rollGamebookSpecificStats(View view);

	

	public void saveAdventure(View view) {
		try {

			EditText et = (EditText) findViewById(R.id.adventureNameInput);

			String relDir = "save_"+Constants.getActivityPrefix(this, gamebook)+"_"
								+ et.getText().toString().replace(' ', '-');
			String dirName = Environment.getExternalStorageDirectory()
					.getPath() + "/ffgbutil/" + relDir;
			File dir = new File(dirName);
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
			bw.write("currentReference=1\n");
			bw.write("equipment=\n");
			bw.write("notes=\n");
			storeAdventureSpecificValuesInFile(bw);

			bw.close();
			
			Intent intent = new Intent(this, Constants
					.getRunActivity(this, gamebook));

			intent.putExtra(LoadAdventureActivity.ADVENTURE_FILE,
					"initial.xml");
			intent.putExtra(LoadAdventureActivity.ADVENTURE_DIR, relDir);
			startActivity(intent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException;
	
	public void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Result").setMessage(message).setCancelable(false)
				.setNegativeButton("Close", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}


    public static Map<Integer, Fragment> getFragments() {
        return fragments;
    }
}
