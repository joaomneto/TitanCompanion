package pt.joaomneto.titancompanion.adventurecreation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import pt.joaomneto.titancompanion.BaseFragmentActivity
import pt.joaomneto.titancompanion.GamebookSelectionActivity
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
import pt.joaomneto.titancompanion.consts.Constants
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

abstract class AdventureCreation(
    override val fragmentConfiguration: Array<AdventureFragmentRunner> = DEFAULT_FRAGMENTS
) : BaseFragmentActivity(fragmentConfiguration, R.layout.activity_adventure_creation) {

    companion object {
        const val NO_PARAMETERS_TO_VALIDATE = ""

        val DEFAULT_FRAGMENTS = arrayOf(
            AdventureFragmentRunner(R.string.title_adventure_creation_vitalstats, VitalStatisticsFragment::class)
        )
    }

    protected var skill = -1
    protected var luck = -1
    protected var stamina = -1
    protected var adventureName: String? = null
    var gamebook: FightingFantasyGamebook? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        gamebook = FightingFantasyGamebook.values()[intent.getIntExtra(
            GamebookSelectionActivity.GAMEBOOK_ID, -1
        )]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.adventure_creation, menu)
        return true
    }

    fun rollStats(view: View) {
        skill = DiceRoller.rollD6() + 6
        luck = DiceRoller.rollD6() + 6
        stamina = DiceRoller.roll2D6().sum + 12



        rollGamebookSpecificStats(view)

        val skillValue = findViewById<TextView>(R.id.skillValue)
        val staminaValue = findViewById<TextView>(R.id.staminaValue)
        val luckValue = findViewById<TextView>(R.id.luckValue)

        skillValue?.text = skill.toString()
        staminaValue?.text = stamina.toString()
        luckValue?.text = luck.toString()
    }

    fun saveAdventure() {
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
            bw.write("gold=0\n")
            storeAdventureSpecificValuesInFile(bw)

            bw.close()

            val intent = Intent(
                this, Constants
                .getRunActivity(this, gamebook!!)
            )

            intent.putExtra(
                LoadAdventureActivity.ADVENTURE_FILE,
                "initial.xml"
            )
            intent.putExtra(LoadAdventureActivity.ADVENTURE_DIR, relDir)
            startActivity(intent)
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }
    }

    fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.result).setMessage(message).setCancelable(false)
            .setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

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

    @Throws(IOException::class)
    open fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
    }

    open fun rollGamebookSpecificStats(view: View) {}

    open fun validateCreationSpecificParameters(): String? {
        return AdventureCreation.Companion.NO_PARAMETERS_TO_VALIDATE
    }
}
