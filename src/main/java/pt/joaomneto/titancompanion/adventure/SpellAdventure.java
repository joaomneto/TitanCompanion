package pt.joaomneto.titancompanion.adventure;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

public abstract class SpellAdventure extends Adventure {


    List<Spell> spells = new ArrayList<Spell>();;


    protected String arrayToStringSpells(List<Spell> spells) {
        String _string = "";

        if (!spells.isEmpty()) {
            for (Spell spell : spells) {
                _string += spell+ "#";
            }
            _string = _string.substring(0, _string.length() - 1);
        }
        return _string;
    }

    protected List<Spell> stringToArraySpells(String spells) {
        List<Spell> elements = new ArrayList<Spell>();

        if (spells != null) {
            List<String> list = Arrays.asList(spells.split("#"));
            for (String string : list) {
                if (!string.isEmpty()) {
                        elements.add(getSpellForString(string));
                }
            }
        }

        return elements;
    }

    private Spell getSpellForString(String string) {
        try {
            String simpleName = this.getClass().getSimpleName();
            simpleName = simpleName.substring(0, simpleName.length() - "Adventure".length());
            Class<? extends Spell> clazz = (Class<? extends Spell>) Class.forName("pt.joaomneto.titancompanion.adventure.impl.fragments."+simpleName.toLowerCase()+ "." + simpleName + "Spell");
            Method method = clazz.getMethod("valueOf", String.class);
            Spell spell = (Spell) method.invoke(null, string);
            return spell;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to instantiate Spell enum for class"+this.getClass(), e);
        }
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public abstract List<Spell> getSpellList();

    public abstract boolean isSpellSingleUse();
}
