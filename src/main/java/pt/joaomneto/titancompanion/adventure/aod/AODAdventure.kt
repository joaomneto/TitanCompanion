package pt.joaomneto.titancompanion.adventure.aod

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_36aod_adventure_soldiers.*
import net.attilaszabo.redux.Store
import net.attilaszabo.redux.implementation.java.BaseStore
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.aod.AODAdventure.Companion.ARMY
import pt.joaomneto.titancompanion.adventure.aod.AODAdventure.Companion.ENEMY
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.state.bean.CombatState
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.adventure.state.bean.State
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceNumberToWords
import pt.joaomneto.titancompanion.util.DiceRoller
import java.util.ArrayList
import java.util.HashMap
import java.util.Properties

class AODAdventure : Adventure<AODAdventureState, AODMainState, AODCombatState>(
    arrayOf(
        AdventureFragmentRunner(R.string.vitalStats, AdventureVitalStatsFragment::class),
        AdventureFragmentRunner(R.string.soldiers, AODAdventureSoldiersFragment::class),
        AdventureFragmentRunner(R.string.fights, AdventureCombatFragment::class),
        AdventureFragmentRunner(R.string.goldTreasure, AdventureEquipmentFragment::class),
        AdventureFragmentRunner(R.string.notes, AdventureNotesFragment::class)
    )
) {

    override val state: MainState
        get() = store.getState().mainState

    override val combatState: CombatState
        get() = store.getState().combatState

    override val customState: AODMainState
        get() = store.getState().aodState

    override val customCombatState: AODCombatState
        get() = store.getState().aodCombatState

    val battleState: AODAdventureBattleState
        get() = customCombatState.battleState

    val isBattleStarted: Boolean
        get() = battleState == AODAdventureBattleState.STARTED || battleState == AODAdventureBattleState.DAMAGE

    val isBattleDamage: Boolean
        get() = battleState == AODAdventureBattleState.DAMAGE

    val isBattleStaging: Boolean
        get() = battleState == AODAdventureBattleState.STAGING

    val isBattleNormal: Boolean
        get() = battleState == AODAdventureBattleState.NORMAL

    internal val battleSoldiers: List<CustomSoldiersDivision>
        get() {
            val battleSoldiers = ArrayList<CustomSoldiersDivision>()
            for (type in customCombatState.skirmishArmy.keys) {
                if (customCombatState.skirmishArmy[type]!! > 0) {
                    battleSoldiers.add(CustomSoldiersDivision(type, 0, 0))
                }
            }
            return battleSoldiers
        }

    override fun generateStoreFromSavegame(properties: Properties): Store<AODAdventureState> {
        val initialState = AODAdventureState(
            mainState = MainState.fromProperties(properties),
            aodState = AODMainState.fromProperties(properties)
        )
        return BaseStore.Creator<AODAdventureState>().create(
            initialState,
            AODAdventureStateReducer()
        )
    }

    override val currencyName = R.string.gold

    val soldiersFragment: AODAdventureSoldiersFragment?
        get() = getFragment(AODAdventureSoldiersFragment::class)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    fun decreaseEnemyForcers() {
        store.dispatch(AODCombatStateActions.DecreaseEnemyForces())
    }

    fun increaseEnemyForces() {
        store.dispatch(AODCombatStateActions.IncreaseEnemyForces())
    }

    fun startBattle() {

        aod_soldiers_combatResultText.text = ""

        if (customCombatState.enemyForces == 0) {
            Adventure.showAlert(getString(R.string.mustSetEnemyForces), this)
            return
        }

        var totalForces = 0

        for (type in customCombatState.skirmishArmy.keys) {
            totalForces += customCombatState.skirmishArmy[type]!!
        }

        if (totalForces == 0) {
            Adventure.showAlert(getString(R.string.mustCommitTroops), this)
            return
        }

        store.dispatch(AODCombatStateActions.ChangeBattleState(AODAdventureBattleState.STARTED))
        store.dispatch(
            AODCombatStateActions.ChangeBattleBalance(
            when {
                totalForces > customCombatState.enemyForces -> AODAdventureBattleBalance.SUPERIOR
                totalForces < customCombatState.enemyForces -> AODAdventureBattleBalance.INFERIOR
                else -> AODAdventureBattleBalance.EVEN
            }
        ))

    }

    fun combatTurn() {

        if (customCombatState.targetLosses == customCombatState.turnArmyLosses) {
            store.dispatch(AODCombatStateActions.ChangeBattleState(AODAdventureBattleState.STARTED))
            store.dispatch(AODCombatStateActions.ChangeTargetLosses(0))
            store.dispatch(AODCombatStateActions.ChangeTurnArmyLosses(0))
        }

        if (battleState == AODAdventureBattleState.DAMAGE) {
            Adventure.showAlert(getString(R.string.mustDistributeLosses), this)
            return
        }

        aod_soldiers_combatResultText.text = ""

        var totalForces = 0

        for (type in customCombatState.skirmishArmy.keys) {
            totalForces += customCombatState.skirmishArmy[type]!!
        }


        store.dispatch(
            AODCombatStateActions.ChangeBattleBalance(
            when {
                totalForces > customCombatState.enemyForces -> AODAdventureBattleBalance.SUPERIOR
                totalForces < customCombatState.enemyForces -> AODAdventureBattleBalance.INFERIOR
                else -> AODAdventureBattleBalance.EVEN
            }
        ))

        val diceRoll = DiceRoller.rollD6()

        val result = battleResults[customCombatState.battleBalance]!![diceRoll]!!

        aod_soldiers_combatResultText.append(
            getString(
                R.string.youveRolled,
                getString(DiceNumberToWords.convert(diceRoll))
            )
        )
        aod_soldiers_combatResultText.append("\n")
        aod_soldiers_combatResultText.append(getString(customCombatState.battleBalance.textToDisplay, totalForces, customCombatState.enemyForces))


        if (result.armyOrEnemy == "ARMY") {
            aod_soldiers_combatResultText.append(getString(R.string.aodSufferLossTropps, result.quantity))
            store.dispatch(AODCombatStateActions.ChangeBattleState(AODAdventureBattleState.DAMAGE))
            store.dispatch(AODCombatStateActions.ChangeTargetLosses(result.quantity))
        } else {
            aod_soldiers_combatResultText.append(getString(R.string.aodKillEnemyTroops, result.quantity))
            store.dispatch(AODCombatStateActions.DecreaseEnemyForces(result.quantity))
        }


        if (customCombatState.enemyForces == 0) {
            store.dispatch(AODMainStateActions.Recalculate(customCombatState.skirmishArmy))
            aod_soldiers_combatResultText.append(getString(R.string.aodDestroyEnemy))
            aod_soldiers_buttonCombatTurn.visibility = View.GONE
            aod_soldiers_buttonResetBattle.setText(R.string.closeCombat)
        }

        if (battleState == AODAdventureBattleState.DAMAGE && totalForces <= result.quantity) {
            store.dispatch(AODCombatStateActions.ClearSkirmishArmy())
            store.dispatch(AODMainStateActions.Recalculate(customCombatState.skirmishArmy))
            aod_soldiers_combatResultText.append(getString(R.string.aodArmyDestroyed))
            aod_soldiers_buttonCombatTurn.visibility = View.GONE
            aod_soldiers_buttonResetBattle.setText(R.string.closeCombat)
        }
    }

    fun commitForces() {
        store.dispatch(AODCombatStateActions.ChangeBattleState(AODAdventureBattleState.STAGING))
    }

    fun resetCombat() {
        aod_soldiers_buttonCombatTurn.visibility = View.VISIBLE
        aod_soldiers_buttonResetBattle.setText(R.string.reset)
        store.dispatch(AODCombatStateActions.ClearSkirmishArmy())
        store.dispatch(AODCombatStateActions.ChangeBattleState(AODAdventureBattleState.NORMAL))
        store.dispatch(AODCombatStateActions.ResetEnemyForces())
        store.dispatch(AODMainStateActions.ResetDivisionsToInitialValues())
    }

    fun addSoldiers() {

        val addSoldiersView = this.layoutInflater.inflate(R.layout.component_36aod_add_soldiers, null)

        val mgr = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val builder = AlertDialog.Builder(this)

        builder.setTitle(R.string.addSoldiers).setCancelable(false).setNegativeButton(R.string.close) { dialog, id ->
            mgr.hideSoftInputFromWindow(addSoldiersView.windowToken, 0)
            dialog.cancel()
        }

        builder.setPositiveButton(R.string.ok, DialogInterface.OnClickListener { _, _ ->
            mgr.hideSoftInputFromWindow(addSoldiersView.windowToken, 0)

            val soldiersType = addSoldiersView.findViewById<EditText>(R.id.aod_soldiers_type)
            val soldiersQuantity = addSoldiersView.findViewById<EditText>(R.id.aod_soldiers_quantity)

            val type = soldiersType.text.toString()
            val quantityS = soldiersQuantity.text.toString()
            var quantity: Int?
            try {
                quantity = Integer.valueOf(quantityS)
            } catch (e: NumberFormatException) {
                Adventure.showAlert(
                    getString(R.string.aodMustFillTypeAndQuantity),
                    this
                )
                return@OnClickListener
            }

            val (type1, quantity1, initialQuantity) = CustomSoldiersDivision(
                type,
                quantity!!,
                quantity
            )

            //                FIXME
            //                aodAdventure.state.getSoldiers().add(sd);

        })

        val alert = builder.create()

        val typeValue = addSoldiersView.findViewById<EditText>(R.id.aod_soldiers_type)

        alert.setView(addSoldiersView)

        mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        typeValue.requestFocus()

        alert.show()
    }

//    fun getSkirmishValueForDivision(name: String): Int? {
//        return if (customCombatState.skirmishArmy[name] != null) customCombatState.skirmishArmy[name] else 0
//    }
//
//    fun setSkirmishValueForDivision(name: String, value: Int?) {
//        customCombatState.skirmishArmy[name] = value
//    }
//
//    fun incrementTurnArmyLosses() {
//        customCombatState.turnArmyLosses += 5
//    }

    companion object {

        const val ARMY = "ARMY"
        const val ENEMY = "ENEMY"

        private val battleResults = HashMap<AODAdventureBattleBalance, Map<Int, AODAdventureBattleResults>>()

        private val evenBattleResults = mapOf(
            1 to AODAdventureBattleResults.A10,
            2 to AODAdventureBattleResults.A5,
            3 to AODAdventureBattleResults.A5,
            4 to AODAdventureBattleResults.E5,
            5 to AODAdventureBattleResults.E5,
            6 to AODAdventureBattleResults.E10
        )

        private val superiorBattleResults = mapOf(
            1 to AODAdventureBattleResults.A5,
            2 to AODAdventureBattleResults.E5,
            3 to AODAdventureBattleResults.E5,
            4 to AODAdventureBattleResults.E5,
            5 to AODAdventureBattleResults.E10,
            6 to AODAdventureBattleResults.E15
        )

        private val inferiorBattleResults = mapOf(
            1 to AODAdventureBattleResults.A15,
            2 to AODAdventureBattleResults.A10,
            3 to AODAdventureBattleResults.A5,
            4 to AODAdventureBattleResults.A5,
            5 to AODAdventureBattleResults.E5,
            6 to AODAdventureBattleResults.E5
        )

        init {
            battleResults[AODAdventureBattleBalance.EVEN] = evenBattleResults
            battleResults[AODAdventureBattleBalance.SUPERIOR] = superiorBattleResults
            battleResults[AODAdventureBattleBalance.INFERIOR] = inferiorBattleResults
        }
    }
}

