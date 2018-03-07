package pt.joaomneto.titancompanion.adventure.impl.fragments.pof

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.POFAdventure
import pt.joaomneto.titancompanion.adventure.impl.util.Spell

/**
 * Created by Joao Neto on 23-05-2017.
 */

enum class POFSpell constructor(private val labelIdInner: Int, private val actionInner: (Adventure) -> Unit) : Spell {

    PROTECT(R.string.pofSpellProtect, POFSpellConstants.DEFAULT_ACTION),
    ILLUSION(R.string.pofSpellIllusion, POFSpellConstants.DEFAULT_ACTION),
    WEAKEN(R.string.pofSpellWeaken, POFSpellConstants.DEFAULT_ACTION),
    LEVITATION(R.string.pofSpellLevitation, POFSpellConstants.DEFAULT_ACTION),
    FINDING(R.string.pofSpellFinding, POFSpellConstants.DEFAULT_ACTION),
    FIRE(R.string.pofSpellFire, POFSpellConstants.DEFAULT_ACTION);

    override fun getLabelId(): Int {
        return labelIdInner
    }

    override fun getAction(): (Adventure) -> Unit {
        return actionInner
    }

    private object POFSpellConstants {
        var DEFAULT_ACTION: (Adventure) -> Unit = { adv ->
            val pofAdv = adv as POFAdventure

            pofAdv.currentPower = Math.max(
                pofAdv
                    .currentPower - 1, 0
            )
        }
    }
}
