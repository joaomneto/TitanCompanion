package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.adapter.CombatantListAdapter
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll
import pt.joaomneto.titancompanion.util.DiceRoller
import java.util.ArrayList

open class AdventureCombatFragment : AdventureFragment() {
    protected var combatResult: TextView? = null
    protected var startCombatButton: Button? = null
    protected var combatTurnButton: Button? = null
    protected var addCombatButton: Button? = null
    protected var testLuckButton: Button? = null
    protected var resetButton: Button? = null
    protected var resetButton2: Button? = null
    protected var combatTypeSwitch: Switch? = null
    protected var rootView: View? = null
    protected var combatPositions: MutableList<Combatant> = ArrayList()
    protected var combatantListAdapter: CombatantListAdapter? = null
    protected var combatantsListView: ListView? = null
    protected var combatMode = NORMAL
    protected var handicap = 0

    protected var draw = false
    protected var luckTest = false
    protected var hit = false

    protected var combatStarted = false

    protected var staminaLoss = 0
    protected var attackDiceRoll = DiceRoll(0,0)

    val offtext: String
        get() = getString(R.string.normal)

    open val ontext: String
        get() = getString(R.string.sequence)

    protected open val knockoutStamina: Int
        get() = Int.MAX_VALUE

    protected open val damage: () -> Int
        get() = { 2 }

    protected open val defaultEnemyDamage: String
        get() = "2"

    protected val currentEnemy: Combatant
        get() {
            for (combatant in combatPositions) {
                if (combatant.isActive) {
                    return combatant
                }
            }
            throw IllegalStateException("No active enemy combatant found.")
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_adventure_combat, container, false)

        init()

