package pt.joaomneto.titancompanion.adventure.impl.fragments.mr

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.util.Spell

/**
 * Created by 962633 on 13-07-2017.
 */

enum class MRSkill constructor(val labelIdInner: Int) : Spell {
    CLIMB(R.string.skill_climb),
    HIDE(R.string.skill_hide),
    PICKLOCK(R.string.skill_picklock),
    PICKPOCKET(R.string.skill_pickpocket),
    SECRET_SIGNS(R.string.skill_secretsigns),
    SNEAK(R.string.skill_sneak),
    HIDDEN(R.string.skill_spot_hidden);

    override fun getAction(): (Adventure) -> Unit {
        throw IllegalStateException("Midnight Rogue skills have no action.")
    }

    override fun getLabelId(): Int {
        return labelIdInner
    }
}
