package pt.joaomneto.titancompanion.adventure.impl.fragments.ss

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.util.Spell

/**
 * Created by Joao Neto on 23-05-2017.
 */

enum class SSSpell constructor(private val labelIdInner : Int, private val actionInner: (Adventure) -> Unit) : Spell{


    STAMINA(R.string.ssSpellStamina, { adv ->
        adv.currentStamina = adv
                .currentStamina!! + adv.initialStamina!! / 2
        if (adv.currentStamina > adv
                .initialStamina)
            adv.currentStamina = adv
                    .initialStamina

    }),
    SKILL(R.string.ssSpellSkill, { adv ->
        adv.currentSkill = adv.currentSkill!! + adv.initialSkill!! / 2
        if (adv.currentSkill > adv
                .initialSkill)
            adv.currentSkill = adv
                    .initialSkill
    }),
    LUCK(R.string.ssSpellLuck, { adv ->
        adv.currentLuck = adv.currentLuck!! + adv.initialLuck!! / 2
        if (adv.currentLuck > adv
                .initialLuck)
            adv.currentLuck = adv.initialLuck
    }),
    FIRE(R.string.ssSpellFire, { adv -> }),
    ICE(R.string.ssSpellIce, { adv -> }),
    ILLUSION(R.string.ssSpellIllusion, { adv -> }),
    FRIENDSHIP(R.string.ssSpellFriendship, { adv -> }),
    GROWTH(R.string.ssSpellGrowth, { adv -> }),
    BLESS(R.string.ssSpellBless, { adv -> }),
    FEAR(R.string.ssSpellFear, { adv -> }),
    WITHERING(R.string.ssSpellWithering, { adv -> }),
    CURSE(R.string.ssSpellCurse, { adv -> });

    override fun getLabelId(): Int{
        return labelIdInner
    }

    override fun getAction(): (Adventure) -> Unit{
        return actionInner
    }


}
