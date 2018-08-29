package pt.joaomneto.titancompanion.adventure.state.bean

import java.util.Properties

class DummyState: State {
    override fun toSavegameProperties(): Properties = EMPTY_PROPERTIES

    companion object {
        val DUMMY_STATE = DummyState()
        val EMPTY_PROPERTIES = Properties()
    }
}