        return rootView
    }

    @Synchronized
    protected open fun combatTurn() {
        if (combatPositions.size == 0)
            return

        if (combatStarted == false) {
            combatStarted = true
            combatTypeSwitch!!.isClickable = false
        }

        if (combatMode == SEQUENCE) {
            sequenceCombatTurn()
        } else {
            standardCombatTurn()
        }


        if (combatPositions.isEmpty()) {
            resetCombat(false)
        }
    }

    protected open fun switchLayoutCombatStarted() {
        addCombatButton!!.visibility = View.GONE
        combatTypeSwitch!!.visibility = View.GONE
        startCombatButton!!.visibility = View.GONE
        resetButton!!.visibility = View.GONE
        resetButton2!!.visibility = View.VISIBLE
        testLuckButton!!.visibility = View.VISIBLE
        combatTurnButton!!.visibility = View.VISIBLE
    }

    protected fun init() {

        combatResult = rootView!!.findViewById(R.id.combatResult)
        combatTurnButton = rootView!!.findViewById(R.id.attackButton)
        startCombatButton = rootView!!.findViewById(R.id.startCombat)
        addCombatButton = rootView!!.findViewById(R.id.addCombatButton)
        combatTypeSwitch = rootView!!.findViewById(R.id.combatType)
        resetButton = rootView!!.findViewById(R.id.resetCombat)
        resetButton2 = rootView!!.findViewById(R.id.resetCombat2)
        testLuckButton = rootView!!.findViewById(R.id.testLuckButton)

        combatTypeSwitch!!.textOff = offtext
        combatTypeSwitch!!.textOn = ontext
        combatTypeSwitch!!.setOnCheckedChangeListener(CombatTypeSwitchChangeListener())

        combatantsListView = rootView!!.findViewById(R.id.combatants)
        combatantListAdapter = CombatantListAdapter(this.activity, combatPositions)
        combatantsListView!!.adapter = combatantListAdapter

        addCombatButton!!.setOnClickListener { addCombatButtonOnClick() }

        resetButton!!.setOnClickListener {
            resetCombat(true)
            refreshScreensFromResume()
        }

        resetButton2!!.setOnClickListener {
            resetCombat(true)
            refreshScreensFromResume()
        }

        combatTurnButton!!.setOnClickListener { combatTurn() }

        startCombatButton!!.setOnClickListener { startCombat() }

        testLuckButton!!.setOnClickListener(OnClickListener {
            val adv = activity as Adventure?

            if (draw || luckTest)
                return@OnClickListener
            luckTest = true
            val result = adv!!.testLuckInternal()
            if (result) {
                combatResult!!.setText(R.string.youreLucky)
                if (hit) {
                    val combatant = currentEnemy
                    combatant.currentStamina = combatant.currentStamina - 1
                    combatant.staminaLoss = combatant.staminaLoss + 1
                    val enemyStamina = combatant.currentStamina
                    if (enemyStamina <= 0 || staminaLoss >= knockoutStamina) {
                        Adventure.showAlert(getString(R.string.defeatedOpponent), adv)
                        removeAndAdvanceCombat(combatant)
                    }
                } else {
                    adv.setCurrentStamina(adv.getCurrentStamina() + 1)
                    staminaLoss--
                }
            } else {
                combatResult!!.setText(R.string.youreUnlucky)
                if (hit) {
                    val combatant = currentEnemy
                    combatant.currentStamina = combatant.currentStamina + 1
                    combatant.staminaLoss = combatant.staminaLoss + 1
                } else {
                    adv.setCurrentStamina(adv.getCurrentStamina() - 1)
                    staminaLoss++
                }

                if (adv.getCurrentStamina() <= knockoutStamina) {
                    Adventure.showAlert(getString(R.string.knockedOut), adv)
                }

                if (adv.getCurrentStamina() == 0) {
                    onPlayerDeath(adv)
                }
            }
            refreshScreensFromResume()
        })

        refreshScreensFromResume()
    }

    protected open fun onPlayerDeath(adv: Adventure) {
        Adventure.showAlert(getString(R.string.youreDead), adv)
    }

    protected open fun sequenceCombatTurn() {

        val position = currentEnemy

        draw = false
        luckTest = false
        hit = false
        val adv = activity as Adventure?
        val skill = adv!!.combatSkillValue
        attackDiceRoll = DiceRoller.roll2D6()
        val attackStrength = attackDiceRoll?.sum + skill + position.handicap
        val enemyDiceRoll = DiceRoller.roll2D6()
        val enemyAttackStrength = enemyDiceRoll.sum + position.currentSkill
        var combatResultText = ""
        if (attackStrength > enemyAttackStrength) {
            if (!position.isDefenseOnly) {
                val suddenDeath = suddenDeath(attackDiceRoll, enemyDiceRoll)
                if (suddenDeath == null || !suddenDeath) {
                    val damage = damage()
                    position.currentStamina = Math.max(0, position.currentStamina - damage)
                    hit = true
                    combatResultText += (getString(R.string.hitEnemy) + " (" + attackDiceRoll?.sum + " + " + skill
                        + (if (position.handicap >= 0) " + " + position.handicap else "") + ") vs (" + enemyDiceRoll.sum + " + "
                        + position.currentSkill + "). (-" + damage + getString(R.string.staminaInitials) + ")")
                } else {
                    position.currentStamina = 0
                    Adventure.showAlert(getString(R.string.defeatSuddenDeath), adv)
                }
            } else {
                draw = true
                combatResultText += (getString(R.string.blockedAttack) + " (" + attackDiceRoll?.sum + " + " + skill
                    + (if (position.handicap >= 0) " + " + position.handicap else "") + ") vs (" + enemyDiceRoll.sum + " + " + position.currentSkill
                    + ")")
            }
        } else if (attackStrength < enemyAttackStrength) {
            val damage = convertDamageStringToInteger(position.damage)
            adv.setCurrentStamina(Math.max(0, adv.getCurrentStamina() - damage))
            combatResultText += (getString(R.string.youWereHit) + " (" + attackDiceRoll?.sum + " + " + skill + (if (position.handicap >= 0) " + " + position.handicap else "")
                + ") vs (" + enemyDiceRoll.sum + " + " + position.currentSkill + "). (-" + damage + R.string.staminaInitials + ")")
        } else {

            combatResultText += R.string.bothMissed
            draw = true
        }

        if (position.currentStamina == 0) {
            removeAndAdvanceCombat(position)
            combatResultText += "\n"
            combatResultText += getString(R.string.defeatedEnemy)
        } else {
            advanceCombat(position)
        }

        if (adv.getCurrentStamina() == 0) {
            removeAndAdvanceCombat(position)
            combatResultText += "\n"
            combatResultText += getString(R.string.youveDied)
        }

        combatResult!!.text = combatResultText

        refreshScreensFromResume()
    }

    protected fun advanceCombat(combatant: Combatant): Combatant? {
        val index = combatPositions.indexOf(combatant)
        var currentEnemy: Combatant? = null

        if (!combatPositions.isEmpty()) {
            if (index <= combatPositions.size - 2) {
                currentEnemy = combatPositions[index + 1]
            } else {
                currentEnemy = combatPositions[0]
            }
            changeActiveCombatant(currentEnemy)
        } else {
            resetCombat(false)
        }

        return currentEnemy
    }

    private fun changeActiveCombatant(currentEnemy: Combatant?) {
        for (combatant in combatPositions) {
            combatant.isActive = false
        }
        currentEnemy!!.isActive = true
    }

    protected open fun removeAndAdvanceCombat(combatant: Combatant) {
        combatPositions.remove(combatant)
        if (!combatPositions.isEmpty()) {
            val currentEnemy = advanceCombat(combatant)
            currentEnemy!!.isDefenseOnly = false
        }
    }

    protected open fun standardCombatTurn() {
        val position = currentEnemy

        // if (!finishedCombats.contains(currentCombat)) {
        draw = false
        luckTest = false
        hit = false
        val adv = activity as Adventure?
        attackDiceRoll = DiceRoller.roll2D6()
        val skill = adv!!.combatSkillValue
        val attackStrength = attackDiceRoll.sum + skill + position.handicap
        val enemyDiceRoll = DiceRoller.roll2D6()
        val enemyAttackStrength = enemyDiceRoll.sum + position.currentSkill
        var combatResultText = ""
        if (attackStrength > enemyAttackStrength) {
            val suddenDeath = suddenDeath(attackDiceRoll, enemyDiceRoll)
            if (suddenDeath == null || !suddenDeath) {
                val damage = damage()

                position.currentStamina = Math.max(0, position.currentStamina - damage)
                position.staminaLoss = position.staminaLoss + damage
                hit = true
                combatResultText += (getString(R.string.hitEnemy) + " (" + attackDiceRoll.sum + " + " + skill
                    + (if (position.handicap >= 0) " + " + position.handicap else "") + ") vs (" + enemyDiceRoll.sum + " + " + position.currentSkill
                    + ").")
            } else {
                position.currentStamina = 0
                Adventure.showAlert(R.string.defeatSuddenDeath, adv)
            }
        } else if (attackStrength < enemyAttackStrength) {
            val damage = convertDamageStringToInteger(position.damage)
            staminaLoss += damage
            adv.setCurrentStamina(Math.max(0, adv.getCurrentStamina() - damage))
            combatResultText += (getString(R.string.youWereHit) + " (" + attackDiceRoll.sum + " + " + skill + (if (position.handicap >= 0) " + " + position.handicap else "")
                + ") vs (" + enemyDiceRoll.sum + " + " + position.currentSkill + ").")
        } else {

            combatResult!!.setText(R.string.bothMissed)
            draw = true
        }

        combatResultText += endOfTurnAction()

        if (position.currentStamina == 0 || position.staminaLoss >= knockoutStamina) {
            removeAndAdvanceCombat(position)
            combatResultText += "\n"
            combatResultText += getString(R.string.defeatedEnemy)
        }

        if (staminaLoss >= knockoutStamina) {
            removeAndAdvanceCombat(position)
            Adventure.showAlert(R.string.knockedOut, adv)
        }

        if (adv.getCurrentStamina() == 0) {
            removeAndAdvanceCombat(position)
            onPlayerDeath(adv)
        }

        combatResult!!.text = combatResultText

        refreshScreensFromResume()
    }

    open fun endOfTurnAction(): String = ""

    protected open fun addCombatButtonOnClick() {
        addCombatButtonOnClick(R.layout.component_add_combatant)
    }

    protected fun addCombatButtonOnClick(layoutId: Int) {

        val adv = activity as Adventure?

        val addCombatantView = adv!!.layoutInflater.inflate(layoutId, null)

        val mgr = adv.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (combatStarted)
            return

        val builder = AlertDialog.Builder(adv)

        builder.setTitle(R.string.addEnemy).setCancelable(false).setNegativeButton(R.string.close) { dialog, id ->
            mgr.hideSoftInputFromWindow(addCombatantView.windowToken, 0)
            dialog.cancel()
        }

        builder.setPositiveButton(R.string.ok) { dialog, which -> confirmCombatAction(mgr, addCombatantView) }

        val alert = builder.create()

        val skillValue = addCombatantView.findViewById<EditText>(R.id.enemySkillValue)

        alert.setView(addCombatantView)

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        skillValue.requestFocus()

        alert.show()
    }

    protected open fun confirmCombatAction(mgr: InputMethodManager, addCombatantView: View) {
        mgr.hideSoftInputFromWindow(addCombatantView.windowToken, 0)

        val enemySkillValue = addCombatantView.findViewById<EditText>(R.id.enemySkillValue)
        val enemyStaminaValue = addCombatantView.findViewById<EditText>(R.id.enemyStaminaValue)
        val handicapValue = addCombatantView.findViewById<EditText>(R.id.handicapValue)

        val skillS = enemySkillValue.text.toString()
        val staminaS = enemyStaminaValue.text.toString()
        val skill: Int
        val stamina: Int
        val handicap: Int
        try {
            skill = Integer.valueOf(skillS)
            stamina = Integer.valueOf(staminaS)
            handicap = if (handicapValue.text != null && handicapValue.text.isNotEmpty()) Integer.valueOf(handicapValue.text.toString()) else 0
        } catch (e: NumberFormatException) {
            Adventure.showAlert(getString(R.string.youMustFillSkillAndStamina), this@AdventureCombatFragment.activity!!)
            return
        }


        addCombatant(skill, stamina, handicap, defaultEnemyDamage)
    }

    protected fun addCombatant(skill: Int, stamina: Int, handicap: Int, damage: String) {

        val combatPosition = Combatant(
            stamina,
            skill,
            handicap,
            combatPositions.size > 0,
            damage,
            combatPositions.size == 0
        )
        if (!combatPositions.isEmpty())
            combatPosition.isDefenseOnly = true
        combatPositions.add(combatPosition)
        refreshScreensFromResume()
    }

    override fun refreshScreensFromResume() {
        if (combatantListAdapter != null)
            combatantListAdapter!!.notifyDataSetChanged()
    }

    protected open fun resetCombat(clearResult: Boolean) {

        staminaLoss = 0

        combatPositions.clear()
        combatantListAdapter!!.notifyDataSetChanged()
        combatMode = NORMAL
        combatStarted = false
        if (clearResult) {
            combatResult!!.text = ""
        }

        combatTypeSwitch!!.isClickable = true
        combatTypeSwitch!!.isChecked = false

        switchLayoutReset(clearResult)

        refreshScreensFromResume()
    }

    protected open fun switchLayoutReset(clearResult: Boolean) {
        addCombatButton!!.visibility = View.VISIBLE
        combatTypeSwitch!!.visibility = View.VISIBLE
        startCombatButton!!.visibility = View.VISIBLE
        resetButton!!.visibility = if (clearResult) View.GONE else View.VISIBLE

        testLuckButton!!.visibility = View.GONE
        combatTurnButton!!.visibility = View.GONE
        resetButton2!!.visibility = View.GONE
    }

    protected open fun combatTypeSwitchBehaviour(isChecked: Boolean): String {
        combatMode = if (isChecked) SEQUENCE else NORMAL
        return combatMode
    }

    protected open fun startCombat() {
        if (!combatPositions.isEmpty()) {
            combatTurn()

            switchLayoutCombatStarted()
        }
    }

    protected open fun suddenDeath(diceRoll: DiceRoll, enemyDiceRoll: DiceRoll): Boolean? {
        return false
    }

    inner class Combatant(
        var currentStamina: Int,
        var currentSkill: Int,
        var handicap: Int = 0,
        var isDefenseOnly: Boolean,
        var damage: String,
        var isActive: Boolean
    ) {
        var staminaLoss: Int = 0
    }

    private inner class CombatTypeSwitchChangeListener : OnCheckedChangeListener {

        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            combatTypeSwitchBehaviour(isChecked)
        }
    }

    companion object {

        val NORMAL = "NORMAL"
        val SEQUENCE = "SEQUENCE"

        public fun convertDamageStringToInteger(damage: String): Int {
            return if (damage == "1D6") {
                DiceRoller.rollD6()
            } else {
                Integer.parseInt(damage)
            }
        }
    }
}
