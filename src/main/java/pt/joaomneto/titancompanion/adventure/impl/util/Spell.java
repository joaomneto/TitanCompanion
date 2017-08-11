package pt.joaomneto.titancompanion.adventure.impl.util;

import java.util.function.Consumer;

import pt.joaomneto.titancompanion.adventure.Adventure;

/**
 * Created by Joao Neto on 23-05-2017.
 */

public interface Spell extends TranslatableEnum {

    Consumer<Adventure> getAction();
    String name();
}
