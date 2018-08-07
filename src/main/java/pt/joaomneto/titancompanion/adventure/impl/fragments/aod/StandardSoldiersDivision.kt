package pt.joaomneto.titancompanion.adventure.impl.fragments.aod

import android.content.Context

data class StandardSoldiersDivision(
    val type: DefaultDivision,
    override val quantity: Int,
    override val initialQuantity: Int = quantity
) : SoldiersDivision {

    override val category
        get() = type.name

    override fun resetToInitialValues() = copy(quantity = initialQuantity)

    override fun incrementAllValues() = copy(quantity = quantity + 5, initialQuantity = quantity + 5)

    override fun decrementAllValues() = copy(
        quantity = Math.max(0, quantity - 5),
        initialQuantity = Math.max(0, quantity - 5)
    )

    override fun getLabel(ctx: Context): String {
        return ctx.getString(type.labelId)
    }
}
