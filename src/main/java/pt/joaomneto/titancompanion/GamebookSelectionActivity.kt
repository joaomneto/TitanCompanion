package pt.joaomneto.titancompanion

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.consts.Constants
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.LocaleHelper

class GamebookSelectionActivity : FragmentActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragmentConfiguration for each of the sections. We use a
     * [android.support.v4.app.FragmentPagerAdapter] derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

    /**
     * The [ViewPager] that will host the section contents.
     */
    private lateinit var mViewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamebook_selection)


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mSectionsPagerAdapter

        intent = intent

        mViewPager.currentItem = intent!!.getIntExtra(GamebookListActivity.GAMEBOOK_POSITION, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.swipe, menu)
        return true
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    class GamebookSelectionFragment : Fragment() {

        private var imageLink = 0
        private var position = 0

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreate(savedInstanceState)
            val rootView = inflater.inflate(R.layout.fragment_gamebook_selection_gamebook_selection, container, false)

            val img = rootView.findViewById<ImageView>(R.id.gamebookCoverImg)
            val detailsButton = rootView.findViewById<Button>(R.id.buttonSite)
            val createButton = rootView.findViewById<Button>(R.id.buttonCreate)

            position = arguments!!.getInt(ARG_SECTION_NUMBER)

            imageLink = Constants.getGameBookCoverAddress(position)
            img.setImageResource(imageLink)


            img.setOnClickListener {
                val intent = Intent(activity!!.baseContext, GamebookFullImageActivity::class.java)
                intent.putExtra(GAMEBOOK_COVER, Constants.getGameBookCoverAddress(position))
                startActivity(intent)
            }

            val urls = FightingFantasyGamebook.values().map { getString(it.url) }

            detailsButton.setOnClickListener {
                val intent = Intent(activity!!.baseContext, GamebookWikiaActivity::class.java)
                intent.putExtra(GAMEBOOK_URL, urls[position])
                startActivity(intent)
            }

            val creationActivity = Constants.getCreationActivity(activity!!, position)

            val bookSupported = creationActivity != null

            createButton.isEnabled = bookSupported


            if (bookSupported) {
                createButton.setOnClickListener { view ->

                    val intent = Intent(activity!!.baseContext, creationActivity)
                    intent.putExtra(GAMEBOOK_ID, position)
                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Adventure.showAlert(R.string.gamebookNotImplemented, this@GamebookSelectionFragment.activity!!)
                        e.printStackTrace()
                    }

                }
            } else {
                createButton.setText(R.string.comingSoon)
            }

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            val ARG_SECTION_NUMBER = "section_number"
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        val values = FightingFantasyGamebook.values().map {
            getString(it.nameResourceId)
        }

        override fun getItem(position: Int): Fragment {
            val fragment = GamebookSelectionFragment()
            val args = Bundle()
            args.putInt(GamebookSelectionFragment.ARG_SECTION_NUMBER, position)
            fragment.arguments = args
            return fragment
        }

        override fun getCount(): Int {
            return values!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return values!![position]
        }
    }

    companion object {

        val GAMEBOOK_URL = "GAMEBOOK_URL"
        val GAMEBOOK_COVER = "GAMEBOOK_COVER"
        val GAMEBOOK_ID = "GAMEBOOK_ID"
    }
}
