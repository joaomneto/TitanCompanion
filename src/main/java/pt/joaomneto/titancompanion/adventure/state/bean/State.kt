package pt.joaomneto.titancompanion.adventure.state.bean

import java.util.Properties

interface State {
    fun toSavegameProperties(): Properties
}

