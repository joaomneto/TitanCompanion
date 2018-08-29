package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import net.attilaszabo.redux.Store
import pt.joaomneto.titancompanion.BaseFragmentActivity
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll
import pt.joaomneto.titancompanion.adventure.state.StateAware
import pt.joaomneto.titancompanion.adventure.state.actions.AdventureActions
import pt.joaomneto.titancompanion.adventure.state.actions.CombatActions
import pt.joaomneto.titancompanion.adventure.state.bean.State
import pt.joaomneto.titancompanion.adventure.values.CombatMode
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.StringReader
import java.util.ArrayList
import java.util.Arrays
import java.util.Date
import java.util.HashSet
import java.util.Properties

abstract class Adventure<STORE, CUSTOM_STATE : State, CUSTOM_COMBAT_STATE>(
    override val fragmentConfiguration: Array<AdventureFragmentRunner>
) : BaseFragmentActivity(
    fragmentConfiguration,
    R.layout.activity_adventure
), StateAware<STORE, CUSTOM_STATE, CUSTOM_COMBAT_STATE> {

    lateinit var name: String

    private var dir: File? = null

    open val consumeProvisionText: Int = R.string.consumeProvisions

    open val provisionsText: Int = R.string.provisions

    open val currencyName: Int = R.string.gold

    open val combatSkillValue: Int
        get() = state.currentSkill

    protected open val knockoutStamina: Int
        get() = Int.MAX_VALUE

    public open val damage: () -> Int
        get() = { 2 }

    open val defaultEnemyDamage: String
        get() = "2"

    override lateinit var store: Store<STORE>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val savedGame = loadSavegameProperties()

        loadSavegame(savedGame)
    }

    private fun loadSavegameProperties(): Properties {
        val savedGame = Properties()

        if (intent.hasExtra(LoadAdventureActivity.ADVENTURE_FILE)) {
            val fileName = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_FILE)
            name = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_DIR)
            dir = File(File(filesDir, "ffgbutil"), name)

            savedGame.load(InputStreamReader(FileInputStream(File(dir, fileName)), "UTF-8"))
        } else if (intent.hasExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT)) {
            val savegameContent = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT)
            savedGame.load(StringReader(savegameContent))
        }
        return savedGame
    }

    fun performTestSkill() {
        val message = if (state.checkSkill()) R.string.success else R.string.failed
        showAlert(message, this)
    }

    fun performTestLuck() {
        val message = if (state.checkLuck()) R.string.success else R.string.failed
        store.dispatch(AdventureActions.DecreaseLuck(1))
        showAlert(message, this)
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
        var d2 = 0
        var d3 = 0
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

        val sum = d1 + d2 + d3

        resultView.text = " = $sum"

        showAlert(toastView, this)

        return true
    }

    fun performSavegame() {
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

                toSavegameProperties().store(bw, "$ref")

                storeNotesForRestart(dir)

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

        var initialContent = readFile(File(dir, "initial.xml"))
        initialContent = initialContent.replace("notes=", "notes=${state.notesAsString()}")

        val fileWriter = FileWriter(File(dir, "initial_full_notes.xml"))
        val bw = BufferedWriter(fileWriter)
        bw.write(initialContent)

        bw.close()
        fileWriter.close()
    }

    fun performUsePotion() {
        var message = -1
        when (state.standardPotion) {
            0 -> {
                message = R.string.potionSkillReplenish
                store.dispatch(AdventureActions.ResetSkill())
                store.dispatch(AdventureActions.DecreasePotion())
            }
            1 -> {
                message = R.string.potionStaminaReplenish
                store.dispatch(AdventureActions.ResetStamina())
                store.dispatch(AdventureActions.DecreasePotion())
            }
            2 -> {
                message = R.string.potionLuckReplenish
                store.dispatch(AdventureActions.ChangeInitialLuck(state.initialLuck + 1))
                store.dispatch(AdventureActions.ResetLuck())
                store.dispatch(AdventureActions.DecreasePotion())
            }
        }
        showAlert(message, this)
    }

    private fun performAddNote(note: String) {
        store.dispatch(AdventureActions.AddNote(note))
    }

    private fun performAddEquipment(equipment: String) {
        store.dispatch(AdventureActions.AddEquipment(equipment))
    }

    private fun performRemoveEquipment(equipment: String) {
        store.dispatch(AdventureActions.RemoveEquipment(equipment))
    }

    private fun performRemoveNote(note: String) {
        store.dispatch(AdventureActions.RemoveNote(note))
    }

    fun performIncreaseGold(value: Int = 1) {
        store.dispatch(AdventureActions.IncreaseGold(value))
    }

    fun performDecreaseGold(value: Int = 1) {
        store.dispatch(AdventureActions.DecreaseGold(value))
    }

    private fun performChangeGold(value: Int) {
        store.dispatch(AdventureActions.ChangeGold(value))
    }

    fun performConsumeProvisions() {
        when {
            state.provisions == 0 -> showAlert(R.string.noProvisionsLeft, this)
            state.isMaxStamina() -> showAlert(R.string.provisionsMaxStamina, this)
            else -> {
                val staminaGain = Math.min(state.provisionsValue, state.initialStamina - state.currentStamina)
                store.dispatch(AdventureActions.DecreaseProvisions())
                store.dispatch(AdventureActions.IncreaseStamina(staminaGain))
                showAlert(resources.getString(R.string.provisionsStaminaGain, staminaGain), this)
            }
        }
    }

    fun performRemoveEquipmentWithAlert(index: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.deleteEquipmentQuestion).setCancelable(false)
            .setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton(R.string.ok) { _, _ ->
            performRemoveEquipment(state.equipment[index])
        }

        val alert = builder.create()
        alert.show()
        return true
    }

    fun performAddEquipmentWithAlert() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.equipment2)

        // Set an EditText view to get user input
        val input = EditText(this)
        input.id = R.id.alert_editText_field
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(
            R.string.ok
        ) { _, _ ->
            val value = input.text.toString()
            if (value.isEmpty())
                return@setPositiveButton
            this.performAddEquipment(value.trim { it <= ' ' })
        }

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ ->
            // Canceled.
        }

        alert.show()
    }

    fun performChangeGoldWithAlert() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.setValue)

        // Set an EditText view to get user input
        val input = EditText(this)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(R.string.ok) { _, _ ->
            val value = Integer.parseInt(input.text.toString())
            performChangeGold(value)
        }

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ -> imm.hideSoftInputFromWindow(input.windowToken, 0) }

        alert.show()
    }

    fun performRemoveNoteWithAlert(position: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.deleteNote)
            .setCancelable(false)
            .setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton(R.string.ok) { _, _ ->
            if (this.state.notes.size > position) {
                this.performRemoveNote(this.state.notes[position])
            }
        }

        val alert = builder.create()
        alert.show()
        return true
    }

    fun performAddNoteWithAlert() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.note)

        // Set an EditText view to get user input
        val input = EditText(this)
        input.id = R.id.alert_editText_field
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(
            R.string.ok
        ) { _, _ ->
            val value = input.text.toString()
            if (value.isEmpty())
                return@setPositiveButton
            this.performAddNote(value.trim { it <= ' ' })
        }

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ ->
            // Canceled.
        }

        alert.show()
    }

    override fun onPause() {
        super.onPause()

        pause()
    }

    override fun toSavegameProperties(): Properties {
        val properties = state.toSavegameProperties()
        properties.putAll(customState.toSavegameProperties())
        return properties
    }

    private fun pause() {
        try {
            val file = File(dir, "temp" + ".xml")

            val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(file), "UTF-8"))

            toSavegameProperties().store(bw, "temp")

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
        dir?.let {
            try {

                val f = File(dir, "temp.xml")

                if (!f.exists())
                    return

                val savedGame = Properties()
                savedGame.load(InputStreamReader(FileInputStream(File(dir, "temp.xml")), "UTF-8"))

                store = generateStoreFromSavegame(savedGame)

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
    }

    override fun loadSavegame(savedGame: Properties) {
        store = generateStoreFromSavegame(savedGame)
    }

    fun testResume() {
        resume()
    }

    fun testPause() {
        pause()
    }

    fun refreshScreens() {
        fragmentConfiguration
            .map { getFragment(it.fragment) as AdventureFragment<*>? }
            .forEach { it?.refreshScreen() }
    }

    protected fun stringToArray(_string: String?): List<String> {

        val elements = ArrayList<String>()

        if (_string != null) {
            val list = Arrays.asList(*_string.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

            list.filterNotTo(elements) { it.isEmpty() }
        }

        return elements
    }

    protected fun stringToSet(_string: String?): Set<String> {

        val elements = HashSet<String>()

        if (_string != null) {
            val list = Arrays.asList(*_string.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            list.filterNotTo(elements) { it.isEmpty() }
        }

        return elements
    }

    fun fullRefresh() {
        fragmentConfiguration
            .map { it.fragment }
            .forEach { (getFragment(it) as AdventureFragment<*>?)?.refreshScreen() }
    }

    private fun closeKeyboard(view: View?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        view?.clearFocus()
    }

    fun notifyPagerAdapterChanged() {
        mViewPager?.adapter?.notifyDataSetChanged()
    }

    fun performIncreaseStamina() {
        store.dispatch(AdventureActions.IncreaseStamina())
    }

    fun performIncreaseSkill() {
        store.dispatch(AdventureActions.IncreaseSkill())
    }

    fun performIncreaseLuck() {
        store.dispatch(AdventureActions.IncreaseLuck())
    }

    fun performIncreaseProvisions() {
        store.dispatch(AdventureActions.IncreaseProvisions())
    }

    fun performDecreaseStamina(value: Int = 1) {
        store.dispatch(AdventureActions.DecreaseStamina(value))
    }

    fun performDecreaseSkill(value: Int = 1) {
        store.dispatch(AdventureActions.DecreaseSkill(value))
    }

    fun performDecreaseLuck() {
        store.dispatch(AdventureActions.DecreaseLuck())
    }

    fun performDecreaseProvisions() {
        store.dispatch(AdventureActions.DecreaseProvisions())
    }

    fun performSetInitialStamina(fragment: AdventureVitalStatsFragment) {
        val alert = createAlertForInitialStatModification(R.string.setInitialStamina) { dialog, _ ->

            val value = getValueFromAlertTextField(fragment.view, dialog as AlertDialog)

            store.dispatch(AdventureActions.ChangeInitialStamina(value))
        }

        alert.show()
    }

    fun performSetInitialSkill(fragment: AdventureVitalStatsFragment) {
        val alert = createAlertForInitialStatModification(R.string.setInitialSkill) { dialog, _ ->
            val value = getValueFromAlertTextField(fragment.view!!, dialog as AlertDialog)

            store.dispatch(AdventureActions.ChangeInitialSkill(value))
        }

        alert.show()
    }

    fun performSetInitialLuck(fragment: AdventureVitalStatsFragment) {
        val alert = createAlertForInitialStatModification(R.string.setInitialLuck) { dialog, _ ->
            val value = getValueFromAlertTextField(fragment.view!!, dialog as AlertDialog)

            store.dispatch(AdventureActions.ChangeInitialLuck(value))
        }

        alert.show()
    }

    private fun getValueFromAlertTextField(view: View?, dialog: AlertDialog): Int {
        val input = dialog.findViewById<EditText>(R.id.alert_editText_field)

        val value = Integer.parseInt(input.text.toString())

        this.closeKeyboard(view)

        return value
    }

    private fun createAlertForInitialStatModification(
        dialogTitle: Int,
        positiveButtonListener: (DialogInterface, Int) -> Unit
    ): AlertDialog.Builder {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(dialogTitle)

        // Set an EditText view to get user input
        val input = EditText(this)
        input.id = R.id.alert_editText_field

        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.keyListener = DigitsKeyListener.getInstance("0123456789")
        input.requestFocus()
        alert.setView(input)

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ -> imm.hideSoftInputFromWindow(input.windowToken, 0) }

        alert.setPositiveButton(R.string.ok, positiveButtonListener)

        alert.setOnDismissListener {
            val view = this.currentFocus
            if (view != null) {
                val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        return alert
    }

    open fun performStartCombat() {
        performCombatTurn()
    }

    open fun performAddCombatant(
        combatPosition: AdventureCombatFragment.Combatant
    ) {
        store.dispatch(CombatActions.AddCombatant(combatPosition))
    }

    open fun performDecreaseCombatantStamina(
        combatPosition: AdventureCombatFragment.Combatant
    ) {
        store.dispatch(CombatActions.DecreaseCombatantStamina(combatPosition))
    }

    open fun performIncreaseCombatantStamina(
        combatPosition: AdventureCombatFragment.Combatant
    ) {
        store.dispatch(CombatActions.IncreaseCombatantStamina(combatPosition))
    }

    open fun performIncreaseCombatantSkill(
        combatPosition: AdventureCombatFragment.Combatant
    ) {
        store.dispatch(CombatActions.IncreaseCombatantSkill(combatPosition))
    }

    open fun performDecreaseCombatantSkill(
        combatPosition: AdventureCombatFragment.Combatant
    ) {
        store.dispatch(CombatActions.DecreaseCombatantSkill(combatPosition))
    }

    open fun performCombatTurn() {
        if (combatState.combatPositions.isEmpty())
            return

        if (!combatState.combatStarted) {
            store.dispatch(CombatActions.StartCombat())
        }

        if (combatState.combatMode == CombatMode.SEQUENCE) {
            performSequenceCombatTurn()
        } else {
            performStandardCombatTurn()
        }
    }

    fun performResetCombat() {
        store.dispatch(CombatActions.ResetCombat())
    }

    fun performSwitchCombatMode(checked: Boolean) {
        store.dispatch(CombatActions.CombatMode(if (checked) CombatMode.SEQUENCE else CombatMode.NORMAL))
    }

    fun performTestLuckCombat() {
        if (combatState.draw || combatState.luckTest)
            return

        store.dispatch(CombatActions.ChangeCombatLuckTest(true))

        val result = state.checkLuck()

        if (result) {
            store.dispatch(CombatActions.CombatResult(getString(R.string.youreLucky)))
            if (combatState.hit) {
                val combatant = { combatState.currentEnemy }
                store.dispatch(
                    CombatActions.ModifyCombatant(
                        combatant().copy(
                            currentStamina = combatant().currentStamina - 1,
                            staminaLoss = combatant().staminaLoss + 1
                        )
                    )
                )
                val enemyStamina = combatant().currentStamina
                if (enemyStamina <= 0 || combatState.staminaLoss >= knockoutStamina) {
                    Adventure.showAlert(getString(R.string.defeatedOpponent), this)
                    store.dispatch(CombatActions.RemoveCombatantAndAdvanceCombat())
                }
            } else {
                performIncreaseStamina()
                store.dispatch(CombatActions.DecreaseCombatStaminaLoss())
            }
        } else {
            store.dispatch(CombatActions.CombatResult(getString(R.string.youreUnlucky)))
            if (combatState.hit) {
                val combatant = combatState.currentEnemy
                store.dispatch(
                    CombatActions.ModifyCombatant(
                        combatant.copy(
                            currentStamina = combatant.currentStamina + 1,
                            staminaLoss = combatant.staminaLoss - 1
                        )
                    )
                )
            } else {
                performDecreaseStamina()
                store.dispatch(CombatActions.IncreaseCombatStaminaLoss())
            }

            if (state.currentStamina <= knockoutStamina) {
                Adventure.showAlert(getString(R.string.knockedOut), this)
            }

            if (state.currentStamina == 0) {
                onPlayerDeath()
            }
        }
    }

    protected open fun suddenDeath(diceRoll: DiceRoll, enemyDiceRoll: DiceRoll): Boolean? {
        return false
    }

    open fun endOfTurnAction(): String = ""

    protected open fun performSequenceCombatTurn() {

        store.dispatch(CombatActions.CombatDraw(false))
        store.dispatch(CombatActions.CombatHit(false))
        store.dispatch(CombatActions.CombatLuckTest(false))

        val skill = combatSkillValue

        val currentEnemy = { combatState.currentEnemy }

        store.dispatch(CombatActions.CombatDiceRoll())

        val attackStrength = combatState.attackDiceRoll.sum + skill + currentEnemy().handicap
        val enemyDiceRoll = DiceRoller.roll2D6()
        val enemyAttackStrength = enemyDiceRoll.sum + currentEnemy().currentSkill
        var combatResultText = ""
        if (attackStrength > enemyAttackStrength) {
            if (!currentEnemy().isDefenseOnly) {
                val suddenDeath = suddenDeath(combatState.attackDiceRoll, enemyDiceRoll)
                if (suddenDeath == null || !suddenDeath) {
                    store.dispatch(
                        CombatActions.ModifyCombatant(
                            currentEnemy().copy(
                                currentStamina = Math.max(
                                    0,
                                    currentEnemy().currentStamina - damage()
                                ), staminaLoss = damage()
                            )
                        )
                    )
                    store.dispatch(CombatActions.CombatHit())
                    combatResultText += (getString(R.string.hitEnemy) + " (" + combatState.attackDiceRoll.sum + " + " + skill
                        + (if (currentEnemy().handicap >= 0) " + " + currentEnemy().handicap else "") + ") vs (" + enemyDiceRoll.sum + " + "
                        + currentEnemy().currentSkill + "). (-" + damage() + getString(R.string.staminaInitials) + ")")
                } else {
                    store.dispatch(CombatActions.ModifyCombatant(currentEnemy().copy(currentStamina = 0)))
                    Adventure.showAlert(getString(R.string.defeatSuddenDeath), this)
                }
            } else {
                store.dispatch(CombatActions.CombatDraw())
                combatResultText += (getString(R.string.blockedAttack) + " (" + combatState.attackDiceRoll.sum + " + " + skill
                    + (if (currentEnemy().handicap >= 0) " + " + currentEnemy().handicap else "") + ") vs (" + enemyDiceRoll.sum + " + " + currentEnemy().currentSkill
                    + ")")
            }
        } else if (attackStrength < enemyAttackStrength) {
            val enemyDamage = AdventureCombatFragment.convertDamageStringToInteger(currentEnemy().damage)
            store.dispatch(AdventureActions.DecreaseStamina(enemyDamage))
            store.dispatch(CombatActions.IncreaseCombatStaminaLoss(enemyDamage))
            combatResultText += (getString(R.string.youWereHit) + " (" + combatState.attackDiceRoll.sum + " + " + skill + (if (currentEnemy().handicap >= 0) " + " + currentEnemy().handicap else "")
                + ") vs (" + enemyDiceRoll.sum + " + " + currentEnemy().currentSkill + "). (-" + enemyDamage + getString(
                R.string.staminaInitials
            ) + ")")
        } else {
            combatResultText += getString(R.string.bothMissed)
            store.dispatch(CombatActions.CombatDraw())
        }

        if (currentEnemy().currentStamina == 0) {
            store.dispatch(CombatActions.RemoveCombatantAndAdvanceCombat())
            combatResultText += "\n"
            combatResultText += getString(R.string.defeatedEnemy)
        } else {
            store.dispatch(CombatActions.AdvanceCombat())
        }

        if (state.currentStamina == 0) {
            store.dispatch(CombatActions.RemoveCombatantAndAdvanceCombat())
            combatResultText += "\n"
            combatResultText += getString(R.string.youveDied)
        }

        store.dispatch(CombatActions.CombatResult(combatResultText))
    }

    open fun onPlayerDeath() {
        Adventure.showAlert(getString(R.string.youreDead), this)
    }

    protected open fun performStandardCombatTurn() {
        val currentEnemy = { combatState.currentEnemy }

        store.dispatch(CombatActions.CombatDraw(false))
        store.dispatch(CombatActions.CombatHit(false))
        store.dispatch(CombatActions.CombatLuckTest(false))
        store.dispatch(CombatActions.CombatDiceRoll())

        val attackStrength = combatState.attackDiceRoll.sum + combatSkillValue + currentEnemy().handicap
        val enemyDiceRoll = DiceRoller.roll2D6()
        val enemyAttackStrength = enemyDiceRoll.sum + currentEnemy().currentSkill
        var combatResultText = ""
        if (attackStrength > enemyAttackStrength) {
            val suddenDeath = suddenDeath(combatState.attackDiceRoll, enemyDiceRoll)
            if (suddenDeath == null || !suddenDeath) {
                store.dispatch(
                    CombatActions.ModifyCombatant(
                        currentEnemy().copy(
                            currentStamina = Math.max(
                                0,
                                currentEnemy().currentStamina - damage()
                            ), staminaLoss = damage()
                        )
                    )
                )
                store.dispatch(CombatActions.CombatHit())
                combatResultText += (getString(R.string.hitEnemy) + " (" + combatState.attackDiceRoll.sum + " + " + combatSkillValue
                    + (if (currentEnemy().handicap >= 0) " + " + currentEnemy().handicap else "") + ") vs (" + enemyDiceRoll.sum + " + " + currentEnemy().currentSkill
                    + "). (-" + damage() + getString(R.string.staminaInitials) + ")")
            } else {
                store.dispatch(CombatActions.ModifyCombatant(currentEnemy().copy(currentStamina = 0)))
                store.dispatch(CombatActions.CombatHit())
                Adventure.showAlert(R.string.defeatSuddenDeath, this)
            }
        } else if (attackStrength < enemyAttackStrength) {
            val enemyDamage = AdventureCombatFragment.convertDamageStringToInteger(currentEnemy().damage)
            store.dispatch(CombatActions.IncreaseCombatStaminaLoss(enemyDamage))
            store.dispatch(AdventureActions.DecreaseStamina(enemyDamage))
            combatResultText += (getString(R.string.youWereHit) + " (" + combatState.attackDiceRoll.sum + " + " + combatSkillValue + (if (currentEnemy().handicap >= 0) " + " + currentEnemy().handicap else "")
                + ") vs (" + enemyDiceRoll.sum + " + " + currentEnemy().currentSkill + "). (-" + enemyDamage + getString(
                R.string.staminaInitials
            ) + ")")
        } else {
            combatResultText += getString(R.string.bothMissed)
            store.dispatch(CombatActions.CombatDraw())
        }

        combatResultText += endOfTurnAction()

        if (currentEnemy().currentStamina == 0 || currentEnemy().staminaLoss >= knockoutStamina) {
            store.dispatch(CombatActions.RemoveCombatantAndAdvanceCombat())
            combatResultText += "\n"
            combatResultText += getString(R.string.defeatedEnemy)
        }

        if (combatState.staminaLoss >= knockoutStamina) {
            store.dispatch(CombatActions.RemoveCombatantAndAdvanceCombat())
            Adventure.showAlert(R.string.knockedOut, this)
        }

        if (state.currentStamina == 0) {
            store.dispatch(CombatActions.RemoveCombatantAndAdvanceCombat())
            onPlayerDeath()
        }

        store.dispatch(CombatActions.CombatResult(combatResultText))
    }

    companion object {

        val DEFAULT_FRAGMENTS = arrayOf(
            AdventureFragmentRunner(
                R.string.vitalStats,
                AdventureVitalStatsFragment::class
            ),
            AdventureFragmentRunner(
                R.string.fights,
                AdventureCombatFragment::class
            ),
            AdventureFragmentRunner(
                R.string.goldEquipment,
                AdventureEquipmentFragment::class
            ),
            AdventureFragmentRunner(
                R.string.notes,
                AdventureNotesFragment::class
            )
        )

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
            builder.setTitle(if (title != null && title > 0) title else R.string.result).setMessage(message).setCancelable(
                false
            ).setNegativeButton(
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
            confirmOnClickListener: DialogInterface.OnClickListener
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(if (title > 0) title else R.string.result).setMessage(message).setCancelable(false).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }.setPositiveButton(R.string.ok, confirmOnClickListener)
            val alert = builder.create()
            alert.show()
        }

        fun showErrorAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.error).setMessage(message).setCancelable(false).setIcon(R.drawable.error_icon).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showInfoAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.info).setMessage(message).setCancelable(false).setIcon(R.drawable.info_icon).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showSuccessAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.done).setMessage(message).setCancelable(false).setIcon(R.drawable.success_icon).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showAlert(view: View, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.result).setView(view).setCancelable(false).setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showAlert(message: Int, context: Context) {
            showAlert(-1, message, context)
        }

        fun showAlert(message: String, context: Context) {
            showAlert(-1, message, context)
        }

        fun getResId(context: Context, resName: String, type: String): Int {
            return context.resources.getIdentifier(resName, type, context.packageName)
        }

        fun createSavegame(vararg values: Pair<String, String>) = Properties()
    }
}
