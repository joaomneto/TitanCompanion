package pt.joaomneto.titancompanion.adventure.impl.fragments.pof;

import java.util.function.Consumer;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.POFAdventure;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

/**
 * Created by 962633 on 23-05-2017.
 */

public enum POFSpell implements Spell {


    PROTECT(R.string.pofSpellProtect, POFSpellConstants.DEFAULT_ACTION),
    ILLUSION(R.string.pofSpellIllusion, POFSpellConstants.DEFAULT_ACTION),
    WEAKEN(R.string.pofSpellWeaken, POFSpellConstants.DEFAULT_ACTION),
    LEVITATION(R.string.pofSpellLevitation, POFSpellConstants.DEFAULT_ACTION),
    FINDING(R.string.pofSpellFinding, POFSpellConstants.DEFAULT_ACTION),
    FIRE(R.string.pofSpellFire, POFSpellConstants.DEFAULT_ACTION);

    int name;
    Consumer<Adventure> action;

    POFSpell(int name, Consumer<Adventure> action) {
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

    private static class POFSpellConstants {
        public static Consumer<Adventure> DEFAULT_ACTION = (adv) -> {
            POFAdventure pofAdv = (POFAdventure) adv;

            pofAdv.setCurrentPower(Math.max(pofAdv
                    .getCurrentPower()
                    - 1, 0));
        };
    }

}
