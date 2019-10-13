package pt.joaomneto.titancompanion

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
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

    var mSectionsPagerAdapter: SectionsPagerAdapter? = null
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

    inline fun <reified F : Fragment> getFragment() = getFragment(F::class)

    fun <F : Fragment> getFragment(klass: KClass<F>) = getFragment(klass.java)

    @Suppress("UNCHECKED_CAST")
    fun <F : Fragment> getFragment(klass: Class<F>): F? {
        var fragment: F? = null
        val fragmentCount = mSectionsPagerAdapter?.registeredFragments?.size() ?: 0
        for (index in 0..fragmentCount) {
            val frag = mSectionsPagerAdapter!!.registeredFragments.get(index)
            if (klass.isInstance(frag)) fragment = frag as F
        }
        return fragment
    }
}
