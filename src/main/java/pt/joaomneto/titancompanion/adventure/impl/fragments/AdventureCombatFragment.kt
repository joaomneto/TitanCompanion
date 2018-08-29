package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_adventure_combat.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.adapter.CombatantListAdapter
import pt.joaomneto.titancompanion.adventure.values.CombatMode.SEQUENCE
import pt.joaomneto.titancompanion.adventure.state.bean.CombatState
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.util.DiceRoller
import java.util.UUID

open class AdventureCombatFragment: AdventureFragment<Adventure<*, *, *>>() {
    protected var combatantListAdapter: CombatantListAdapter? = null
    protected var handicap = 0

    val offtext: String
        get() = getString(R.string.normal)

    open val ontext: String
        get() = getString(R.string.sequence)

    protected open val MainState: MainState
        get() = adventure.state

    protected open val combatState: CombatState
        get() = adventure.combatState

    private var keepCombatText = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_adventure_combat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        combatType.textOff = offtext
        combatType.textOn = ontext
        combatType.setOnCheckedChangeListener(combatTypeChangeListener())

        combatantListAdapter = CombatantListAdapter(this.activity!!, { combatState.combatPositions })
        combatants.adapter = combatantListAdapter

        addCombatButton.setOnClickListener {
            addCombatButtonOnClick()
        }

        resetCombat.setOnClickListener {
            keepCombatText = true
            adventure.performResetCombat()
        }

        resetCombat2.setOnClickListener {
            keepCombatText = true
            adventure.performResetCombat()
        }

        attackButton.setOnClickListener {
            adventure.performCombatTurn()
        }

        startCombat.setOnClickListener {
            adventure.performStartCombat()
        }

        testLuckButton.setOnClickListener {
            adventure.performTestLuckCombat()
        }

        refreshScreen()
    }

    protected open fun addCombatButtonOnClick() {
        addCombatButtonOnClick(R.layout.component_add_combatant)
    }

    protected fun addCombatButtonOnClick(layoutId: Int) {

        val addCombatantView = adventure.layoutInflater.inflate(layoutId, null)

        val mgr = adventure.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (combatState.combatStarted)
            return

        val builder = AlertDialog.Builder(adventure)

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


        addCombatant(skill, stamina, handicap, adventure.defaultEnemyDamage)
    }

    protected fun addCombatant(skill: Int, stamina: Int, handicap: Int, damage: String) {

        val combatPosition = Combatant(
            stamina,
            skill,
            handicap,
            combatState.combatPositions.isNotEmpty(),
            damage,
            combatState.combatPositions.isEmpty()
        )

        adventure.performAddCombatant(combatPosition)
        refreshScreen()
    }

    override fun refreshScreen() {
        combatantListAdapter?.notifyDataSetChanged()

        if (combatState.combatStarted) {
            addCombatButton.visibility = View.GONE
            combatType.visibility = View.GONE
            startCombat.visibility = View.GONE
            resetCombat.visibility = View.GONE
            resetCombat2.visibility = View.VISIBLE
            testLuckButton.visibility = View.VISIBLE
            attackButton.visibility = View.VISIBLE
        } else {
            addCombatButton.visibility = View.VISIBLE
            combatType.visibility = View.VISIBLE
            startCombat.visibility = View.VISIBLE
            resetCombat.visibility = if (keepCombatText) View.VISIBLE else View.GONE

            testLuckButton.visibility = View.GONE
            attackButton.visibility = View.GONE
            resetCombat2.visibility = View.GONE
        }

        combatResult.text = combatState.combatResult

        combatType.isClickable = true
        combatType.isChecked = combatState.combatMode == SEQUENCE
        combatType.isClickable = !combatState.combatStarted

        keepCombatText = false
    }

    data class Combatant(
        val currentStamina: Int,
        val currentSkill: Int,
        val handicap: Int = 0,
        val isDefenseOnly: Boolean,
        val damage: String,
        val isActive: Boolean,
        val staminaLoss: Int = 0,
        val id: String = UUID.randomUUID().toString()
    )

    private inner class combatTypeChangeListener : OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            adventure.performSwitchCombatMode(isChecked)
        }
    }

    companion object {
        fun convertDamageStringToInteger(damage: String): Int {
            return if (damage == "1D6") {
                DiceRoller.rollD6()
            } else {
                Integer.parseInt(damage)
            }
        }
    }
}
