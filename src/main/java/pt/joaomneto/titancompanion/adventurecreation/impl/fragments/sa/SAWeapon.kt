package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.util.TranslatableEnum

/**
 * Created by Joao Neto on 07-08-2017.
 */

enum class SAWeapon private constructor(private val labelId: Int, val weaponPoints: Int) : TranslatableEnum {

    ELECTRIC_LASH(R.string.saElectricLash, 1),
    ASSAULT_BLASTER(R.string.saAssaultBlaster, 3),
    GRENADE(R.string.saGrenade, 1),
    GRAVITY_BOMB(R.string.saGravityBomb, 3),
    TWO_ARMOR_POINTS(R.string.sa2xArmor, 1);

    override fun getLabelId(): Int {
        return labelId
    }

    companion object {

        val INITIALWEAPONS = arrayOf(ELECTRIC_LASH, ASSAULT_BLASTER)
    }
}
