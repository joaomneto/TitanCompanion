package pt.joaomneto.titancompanion.adventure.twofm

import android.view.Menu
import net.attilaszabo.redux.Store
import net.attilaszabo.redux.implementation.java.BaseStore
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.state.bean.StandardAdventureState
import pt.joaomneto.titancompanion.adventure.state.reducer.AdventureStateReducer
import pt.joaomneto.titancompanion.adventure.state.bean.CombatState
import pt.joaomneto.titancompanion.adventure.state.bean.DummyState
import pt.joaomneto.titancompanion.adventure.state.bean.MainState
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.util.Properties

open class TWOFMAdventure(
    override val fragmentConfiguration: Array<AdventureFragmentRunner> = DEFAULT_FRAGMENTS
) : Adventure<StandardAdventureState, DummyState, DummyState>(fragmentConfiguration) {

    override val customState = DummyState.DUMMY_STATE

    override val state: MainState
        get() = store.getState().mainState

    override val combatState: CombatState
        get() = store.getState().combatState

    override val customCombatState = DummyState.DUMMY_STATE

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }


    override fun generateStoreFromSavegame(properties: Properties): Store<StandardAdventureState> {
        val initialState = StandardAdventureState(
            mainState = MainState.fromProperties(
                properties
            )
        )
        return BaseStore.Creator<StandardAdventureState>().create(
            initialState,
            AdventureStateReducer()
        )
    }
}
