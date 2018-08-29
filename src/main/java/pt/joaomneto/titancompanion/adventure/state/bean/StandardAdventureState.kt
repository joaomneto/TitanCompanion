package pt.joaomneto.titancompanion.adventure.state.bean

data class StandardAdventureState(
    override val mainState: MainState,
    override val combatState: CombatState = CombatState()
): AdventureState


