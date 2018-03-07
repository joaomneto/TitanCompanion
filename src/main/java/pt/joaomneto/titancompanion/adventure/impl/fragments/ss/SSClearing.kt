package pt.joaomneto.titancompanion.adventure.impl.fragments.ss

import pt.joaomneto.titancompanion.R

/**
 * Created by 962633 on 21-08-2017.
 */

enum class SSClearing(val resource: Int) {
    CLEARING_1(R.id.clearing_1),
    CLEARING_10(R.id.clearing_10),
    CLEARING_11(R.id.clearing_11),
    CLEARING_12(R.id.clearing_12),
    CLEARING_13(R.id.clearing_13),
    CLEARING_14(R.id.clearing_14),
    CLEARING_15(R.id.clearing_15),
    CLEARING_16(R.id.clearing_16),
    CLEARING_17(R.id.clearing_17),
    CLEARING_18(R.id.clearing_18),
    CLEARING_19(R.id.clearing_19),
    CLEARING_20(R.id.clearing_20),
    CLEARING_21(R.id.clearing_21),
    CLEARING_23(R.id.clearing_23),
    CLEARING_24(R.id.clearing_24),
    CLEARING_25(R.id.clearing_25),
    CLEARING_26(R.id.clearing_26),
    CLEARING_27(R.id.clearing_27),
    CLEARING_28(R.id.clearing_28),
    CLEARING_29(R.id.clearing_29),
    CLEARING_3(R.id.clearing_3),
    CLEARING_30(R.id.clearing_30),
    CLEARING_32(R.id.clearing_32),
    CLEARING_33(R.id.clearing_33),
    CLEARING_34(R.id.clearing_34),
    CLEARING_35(R.id.clearing_35),
    CLEARING_4(R.id.clearing_4),
    CLEARING_5(R.id.clearing_5),
    CLEARING_6(R.id.clearing_6),
    CLEARING_7(R.id.clearing_7),
    CLEARING_8(R.id.clearing_8),
    CLEARING_9(R.id.clearing_9);

    companion object {
        @JvmStatic
        fun getIfExists(value: String): SSClearing? {
            return SSClearing.values().filter { ssClearing -> ssClearing.name.equals(value) }.firstOrNull()
        }
    }
}
