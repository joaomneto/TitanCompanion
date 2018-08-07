package pt.joaomneto.titancompanion.adventure.impl.fragments.aod

import android.content.Context

/**
 * Created by 962633 on 13-09-2017.
 */
interface SoldiersDivision {

    val quantity: Int
    val initialQuantity: Int
    val category: String

    fun resetToInitialValues(): SoldiersDivision
    fun incrementAllValues(): SoldiersDivision
    fun decrementAllValues(): SoldiersDivision
    fun getLabel(ctx: Context): String
}
