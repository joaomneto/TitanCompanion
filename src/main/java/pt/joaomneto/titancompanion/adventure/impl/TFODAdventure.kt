package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.util.AdventureFragmentRunner

open class TFODAdventure(override val fragmentConfiguration: Array<AdventureFragmentRunner> = TWOFMAdventure.DEFAULT_FRAGMENTS) : TWOFMAdventure(
    fragmentConfiguration
)
