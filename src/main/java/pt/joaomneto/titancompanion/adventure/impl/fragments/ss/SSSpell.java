package pt.joaomneto.titancompanion.adventure.impl.fragments.ss;

import java.util.function.Consumer;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

/**
 * Created by 962633 on 23-05-2017.
 */

public enum SSSpell implements Spell {


    STAMINA(R.string.ssSpellStamina, (adv) -> {
        adv.setCurrentStamina(adv
                .getCurrentStamina()
                + (adv.getInitialStamina() / 2));
        if (adv.getCurrentStamina() > adv
                .getInitialStamina())
            adv.setCurrentStamina(adv
                    .getInitialStamina());
    }), SKILL(R.string.ssSpellSkill, (adv) -> {
        adv.setCurrentSkill(adv.getCurrentSkill()
                + (adv.getInitialSkill() / 2));
        if (adv.getCurrentSkill() > adv
                .getInitialSkill())
            adv.setCurrentSkill(adv
                    .getInitialSkill());
    }), LUCK(R.string.ssSpellLuck, (adv) -> {
        adv.setCurrentLuck(adv.getCurrentLuck()
                + (adv.getInitialLuck() / 2));
        if (adv.getCurrentLuck() > adv
                .getInitialLuck())
            adv.setCurrentLuck(adv.getInitialLuck());
    }), FIRE(R.string.ssSpellFire, (adv) -> {
    }), ICE(R.string.ssSpellIce, (adv) -> {
    }), ILLUSION(R.string.ssSpellIllusion, (adv) -> {
    }), FRIENDSHIP(R.string.ssSpellFriendship, (adv) -> {
    }), GROWTH(R.string.ssSpellGrowth, (adv) -> {
    }), BLESS(R.string.ssSpellBless, (adv) -> {
    }), FEAR(R.string.ssSpellFear, (adv) -> {
    }), WITHERING(R.string.ssSpellWithering, (adv) -> {
    }), CURSE(R.string.ssSpellCurse, (adv) -> {
    });

    int name;
    Consumer<Adventure> action;

    SSSpell(int name, Consumer<Adventure> action) {
        this.name = name;
        this.action = action;
    }


    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public Consumer<Adventure> getAction() {
        return action;
    }

    public void setAction(Consumer<Adventure> action) {
        this.action = action;
    }

}