data class AODMainState(
    val soldiers: Army
) : State {

    override fun toSavegameProperties() = Adventure.createSavegame("soldiers" to soldiers.stringToSaveGame)

    companion object {
        fun fromProperties(
            savedGame: Properties
        ) = AODMainState(
            Army.getInstanceFromSavedString(savedGame.getProperty("soldiers"))
        )
    }
}

data class AODCombatState(
    val battleState: AODAdventureBattleState = AODAdventureBattleState.NORMAL,
    val battleBalance: AODAdventureBattleBalance = AODAdventureBattleBalance.EVEN,
    val skirmishArmy: Map<String, Int> = emptyMap(),
    val enemyForces: Int = 0,
    val turnArmyLosses: Int = 0,
    val targetLosses: Int = 0
)

enum class AODAdventureBattleState {
    NORMAL,
    STAGING,
    STARTED,
    DAMAGE
}

enum class AODAdventureBattleBalance(var textToDisplay: Int) {
    EVEN(R.string.aodSituationEven),
    SUPERIOR(R.string.aodSituationSuperior),
    INFERIOR(R.string.aodSituationInferior)
}

enum class AODAdventureBattleResults(val quantity: Int, var armyOrEnemy: String) {
    A5(5, ARMY),
    A10(10, ARMY),
    A15(15, ARMY),
    E5(5, ENEMY),
    E10(10, ENEMY),
    E15(15, ENEMY);
}


