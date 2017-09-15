package pt.joaomneto.titancompanion.adventure.impl.fragments.aod

import android.content.Context

class CustomSoldiersDivision(val type: String, override var quantity: Int) : SoldiersDivision {

    override var initialQuantity: Int = 0

    init {
        this.initialQuantity = quantity
    }

    override fun resetToInitialValues() {
        quantity = initialQuantity
    }

    override fun incrementAllValues() {
        quantity += 5
        initialQuantity = quantity
    }

    override fun decrementAllValues() {
        quantity = Math.max(0, quantity - 5)
        initialQuantity = quantity
    }

    override fun getLabel(ctx: Context): String {
        return type
    }

    override fun getCategory(): String {
        return type
    }
}