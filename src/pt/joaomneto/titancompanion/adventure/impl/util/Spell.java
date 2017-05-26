package pt.joaomneto.titancompanion.adventure.impl.util;

import java.util.function.Consumer;

import pt.joaomneto.titancompanion.adventure.Adventure;

/**
 * Created by 962633 on 23-05-2017.
 */

public interface Spell {

    public int getName();
    public Consumer<Adventure> getAction();
    public String name();
}
