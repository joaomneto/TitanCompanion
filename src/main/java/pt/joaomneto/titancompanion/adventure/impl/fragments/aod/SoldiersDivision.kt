package pt.joaomneto.titancompanion.adventure.impl.fragments.aod

import android.content.Context

/**
 * Created by 962633 on 13-09-2017.
 */
interface SoldiersDivision {

    var quantity: Int
    var initialQuantity: Int

    fun resetToInitialValues()
    fun incrementAllValues()
    fun decrementAllValues()
    fun getLabel(ctx: Context): String
    fun getCategory(): String
}