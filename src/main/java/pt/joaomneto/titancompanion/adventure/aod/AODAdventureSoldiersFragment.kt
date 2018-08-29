package pt.joaomneto.titancompanion.adventure.aod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_36aod_adventure_soldiers.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.R.string.enemyForces
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.aod.adapter.SoldierListAdapter

class AODAdventureSoldiersFragment : AdventureFragment<AODAdventure>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_36aod_adventure_soldiers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aod_division_plusEnemyForcesButton.setOnClickListener {
            adventure.increaseEnemyForces()
        }

        aod_division_minusEnemyForcesButton.setOnClickListener {
            adventure.decreaseEnemyForcers()
        }


        aod_soldiers_buttonAddNewSoldiers.setOnClickListener { adventure.addSoldiers() }

        aod_soldiers_soldiersList.adapter = SoldierListAdapter(
            adventure,
            adventure.customState.soldiers.divisions
        )


        aod_soldiers_buttonStartSkirmish.setOnClickListener { adventure.commitForces() }

        aod_soldiers_buttonCommitForces.setOnClickListener { adventure.startBattle() }

        aod_soldiers_buttonCombatTurn.setOnClickListener { adventure.combatTurn() }

        val resetBattleOnClickListener = OnClickListener { adventure.resetCombat() }
        aod_soldiers_buttonResetBattle.setOnClickListener(resetBattleOnClickListener)
        aod_soldiers_buttonCancelBattle.setOnClickListener(resetBattleOnClickListener)

        refreshScreen()
    }

    fun alternateButtonsLayout() {
        aod_soldiers_ButtonsNormal.visibility = if (adventure.isBattleNormal) View.VISIBLE else View.GONE
        aod_soldiers_ButtonsBattle.visibility = if (adventure.isBattleStarted) View.VISIBLE else View.GONE
        aod_soldiers_combatResultText.visibility = if (adventure.isBattleStarted) View.VISIBLE else View.GONE
        aod_soldiers_ButtonsStaging.visibility = if (adventure.isBattleStaging) View.VISIBLE else View.GONE
        aod_soldiers_armyTitle.setText(if (adventure.isBattleNormal) R.string.currentArmy else if (adventure.isBattleStaging) R.string.selectForces else R.string.skirmishForces)
        aod_soldiers_enemyTroopsValue.visibility = if (adventure.isBattleStarted) View.VISIBLE else View.GONE
        aod_soldiers_enemyTroopsLabel.visibility = if (adventure.isBattleStarted) View.VISIBLE else View.GONE
    }

    override fun refreshScreen() {

        val adapter = aod_soldiers_soldiersList.adapter as SoldierListAdapter<*>

        val index = aod_soldiers_soldiersList.firstVisiblePosition
        val v = aod_soldiers_soldiersList.getChildAt(0)
        val top = if (v == null) 0 else v.top - aod_soldiers_soldiersList.paddingTop

        if (adventure.isBattleStarted) {
            aod_soldiers_soldiersList.adapter = SoldierListAdapter(
                adventure,
                adventure.battleSoldiers
            )
        } else {
            aod_soldiers_soldiersList.adapter = SoldierListAdapter(
                adventure,
                adventure.customState.soldiers.divisions
            )
        }
        adapter.notifyDataSetChanged()


        aod_division_enemyForcesValue.text = "$enemyForces"
        aod_soldiers_enemyTroopsValue.text = "$enemyForces"

        alternateButtonsLayout()


        aod_soldiers_soldiersList.setSelectionFromTop(index, top)
    }
}
