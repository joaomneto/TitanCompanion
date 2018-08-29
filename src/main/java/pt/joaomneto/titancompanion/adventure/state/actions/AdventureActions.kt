package pt.joaomneto.titancompanion.adventure.state.actions

import net.attilaszabo.redux.Action
import java.util.Properties
import java.util.stream.Stream

object AdventureActions {
    data class LoadGame(val properties: Properties): Action
    data class IncreaseStamina(val value: Int = 1) : Action
    data class IncreaseSkill(val value: Int = 1) : Action
    data class IncreaseLuck(val value: Int = 1) : Action
    data class IncreaseProvisions(val value: Int = 1) : Action
    data class IncreaseGold(val value: Int = 1) : Action
    data class DecreaseStamina(val value: Int = 1) : Action
    data class DecreaseSkill(val value: Int = 1) : Action
    data class DecreaseLuck(val value: Int = 1) : Action
    data class DecreaseProvisions(val value: Int = 1) : Action
    data class DecreaseGold(val value: Int = 1) : Action
    data class DecreasePotion(val value: Int = 1) : Action
    class ResetStamina : Action
    class ResetSkill : Action
    class ResetLuck : Action
    data class ChangeInitialStamina(val value: Int) : Action
    data class ChangeInitialSkill(val value: Int) : Action
    data class ChangeInitialLuck(val value: Int) : Action
    data class ChangeGold(val value: Int) : Action
    data class AddEquipment(val value: String) : Action
    data class AddNote(val value: String) : Action
    data class RemoveEquipment(val value: String) : Action
    data class RemoveNote(val value: String) : Action
}
