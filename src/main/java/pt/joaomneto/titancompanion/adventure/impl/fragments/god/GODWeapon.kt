package pt.joaomneto.titancompanion.adventure.impl.fragments.god

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll

enum class GODWeapon(
    val displayName: Int,
    val damage: (roll: DiceRoll, demon: Boolean, stoneCrystal: Boolean) -> Int
    = { _, _, _ -> 2 },
    val handicap: (demon: Boolean) -> Int = { 0 }
) {
    UNARMED(R.string.godUnarmed, { _, _, _ -> 1 }, { -2 }),
    RUSTY_BREADKNIFE(R.string.rustyBreadknife, { _, _, _ -> 1 }),
    FIRE_IRON(R.string.fireIron, handicap = { -1 }),
    PITCHFORK(R.string.pitchfork, handicap = { -1 }),
    CUDGEL(R.string.cudgel, { roll, _, _ ->
        if (roll.isDouble) {
            1
        } else {
            2
        }
    }),
    CLEAVER(R.string.cleaver),
    HARPOON(R.string.harpoon),
    SNEAKY_SWORD(R.string.sneakySword),
    ASSASSINS_STILETTO(R.string.assassinsStiletto),
    WOODMANS_AXE(R.string.woodmansAxe, { roll, won, _ ->
        if (roll.isDouble && won) {
            3
        } else {
            2
        }
    }),
    TEMPLES_GUARD_AXE(R.string.templeGuardAxe, { roll, _, _ ->
        if (roll.isDouble) {
            3
        } else {
            2
        }
    }, { 1 }),
    JEWELLED_WARHAMMER(R.string.jewelledWarhammer, { _, demon, stoneCrystal ->
        when {
            demon -> 3
            stoneCrystal -> 4
            else -> 2
        }
    }),
    KHOPESH(R.string.khopesh, { _, demon, _ ->
        if (demon) {
            3
        } else {
            2
        }
    }, { demon ->
        if (demon) {
            2
        } else {
            1
        }
    })
}
