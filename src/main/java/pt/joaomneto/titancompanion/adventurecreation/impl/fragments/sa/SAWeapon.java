package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa;

import pt.joaomneto.titancompanion.R;

/**
 * Created by Joao Neto on 07-08-2017.
 */

public enum SAWeapon {

    ELECTRIC_LASH(R.string.saElectricLash, 1),
    ASSAULT_BLASTER(R.string.saAssaultBlaster, 3),
    GRENADE(R.string.saGrenade, 1),
    GRAVITY_BOMB(R.string.saGravityBomb, 3),
    TWO_ARMOR_POINTS(R.string.sa2xArmor, 1);

    private int labelId;
    private int weaponPoints;

    public static final SAWeapon[] INITIALWEAPONS = new SAWeapon[]{ELECTRIC_LASH, ASSAULT_BLASTER};

    SAWeapon(int labelId, int weaponPoints) {
        this.labelId = labelId;
        this.weaponPoints = weaponPoints;
    }

    public int getLabelId() {
        return labelId;
    }

    public int getWeaponPoints() {
        return weaponPoints;
    }
}
