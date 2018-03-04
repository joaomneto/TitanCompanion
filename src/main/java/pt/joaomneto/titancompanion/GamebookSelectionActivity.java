package pt.joaomneto.titancompanion;

import android.content.ActivityNotFoundException;
import android.content.Context;
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

import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;
import pt.joaomneto.titancompanion.consts.Constants;
import pt.joaomneto.titancompanion.util.LocaleHelper;

public class GamebookSelectionActivity extends FragmentActivity {

    public static final String GAMEBOOK_URL = "GAMEBOOK_URL";
    public static final String GAMEBOOK_COVER = "GAMEBOOK_COVER";
    public static final String GAMEBOOK_ID = "GAMEBOOK_ID";

    private static String[] urls;
    private static String[] values;
    private static Intent intent;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragmentConfiguration for each of the sections. We use a
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
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        intent = getIntent();

        mViewPager.setCurrentItem(intent.getIntExtra(GamebookListActivity.GAMEBOOK_POSITION, 0));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.swipe, menu);
        return true;
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

            ImageView img = rootView.findViewById(R.id.gamebookCoverImg);
            Button detailsButton = rootView.findViewById(R.id.buttonSite);
            Button createButton = rootView.findViewById(R.id.buttonCreate);

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

            Class<? extends AdventureCreation> creationActivity = Constants.getCreationActivity(getActivity(), position);

            boolean bookSupported = creationActivity != null;

            createButton.setEnabled(bookSupported);


            if (bookSupported) {
                createButton.setOnClickListener(view -> {

                    Intent intent = new Intent(getActivity().getBaseContext(), creationActivity);
                    intent.putExtra(GAMEBOOK_ID, position);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Adventure.Companion.showAlert(R.string.gamebookNotImplemented, GamebookSelectionFragment.this.getActivity());
                        e.printStackTrace();
                    }

                });
            } else {
                createButton.setText(R.string.comingSoon);
            }

            return rootView;
        }

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


}
