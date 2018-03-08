package pt.joaomneto.titancompanion.adventure.impl.fragments.tot

import android.app.AlertDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.util.Spell

/**
 * Created by Joao Neto on 23-05-2017.
 */

enum class TOTSpell constructor(internal var labelIdInner: Int, internal var actionInner: (Adventure) -> Unit) : Spell {

    OPEN_DOOR(R.string.totSpellOpenDoor, { adv -> adv.changeStamina(-2) }),
    CREATURE_SLEEP(R.string.totSpellCreatureSlepp, { adv -> adv.changeStamina(-1) }),
    MAGIC_ARROW(R.string.totSpellMagicArrow, { adv -> adv.changeStamina(-2) }),
    LANGUAGE(R.string.totSpellLanguage, { adv -> adv.changeStamina(-1) }),
    READ_SYMBOLS(R.string.totSpellReadSymbols, { adv -> adv.changeStamina(-1) }),
    LIGHT(R.string.totSpellLight, { adv -> adv.changeStamina(-2) }),
    FIRE(R.string.totSpellFire, { adv ->
        val builder = AlertDialog.Builder(adv)
        builder.setTitle(R.string.totSpellStaminaCost)
            .setCancelable(false)
            .setNegativeButton(
                R.string.number_2
            ) { dialog, _ ->
                adv.changeStamina(-2)
                dialog.cancel()
            }
        builder.setPositiveButton(
            R.string.number_1
        ) { dialog, _ ->
            adv.changeStamina(-1)
            dialog.cancel()
        }

        val alert = builder.create()
        alert.show()
    }),
    JUMP(R.string.totSpellJump, { adv -> adv.changeStamina(-3) }),
    DETECT_TRAP(R.string.totSpellDetectTrap, { adv -> adv.changeStamina(-2) }),
    CREATE_WATER(R.string.totSpellWater, {

    });

    override fun getLabelId(): Int {
        return labelIdInner
    }

    override fun getAction(): (Adventure) -> Unit {
        return actionInner
    }
}
