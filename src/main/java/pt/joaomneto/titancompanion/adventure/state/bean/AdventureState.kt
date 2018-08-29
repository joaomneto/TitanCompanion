package pt.joaomneto.titancompanion.adventure.state.bean

interface AdventureState {
    val mainState: MainState
    val combatState: CombatState
}
