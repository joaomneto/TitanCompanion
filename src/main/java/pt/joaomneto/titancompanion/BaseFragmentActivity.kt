package pt.joaomneto.titancompanion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.util.SparseArray
import android.view.ViewGroup
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.LocaleHelper
import java.util.Locale
import kotlin.reflect.KClass

/**
 * Created by Joao Neto on 31-05-2017.
 */

abstract class BaseFragmentActivity(
    open val fragmentConfiguration: Array<AdventureFragmentRunner>,
    open val contentView: Int
) : FragmentActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragmentConfiguration for each of the sections. We use a
     * [android.support.v4.app.FragmentPagerAdapter] derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    protected var mViewPager: ViewPager? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = SectionsPagerAdapter(
            supportFragmentManager
        )

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager)
        mViewPager?.adapter = mSectionsPagerAdapter
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var registeredFragments = SparseArray<Fragment>()

        override fun getItem(position: Int): Fragment {
            return fragmentConfiguration[position].fragment.constructors.first().call()
        }

        override fun getCount(): Int {
            return fragmentConfiguration.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            val l = Locale.getDefault()
            return getString(fragmentConfiguration[position].titleId).toUpperCase(l)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            registeredFragments.put(position, fragment)
            return fragment
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            registeredFragments.remove(position)
            super.destroyItem(container, position, `object`)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <F : Fragment> getFragment(kclass: KClass<F>): F? {
        var fragment: F? = null
        val fragmentCount = mSectionsPagerAdapter?.registeredFragments?.size() ?: 0
        for(index in 0..fragmentCount) {
            val frag = mSectionsPagerAdapter!!.registeredFragments.get(index)
            if (kclass.isInstance(frag)) fragment = frag as F
        }
        return fragment
    }

    @Suppress("UNCHECKED_CAST")
    fun <F : Fragment> getFragment(javaclass: Class<F>): F? {
        var fragment: F? = null
        val fragmentCount = mSectionsPagerAdapter?.registeredFragments?.size() ?: 0
        for(index in 0..fragmentCount) {
            val frag = mSectionsPagerAdapter!!.registeredFragments.get(index)
            if (javaclass.kotlin.isInstance(frag)) fragment = frag as F
        }
        return fragment
    }
}
