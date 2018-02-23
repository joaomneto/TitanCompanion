package pt.joaomneto.titancompanion.adventurecreation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.SparseArray
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import pt.joaomneto.titancompanion.*
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.Adventure.AdventureFragmentRunner
import pt.joaomneto.titancompanion.consts.Constants
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

abstract class AdventureCreation : BaseFragmentActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [android.support.v4.app.FragmentPagerAdapter] derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    protected var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    protected var mViewPager: ViewPager? = null

    protected var skill = -1
    protected var luck = -1
    protected var stamina = -1
    protected var adventureName: String? = null
    var gamebook: FightingFantasyGamebook? = null
        private set

    init {
        fragmentConfiguration.put(0, AdventureFragmentRunner(
            R.string.title_adventure_creation_vitalstats,
            "pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment"))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adventure_creation)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = SectionsPagerAdapter(
            supportFragmentManager)

        gamebook = FightingFantasyGamebook.values()[intent.getIntExtra(
            GamebookSelectionActivity.GAMEBOOK_ID, -1)]

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager)
        mViewPager?.adapter = mSectionsPagerAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.adventure_creation, menu)
        return true
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            try {
                val fragmentClass: Class<Fragment>
                fragmentClass = Class.forName(fragmentConfiguration.get(position).className) as Class<Fragment>
                val fragment = fragmentClass.newInstance()

                fragments.put(position, fragment)

                return fragment
            } catch (e: Exception) {
                throw TechnicalException(e)
            }

        }

        override fun getCount(): Int {
            return fragmentConfiguration.size()
        }

        override fun getPageTitle(position: Int): CharSequence {
            val l = Locale.getDefault()
            return getString(fragmentConfiguration.get(position).titleId).toUpperCase(l)
        }
    }

    fun rollStats(view: View) {
        skill = DiceRoller.rollD6() + 6
        luck = DiceRoller.rollD6() + 6
        stamina = DiceRoller.roll2D6().sum!! + 12



        rollGamebookSpecificStats(view)

        val skillValue = findViewById<TextView>(R.id.skillValue)
        val staminaValue = findViewById<TextView>(R.id.staminaValue)
        val luckValue = findViewById<TextView>(R.id.luckValue)

        skillValue.text = "" + skill
        staminaValue.text = "" + stamina
        luckValue.text = "" + luck
    }

    protected abstract fun rollGamebookSpecificStats(view: View)

    fun saveAdventure(view: View) {
        try {

            val et = findViewById<EditText>(R.id.adventureNameInput)

            adventureName = et.text.toString()

            try {
                validateCreationParameters()
            } catch (e: IllegalArgumentException) {
                showAlert(e.message!!)
                return
            }

            val relDir = ("save_" + gamebook!!.initials + "_"
                + adventureName!!.replace(' ', '-'))
            var dir = File(filesDir, "ffgbutil")
            dir = File(dir, relDir)
            if (!dir.exists()) {
                dir.mkdirs()
            } else {
                dir.deleteRecursively()
            }

            val file = File(dir, "initial.xml")

            val bw = BufferedWriter(FileWriter(file))

            bw.write("gamebook=" + gamebook + "\n")
            bw.write("name=" + adventureName + "\n")
            bw.write("initialSkill=" + skill + "\n")
            bw.write("initialLuck=" + luck + "\n")
            bw.write("initialStamina=" + stamina + "\n")
            bw.write("currentSkill=" + skill + "\n")
            bw.write("currentLuck=" + luck + "\n")
            bw.write("currentStamina=" + stamina + "\n")
            bw.write("currentReference=1\n")
            bw.write("equipment=\n")
            bw.write("notes=\n")
            storeAdventureSpecificValuesInFile(bw)

            bw.close()

            val intent = Intent(this, Constants
                .getRunActivity(this, gamebook))

            intent.putExtra(LoadAdventureActivity.ADVENTURE_FILE,
                "initial.xml")
            intent.putExtra(LoadAdventureActivity.ADVENTURE_DIR, relDir)
            startActivity(intent)

        } catch (e: Exception) {
            throw IllegalStateException(e)
        }

    }

    @Throws(IOException::class)
    protected abstract fun storeAdventureSpecificValuesInFile(bw: BufferedWriter)

    fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.result).setMessage(message).setCancelable(false)
            .setNegativeButton(R.string.close) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    abstract fun validateCreationSpecificParameters(): String?

    @Throws(IllegalArgumentException::class)
    private fun validateCreationParameters() {
        val sb = StringBuilder()
        var error = false
        sb.append(getString(R.string.someParametersMIssing))

        if (this.stamina < 0) {
            sb.append(getString(R.string.skill2) + ", " + getString(R.string.stamina2) + " and " + getString(R.string.luck2))
            error = true
        }
        sb.append(if (error) "; " else "")
        if (this.adventureName == null || this.adventureName!!.trim { it <= ' ' }.isEmpty()) {
            sb.append(getString(R.string.adventureName))
            error = true
        }

        val specificParameters = validateCreationSpecificParameters()

        if (specificParameters != null && specificParameters.isNotEmpty()) {
            sb.append(if (error) "; " else "")
            error = true
            sb.append(specificParameters)
        }

        sb.append(")")
        if (error) {
            throw IllegalArgumentException(sb.toString())
        }
    }

    companion object {

        @JvmStatic
        val NO_PARAMETERS_TO_VALIDATE = ""

        @JvmStatic
        var fragmentConfiguration = SparseArray<Adventure.AdventureFragmentRunner>()

        @JvmStatic
        val fragments = HashMap<Int, Fragment>()
    }

}
