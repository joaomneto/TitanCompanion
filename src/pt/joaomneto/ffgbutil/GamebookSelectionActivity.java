package pt.joaomneto.ffgbutil;

import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import pt.joaomneto.ffgbutil.consts.Constants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class GamebookSelectionActivity extends FragmentActivity {

	public static final String GAMEBOOK_URL = "GAMEBOOK_URL";
	public static final String GAMEBOOK_COVER = "GAMEBOOK_COVER";
	public static final String GAMEBOOK_ID = "GAMEBOOK_ID";

	private static String[] urls;
	private static String[] values;
	private static Intent intent;

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamebook_selection);

		values = getResources().getStringArray(R.array.gamebook_list_names);
		urls = getResources().getStringArray(R.array.gamebook_list_urls);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		intent = getIntent();

		mViewPager.setCurrentItem(intent.getIntExtra(GamebookListActivity.GAMEBOOK_POSITION, 0));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.swipe, menu);
		return true;
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			GamebookSelectionFragment fragment = new GamebookSelectionFragment();
			Bundle args = new Bundle();
			args.putInt(GamebookSelectionFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return values.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return values[position];
		}

	}

	public static class GamebookSelectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		private int imageLink = 0;
		private int position = 0;

		public GamebookSelectionFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			View rootView = inflater.inflate(R.layout.fragment_gamebook_selection_gamebook_selection, container, false);

			ImageView img = (ImageView) rootView.findViewById(R.id.gamebookCoverImg);
			Button detailsButton = (Button) rootView.findViewById(R.id.buttonSite);
			Button createButton = (Button) rootView.findViewById(R.id.buttonCreate);

			position = getArguments().getInt(ARG_SECTION_NUMBER);

			imageLink = Constants.getGameBookCoverAddress(position);
			img.setImageResource(imageLink);

			
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Intent intent = new Intent(getActivity().getBaseContext(), GamebookFullImageActivity.class);
					intent.putExtra(GAMEBOOK_COVER, Constants.getGameBookCoverAddress(position));
					startActivity(intent);

				}
			});

			detailsButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Intent intent = new Intent(getActivity().getBaseContext(), GamebookWikiaActivity.class);
					intent.putExtra(GAMEBOOK_URL, urls[position]);
					startActivity(intent);

				}
			});

			createButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Class<? extends AdventureCreation> creationActivity = Constants.getCreationActivity(getActivity(), position);
					if(creationActivity == null){
						Adventure.showAlert("Sorry,  this gamebook is not implemented yet!", GamebookSelectionFragment.this.getActivity());
						return;
					}
					Intent intent = new Intent(getActivity().getBaseContext(), creationActivity);
					intent.putExtra(GAMEBOOK_ID, position);
					startActivity(intent);

				}
			});

			return rootView;
		}

	}

}
