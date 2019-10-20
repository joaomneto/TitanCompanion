package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import pt.joaomneto.titancompanion.BaseFragmentActivity
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.util.ArrayList
import java.util.Date
import java.util.HashSet
import java.util.Properties
import kotlin.math.max
import kotlin.math.min

abstract class Adventure(override val fragmentConfiguration: Array<AdventureFragmentRunner>) :
    BaseFragmentActivity(
        fragmentConfiguration,
        R.layout.activity_adventure
    ) {

    var initialSkill: Int = -1
    var initialLuck: Int = -1
    var initialStamina: Int = -1
    internal var currentSkill: Int = -1
    internal var currentLuck: Int = -1
    internal var currentStamina: Int = -1
    var equipment: MutableList<String> = ArrayList()
    var notes: List<String> = ArrayList()
    private var currentReference: Int = -1
    // Common values
    var standardPotion: Int = -1
    open var gold: Int = 0
    var provisions: Int = -1
    @get:Synchronized
    @set:Synchronized
    var provisionsValue: Int = -1
    var standardPotionValue: Int = -1
    private var dir: File? = null
    var gamebook: FightingFantasyGamebook? = null
        internal set
    var name: String? = null
    var savedGame: Properties = Properties()

    private val vitalStatsFragment: AdventureVitalStatsFragment?
        get() = getFragment()

    open val consumeProvisionText: Int = R.string.consumeProvisions

    open val provisionsText: Int = R.string.provisions

    open val currencyName: Int = R.string.gold

    open val combatSkillValue: Int
        get() = getCurrentSkill()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val propertiesString = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT)
            name = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_NAME)
            dir = File(File(filesDir, "ffgbutil"), requireNotNull(name))

            savedGame.clear()
            savedGame.load(ByteArrayInputStream(propertiesString?.toByteArray()))

            loadGameFromProperties()
        } catch (e: Exception) {
            throw IllegalStateException("Unable to create adventure", e)
        }
    }

    fun loadGameFromFile(dir: File, fileName: String){
        savedGame.clear()
        savedGame.load(FileInputStream(File(dir, fileName)))
        loadGameFromProperties()
    }

    fun loadGameFromProperties() {

        val gamebook = savedGame.getProperty("gamebook")
        val numbericGbs = gamebook.toIntOrNull()
        if (numbericGbs != null)
            this.gamebook = FightingFantasyGamebook.values()[numbericGbs]
        else
            this.gamebook = FightingFantasyGamebook.valueOf(gamebook)

        initialSkill = Integer.valueOf(savedGame.getProperty("initialSkill"))
        initialLuck = Integer.valueOf(savedGame.getProperty("initialLuck"))
        initialStamina = Integer.valueOf(savedGame.getProperty("initialStamina"))
        currentSkill = Integer.valueOf(savedGame.getProperty("currentSkill"))
        currentLuck = Integer.valueOf(savedGame.getProperty("currentLuck"))
        currentStamina = Integer.valueOf(savedGame.getProperty("currentStamina"))

        val equipmentS =
            String(savedGame.getProperty("equipment").toByteArray(java.nio.charset.Charset.forName("UTF-8")))
        val notesS =
            String(savedGame.getProperty("notes").toByteArray(java.nio.charset.Charset.forName("UTF-8")))
        currentReference = Integer.valueOf(savedGame.getProperty("currentReference"))

        equipment = stringToStringList(equipmentS)

        notes = stringToStringList(notesS)

        val provisionsS = savedGame.getProperty("provisions")
        provisions =
            if (provisionsS != null && provisionsS != "null") Integer.valueOf(provisionsS) else -1
        val provisionsValueS = savedGame.getProperty("provisionsValue")
        provisionsValue =
            if (provisionsValueS != null && provisionsValueS != "null") Integer.valueOf(
                provisionsValueS
            ) else -1

        loadAdventureSpecificValuesFromFile()
    }

    protected fun stringToStringList(equipmentS: String) =
        equipmentS
            .split("#".toRegex())
            .filter { it.isNotEmpty() }
            .toMutableList()

    inline fun <reified Y : Enum<Y>> stringToEnumList(equipmentS: String): List<Y> {
        return stringToStringList(equipmentS).map { safeValueOf<Y>(it)!! }
    }

    inline fun <reified Y : Enum<Y>> stringToEnumMap(equipmentS: String): Map<Y, Int> {
        return stringToStringList(equipmentS).map {
            val tokens = it.split("§")
            safeValueOf<Y>(tokens[0])!! to tokens[1].toInt()
        }.toMap()
    }

    inline fun <reified T : Enum<T>> safeValueOf(type: String?): T? {
        return java.lang.Enum.valueOf(T::class.java, type)
    }

    protected abstract fun loadAdventureSpecificValuesFromFile()

    fun testSkill() {
        val result = DiceRoller.roll2D6().sum < currentSkill

        val message = if (result) R.string.success else R.string.failed
        showAlert(message, this)
    }

    fun testLuck() {
        val result = testLuckInternal()

        val message = if (result) R.string.success else R.string.failed
        showAlert(message, this)
    }

    fun testLuckInternal(): Boolean {
        val result = DiceRoller.roll2D6().sum < currentLuck

        setCurrentLuck(--currentLuck)
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.rolld6 -> {
                displayRollXD6(1)
            }
            R.id.roll2d6 -> {
                displayRollXD6(2)
            }
            R.id.roll3d6 -> {
                displayRollXD6(3)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayRollXD6(diceNumber: Int): Boolean {
        val d1 = DiceRoller.rollD6()
        var d2: Int = 0
        var d3: Int = 0
        if (diceNumber > 1) {
            d2 = DiceRoller.rollD6()
        }
        if (diceNumber > 2) {
            d3 = DiceRoller.rollD6()
        }

        val toastView = layoutInflater.inflate(R.layout.d3toast, findViewById(R.id.d3ToastLayout))
        val imageViewD1 = toastView.findViewById<ImageView>(R.id.d1)
        var imageViewD2: ImageView? = null
        var imageViewD3: ImageView? = null

        if (diceNumber > 1) {
            imageViewD2 = toastView.findViewById(R.id.d2)
        }

        if (diceNumber > 2) {
            imageViewD3 = toastView.findViewById(R.id.d3)
        }

        val resultView = toastView.findViewById<TextView>(R.id.diceResult)

        val d1Id = resources.getIdentifier("d6$d1", "drawable", this.packageName)

        var d2Id: Int? = null
        var d3Id: Int? = null

        if (diceNumber > 1) {
            d2Id = resources.getIdentifier("d6$d2", "drawable", this.packageName)
        }

        if (diceNumber > 2) {
            d3Id = resources.getIdentifier("d6$d3", "drawable", this.packageName)
        }

        imageViewD1.setImageResource(d1Id)

        if (diceNumber > 1) {
            imageViewD2!!.setImageResource(d2Id!!)
        }

        if (diceNumber > 2) {
            imageViewD3!!.setImageResource(d3Id!!)
        }

        resultView.text = " = ${d1 + d2 + d3}"

        showAlert(toastView, this)

        return true
    }

    fun savepoint(onSaveCallback: (() -> Unit)? = null) {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.currentReference)

        // Set an EditText view to get user input
        val input = EditText(this)
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(R.string.ok) { _, _ ->
            try {
                imm.hideSoftInputFromWindow(input.windowToken, 0)
                val ref = input.text.toString()
                val file = File(dir, "$ref.xml")

                val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(file), "UTF-8"))

                storeValuesInFile(ref, bw)
                storeNotesForRestart(dir)

                onSaveCallback?.invoke()

                bw.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        alert.setNegativeButton(R.string.cancel) { _, _ ->
            imm.hideSoftInputFromWindow(
                input.windowToken,
                0
            )
        }

        alert.show()
    }

    @Throws(IOException::class)
    private fun readFile(file: File): String {
        val reader = BufferedReader(FileReader(file))
        var line: String?
        val stringBuilder = StringBuilder()
        val ls = System.getProperty("line.separator")

        while (true) {
            line = reader.readLine()
            if (line == null) break
            stringBuilder.append(line)
            stringBuilder.append(ls)
        }

        reader.close()
        return stringBuilder.toString()
    }

    @Throws(IOException::class)
    private fun storeNotesForRestart(dir: File?) {
        val notesS = stringListToText(notes)

        var initialContent = readFile(File(dir, "initial.xml"))
        initialContent = initialContent.replace("notes=", "notes=$notesS")

        val fileWriter = FileWriter(File(dir, "initial_full_notes.xml"))
        val bw = BufferedWriter(fileWriter)
        bw.write(initialContent)

        bw.close()
        fileWriter.close()
    }

    @Throws(IOException::class)
    fun storeValuesInFile(ref: String, bw: BufferedWriter) {
        var equipmentS = ""
        val notesS = stringListToText(notes)

        if (equipment.isNotEmpty()) {
            for (eq in equipment) {
                equipmentS += "$eq#"
            }
            equipmentS = equipmentS.substring(0, equipmentS.length - 1)
        }
        bw.write("gamebook=$gamebook\n")
        bw.write("name=$name\n")
        bw.write("initialSkill=$initialSkill\n")
        bw.write("initialLuck=$initialSkill\n")
        bw.write("initialStamina=$initialStamina\n")
        bw.write("currentSkill=$currentSkill\n")
        bw.write("currentLuck=$currentLuck\n")
        bw.write("currentStamina=$currentStamina\n")
        bw.write("currentReference=$ref\n")
        bw.write("equipment=$equipmentS\n")
        bw.write("notes=$notesS\n")
        bw.write("provisions=$provisions\n")
        bw.write("provisionsValue=$provisionsValue\n")
        storeAdventureSpecificValuesInFile(bw)
    }

    @Throws(IOException::class)
    abstract fun storeAdventureSpecificValuesInFile(bw: BufferedWriter)

    fun consumePotion() {
        var message = -1
        when (standardPotion) {
            0 -> {
                message = R.string.potionSkillReplenish
                setCurrentSkill(initialSkill)
            }
            1 -> {
                message = R.string.potionStaminaReplenish
                setCurrentStamina(initialStamina)
            }
            2 -> {
                message = R.string.potionLuckReplenish
                val maxLuck = initialLuck + 1
                setCurrentLuck(maxLuck)
                initialLuck = maxLuck
            }
        }
        standardPotionValue -= 1
        showAlert(message, this)
        refreshScreens()
    }

    fun consumeProvision() {
        when {
            provisions == 0 -> showAlert(R.string.noProvisionsLeft, this)
            getCurrentStamina() == initialStamina -> showAlert(R.string.provisionsMaxStamina, this)
            else -> {
                vitalStatsFragment?.setProvisionsValue(--provisions)
                val staminaGain = min(provisionsValue, initialStamina - currentStamina)
                setCurrentStamina(min(getCurrentStamina() + provisionsValue, initialStamina))
                showAlert(resources.getString(R.string.provisionsStaminaGain, staminaGain), this)
            }
        }
    }

    fun getCurrentSkill(): Int {
        return currentSkill
    }

    fun setCurrentSkill(currentSkill: Int) {
        val adventureVitalStatsFragment = vitalStatsFragment
        this.currentSkill = currentSkill
        adventureVitalStatsFragment?.refreshScreensFromResume()
    }

    fun getCurrentLuck(): Int {
        return currentLuck
    }

    fun setCurrentLuck(currentLuck: Int) {
        val adventureVitalStatsFragment = vitalStatsFragment
        this.currentLuck = currentLuck
        adventureVitalStatsFragment?.refreshScreensFromResume()
    }

    fun getCurrentStamina(): Int {
        return currentStamina
    }

    fun setCurrentStamina(currentStamina: Int) {
        val adventureVitalStatsFragment = vitalStatsFragment
        this.currentStamina = currentStamina
        adventureVitalStatsFragment?.refreshScreensFromResume()
    }

    override fun onPause() {
        super.onPause()

        pause()
    }

    private fun pause() {
        try {
            val file = File(dir, "temp" + ".xml")

            val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(file), "UTF-8"))

            storeValuesInFile("-1", bw)

            bw.close()
        } catch (e: IOException) {
            try {
                val fw = FileWriter(File(dir, "exception_" + Date() + ".txt"), true)
                val pw = PrintWriter(fw)
                e.printStackTrace(pw)
                pw.close()
                fw.close()
            } catch (e1: IOException) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resume()
    }

    private fun resume() {
        try {
            val f = File(dir, "temp.xml")

            if (!f.exists())
                return

            loadGameFromFile(requireNotNull(dir), "temp.xml")

            refreshScreens()
        } catch (e: Exception) {
            try {
                val fw = FileWriter(File(dir, "exception_" + Date() + ".txt"), true)
                val pw = PrintWriter(fw)
                e.printStackTrace(pw)
                pw.close()
                fw.close()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }
    }

    fun testResume() {
        resume()
    }

    fun testPause() {
        pause()
    }

    open fun refreshScreens() {
        fragmentConfiguration
            .map { getFragment(it.fragment) as AdventureFragment? }
            .forEach { it?.refreshScreensFromResume() }
    }

    override fun onBackPressed() {
        showConfirmation(
            R.string.savegameQuestionTitle,
            R.string.savegameQuestion,
            this,
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                savepoint {
                    showSuccessAlert(
                        R.string.gamesaved,
                        this,
                        DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()
                            super.onBackPressed()
                        }
                    )
                }
            },
            DialogInterface.OnClickListener { _, _ ->
                super.onBackPressed()
            }
        )
    }

    protected fun stringToArray(_string: String?): List<String> {
        val elements = ArrayList<String>()

        if (_string != null) {
            val list =
                listOf(*_string.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            for (string in list) {
                if (string.isNotEmpty())
                    elements.add(string)
            }
        }

        return elements
    }

    protected fun stringToSet(_string: String?): Set<String> {
        val elements = HashSet<String>()

        if (_string != null) {
            val list =
                listOf(*_string.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            for (string in list) {
                if (string.isNotEmpty())
                    elements.add(string)
            }
        }

        return elements
    }

    fun changeStamina(i: Int) {
        setCurrentStamina(
            if (i > 0) min(initialStamina, getCurrentStamina() + i) else max(
                0,
                getCurrentStamina() + i
            )
        )
    }

    fun fullRefresh() {
        fragmentConfiguration
            .map { it.fragment }
            .forEach { (getFragment(it) as AdventureFragment?)?.refreshScreensFromResume() }
    }

    fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    fun notifyPagerAdapterChanged() {
        mViewPager?.adapter?.notifyDataSetChanged()
    }

    companion object {

        protected const val FRAGMENT_EQUIPMENT = 2
        protected const val FRAGMENT_NOTES = 3

        fun showAlert(title: Int, message: Int, context: Context) {
            showAlert(title, context.getString(message), context)
        }

        fun showAlert(
            title: Int? = null,
            message: String,
            context: Context,
            extraActionTextId: Int? = null,
            extraActionCallback: () -> Unit = {}
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(if (title != null && title > 0) title else R.string.result)
                .setMessage(message).setCancelable(false).setNegativeButton(
                    R.string.close
                ) { dialog, _ -> dialog.cancel() }

            if (extraActionTextId != null && extraActionCallback != {}) {
                builder.setPositiveButton(extraActionTextId) { dialog, _ ->
                    extraActionCallback()
                    dialog.cancel()
                }
            }
            val alert = builder.create()
            alert.show()
        }

        fun showConfirmation(
            title: Int,
            message: Int,
            context: Context,
            confirmOnClickListener: DialogInterface.OnClickListener,
            dismissOnClickListener: DialogInterface.OnClickListener? = null
        ) {
            val builder = AlertDialog.Builder(context)
            builder
                .setTitle(if (title > 0) title else R.string.result)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(
                    R.string.close,
                    dismissOnClickListener
                        ?: DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() }
                )
                .setPositiveButton(R.string.ok, confirmOnClickListener)
            val alert = builder.create()
            alert.show()
        }

        fun showErrorAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.error).setMessage(message).setCancelable(false)
                .setIcon(R.drawable.error_icon).setNegativeButton(
                    R.string.close
                ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showInfoAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.info).setMessage(message).setCancelable(false)
                .setIcon(R.drawable.info_icon).setNegativeButton(
                    R.string.close
                ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showSuccessAlert(
            message: Int,
            context: Context,
            onClickListener: DialogInterface.OnClickListener? = null
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.done)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.drawable.success_icon)
                .setNegativeButton(
                    R.string.close,
                    onClickListener
                        ?: DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() }
                )
            val alert = builder.create()
            alert.show()
        }

        fun showAlert(view: View, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.result).setView(view).setCancelable(false)
                .setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun arrayToString(elements: Collection<Any>): String {
            var _string = ""

            if (!elements.isEmpty()) {
                for (value in elements) {
                    _string += "$value#"
                }
                _string = _string.substring(0, _string.length - 1)
            }
            return _string
        }

        fun showAlert(message: Int, context: Context) {
            showAlert(-1, message, context)
        }

        fun showAlert(message: String, context: Context) {
            showAlert(-1, message, context)
        }

        fun <Y : Enum<Y>> enumListToText(list: List<Y>): String {
            var text = ""

            if (list.isNotEmpty()) {
                for (note in list) {
                    text += note.name + "#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun stringListToText(list: List<String>): String {
            var text = ""

            if (list.isNotEmpty()) {
                for (note in list) {
                    text += "$note#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun getResId(context: Context, resName: String, type: String): Int {
            return context.resources.getIdentifier(resName, type, context.packageName)
        }

        fun addToListOnClickListener(
            list: MutableList<String>,
            listView: ListView,
            context: Context
        ): (View) -> Unit = { _: View ->
            val alert = AlertDialog.Builder(context)

            alert.setTitle(R.string.note)

            // Set an EditText view to get user input
            val input = EditText(context)
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
            input.requestFocus()
            alert.setView(input)

            alert.setPositiveButton(
                R.string.ok
            ) { _, _ ->
                synchronized(list) {
                    val value = input.text.toString()
                    if (value.isEmpty())
                        return@setPositiveButton
                    list.add(value.trim { it <= ' ' })
                    (listView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }
            }

            alert.setNegativeButton(R.string.cancel) { _, _ -> }
            alert.show()
        }

        fun listItemLongPressListener(
            list: MutableList<String>,
            listView: ListView,
            context: Context
        ): (parent: AdapterView<*>, view: View, position: Int, id: Long) -> Boolean =
            { _: AdapterView<*>, _: View, position: Int, _: Long ->
                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.deleteNote)
                    .setCancelable(false)
                    .setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
                builder.setPositiveButton(R.string.ok) { _, _ ->
                    synchronized(list) {
                        if (list.size > position) {
                            list.removeAt(position)
                        }
                    }
                    (listView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }

                val alert = builder.create()
                alert.show()
                true
            }
    }
}
