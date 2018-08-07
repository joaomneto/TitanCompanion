package pt.joaomneto.titancompanion.adventure

import pt.joaomneto.titancompanion.BaseFragmentActivity
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.state.*
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

abstract class StatefulAdventure<T : AdventureState>(
    override val fragmentConfiguration: Array<AdventureFragmentRunner>,
    private val kClass: KClass<T>
) : BaseFragmentActivity(
    fragmentConfiguration,
    R.layout.activity_adventure
) {
    lateinit var state: T

    fun T.increaseStamina(value: Int = 1) = copy(
        CURRENT_STAMINA,
        Math.min(currentStamina + value, initialStamina)
    )

    fun T.increaseSkill(value: Int = 1) = copy(
        CURRENT_SKILL,
        Math.min(currentSkill + value, initialSkill)
    )

    fun T.increaseLuck(value: Int = 1) = copy(CURRENT_LUCK, Math.min(currentLuck + value, initialLuck))

    fun T.increaseProvisions(value: Int = 1) = copy(PROVISIONS, provisions + value)

    fun T.increaseGold(value: Int = 1) = copy(GOLD, gold + value)

    fun T.decreaseStamina(value: Int = 1) = copy(CURRENT_STAMINA, Math.max(currentStamina - value, 0))

    fun T.decreaseSkill(value: Int = 1) = copy(CURRENT_SKILL, Math.max(currentSkill - value, 0))

    fun T.decreaseLuck(value: Int = 1) = copy(CURRENT_LUCK, Math.max(currentLuck - value, 0))

    fun T.decreaseProvisions(value: Int = 1) = copy(PROVISIONS, Math.max(provisions - value, 0))

    fun T.decreasePotion(value: Int = 1) = copy(
        STANDARD_POTION_VALUE,
        Math.max(standardPotionValue - value, 0)
    )

    fun T.decreaseGold(value: Int = 1) = copy(GOLD, Math.max(gold - value, 0))

    fun T.resetStamina() = copy(CURRENT_STAMINA, initialStamina)

    fun T.resetSkill() = copy(CURRENT_SKILL, initialSkill)

    fun T.resetLuck() = copy(CURRENT_LUCK, initialLuck)

    fun T.changeInitialStamina(value: Int = 1) = copy(INITIAL_STAMINA, Math.max(0, value))

    fun T.changeInitialSkill(value: Int = 1) = copy(INITIAL_SKILL, Math.max(0, value))

    fun T.changeInitialLuck(value: Int = 1) = copy(INITIAL_LUCK, Math.max(0, value))

    fun T.changeGold(value: Int) = copy(GOLD, Math.max(value, 0))

    fun T.addEquipment(item: String) = if (item.isNotBlank()) {
        copy(EQUIPMENT, equipment.plus(item))
    } else this

    fun T.addNote(note: String) = if (note.isNotBlank()) {
        copy(NOTES, notes.plus(note))
    } else this

    fun T.removeEquipment(item: String) = copy(EQUIPMENT, equipment.minus(item))

    fun T.removeNote(note: String) = copy(NOTES, notes.minus(note))

    fun T.startCombat() = copy(COMBAT, this.combat.copy(CombatStateKey.COMBAT_STARTED, true))

    fun T.stopCombat() = copy(COMBAT, this.combat.copy(CombatStateKey.COMBAT_STARTED, false))

    fun T.resetStaminaLoss() = copy(COMBAT, this.combat.copy(CombatStateKey.STAMINA_LOSS, 0))

    fun T.clearCombatants() = copy(
        COMBAT,
        this.combat.copy(CombatStateKey.COMBAT_POSITIONS, emptyList<AdventureCombatFragment.Combatant>())
    )

    fun T.resetCombatMode() = copy(
        COMBAT,
        this.combat.copy(CombatStateKey.COMBAT_MODE, CombatMode.NORMAL)
    )

    fun T.changeCombatLuckTest(value: Boolean) = copy(
        COMBAT,
        this.combat.copy(CombatStateKey.LUCK_TEST, value)
    )

    fun T.decreaseCombatStaminaLoss(value: Int = 1) = copy(
        COMBAT,
        this.combat.copy(CombatStateKey.STAMINA_LOSS, this.combat.staminaLoss - value)
    )

    fun T.increaseCombatStaminaLoss(value: Int = 1) = copy(
        COMBAT,
        this.combat.copy(CombatStateKey.STAMINA_LOSS, this.combat.staminaLoss + value)
    )

    fun T.removeCombatant(combatant: AdventureCombatFragment.Combatant): T {
        var newState = this.copy(
            COMBAT,
            this.combat.copy(
                CombatStateKey.COMBAT_POSITIONS,
                this.combat.combatPositions.filter { it.id != combatant.id })
        )

        newState.combat.combatPositions.forEachIndexed { index, combatant ->
            newState = newState.modifyCombatant(combatant.copy(isDefenseOnly = index != 0))
        }

        return newState
    }

    fun T.changeActiveCombatant(activeIndex: Int) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_POSITIONS,
            this.combat.combatPositions
                .mapIndexed { index,
                              combatant ->
                    combatant.copy(isActive = index == activeIndex)
                })
    )

    fun T.modifyCombatant(combatant: AdventureCombatFragment.Combatant) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_POSITIONS,
            this.combat.combatPositions
                .map {
                    if (it.id == combatant.id)
                        combatant
                    else
                        it
                })
    )

    fun T.combatDraw(draw: Boolean = true) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.DRAW,
            draw
        )
    )

    fun T.combatHit(hit: Boolean = true) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.HIT,
            hit
        )
    )

    fun T.combatMode(mode: CombatMode) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_MODE,
            mode
        )
    )

    fun T.combatResult(result: String) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_RESULT,
            result
        )
    )

    fun T.combatLuckTest(luckTest: Boolean) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.LUCK_TEST,
            luckTest
        )
    )

    fun T.combatDiceRoll() = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.ATTACK_DICE_ROLL,
            DiceRoller.roll2D6()
        )
    )

    open fun T.resetCombat(): T = this
        .resetStaminaLoss()
        .stopCombat()
        .clearCombatants()
        .resetCombatMode()
        .combatResult("")

    open fun T.removeCombatantAndAdvanceCombat(

    ): T {
        val enemyToRemove = this.combat.currentEnemy
        return this.advanceCombat().removeCombatant(enemyToRemove)
    }

    open fun T.advanceCombat(
    ): T {

        var newState = this

        val previousCombatant = newState.combat.currentEnemy
        if (newState.combat.combatPositions.isNotEmpty()) {
            val newCombatantIndex: Int
            newState = if (previousCombatant == newState.combat.combatPositions.last()) {
                newState.changeActiveCombatant(0)
            } else {
                newCombatantIndex = newState.combat.combatPositions.indexOf(previousCombatant) + 1
                newState.changeActiveCombatant(newCombatantIndex)
            }
            newState.combat.combatPositions.forEachIndexed { index, combatant ->
                newState = newState.modifyCombatant(combatant.copy(isDefenseOnly = index != 0))
            }
        } else {
            newState.resetCombat()
        }

        return newState
    }

    open fun T.addCombatant(combatant: AdventureCombatFragment.Combatant) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_POSITIONS,
            this.combat.combatPositions.plus(combatant)
        )
    )

    open fun T.decreaseCombatantStamina(combatant: AdventureCombatFragment.Combatant) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_POSITIONS,
            this.combat.combatPositions.map {
                if (it.id == combatant.id)
                    it.copy(currentStamina = combatant.currentStamina - 1)
                else it
            }
        )
    )

    open fun T.increaseCombatantStamina(combatant: AdventureCombatFragment.Combatant) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_POSITIONS,
            this.combat.combatPositions.map {
                if (it.id == combatant.id)
                    it.copy(currentStamina = combatant.currentStamina + 1)
                else it
            }
        )
    )

    open fun T.decreaseCombatantSkill(combatant: AdventureCombatFragment.Combatant) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_POSITIONS,
            this.combat.combatPositions.map {
                if (it.id == combatant.id)
                    it.copy(currentSkill = combatant.currentSkill - 1)
                else it
            }
        )
    )

    open fun T.increaseCombatantSkill(combatant: AdventureCombatFragment.Combatant) = copy(
        COMBAT,
        this.combat.copy(
            CombatStateKey.COMBAT_POSITIONS,
            this.combat.combatPositions.map {
                if (it.id == combatant.id)
                    it.copy(currentSkill = combatant.currentSkill + 1)
                else it
            }
        )
    )

    open fun T.copy(
        key: StateKey, value: Any
    ) = kClass.primaryConstructor!!.call(
        values.toMap().plus(key to value)
    )

    open fun AdventureState.CombatState.copy(
        key: StateKey, value: Any
    ) = AdventureState.CombatState(
        values.toMap().plus(key to value)
    )
}
