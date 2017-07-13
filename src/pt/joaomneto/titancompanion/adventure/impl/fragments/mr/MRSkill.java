package pt.joaomneto.titancompanion.adventure.impl.fragments.mr;

import java.util.function.Consumer;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

/**
 * Created by 962633 on 13-07-2017.
 */

public enum MRSkill implements Spell {
    CLIMB(R.string.skill_climb),
    HIDE(R.string.skill_hide),
    PICKLOCK(R.string.skill_picklock),
    PICKPOCKET(R.string.skill_pickpocket),
    SECRET_SIGNS(R.string.skill_secretsigns),
    SNEAK(R.string.skill_sneak),
    HIDDEN(R.string.skill_spot_hidden);

    private int name;

    MRSkill(int stringResource) {
        this.name = stringResource;
    }

    public int getName() {
        return name;
    }

    public Consumer<Adventure> getAction(){
        throw new IllegalStateException("Midnight Rogue skills have no action.");
    }
}
