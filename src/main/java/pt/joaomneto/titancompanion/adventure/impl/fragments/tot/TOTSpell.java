package pt.joaomneto.titancompanion.adventure.impl.fragments.tot;

import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.function.Consumer;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.util.Spell;

/**
 * Created by Joao Neto on 23-05-2017.
 */

public enum TOTSpell implements Spell {

    OPEN_DOOR(R.string.totSpellOpenDoor, (adv) -> {
        adv.changeStamina(-2);
    }), CREATURE_SLEEP(R.string.totSpellCreatureSlepp, (adv) -> {
        adv.changeStamina(-1);
    }), MAGIC_ARROW(R.string.totSpellMagicArrow, (adv) -> {
        adv.changeStamina(-2);
    }), LANGUAGE(R.string.totSpellLanguage, (adv) -> {
        adv.changeStamina(-1);
    }), READ_SYMBOLS(R.string.totSpellReadSymbols, (adv) -> {
        adv.changeStamina(-1);
    }), LIGHT(R.string.totSpellLight, (adv) -> {
        adv.changeStamina(-2);
    }), FIRE(R.string.totSpellFire, (adv) -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(adv);
        builder.setTitle(R.string.totSpellStaminaCost)
                .setCancelable(false)
                .setNegativeButton(R.string.number_2,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                adv.changeStamina(-2);
                                dialog.cancel();
                            }
                        });
        builder.setPositiveButton(R.string.number_1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        adv.changeStamina(-1);
                        dialog.cancel();
                    }

                });

        AlertDialog alert = builder.create();
        alert.show();
    }), JUMP(R.string.totSpellJump, (adv) -> {
        adv.changeStamina(-3);
    }),
    DETECT_TRAP(R.string.totSpellDetectTrap, (adv) -> {
        adv.changeStamina(-2);
    }), CREATE_WATER(R.string.totSpellWater, (adv) -> {

    });

    int name;
    Consumer<Adventure> action;

    TOTSpell(int name, Consumer<Adventure> action) {
        this.name = name;
        this.action = action;
    }


    public int getLabelId() {
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
