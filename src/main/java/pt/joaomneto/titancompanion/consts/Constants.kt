package pt.joaomneto.titancompanion.consts

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation

import java.util.Locale

object Constants {

    private val gameBookCovers = intArrayOf(
        R.drawable.ff1,
        R.drawable.ff2,
        R.drawable.ff3,
        R.drawable.ff4,
        R.drawable.ff5,
        R.drawable.ff6,
        R.drawable.ff7,
        R.drawable.ff8,
        R.drawable.ff9,
        R.drawable.ff10,
        R.drawable.ff11,
        R.drawable.ff12,
        R.drawable.ff13,
        R.drawable.ff14,
        R.drawable.ff15,
        R.drawable.ff16,
        R.drawable.ff17,
        R.drawable.ff18,
        R.drawable.ff19,
        R.drawable.ff20,
        R.drawable.ff21,
        R.drawable.ff22,
        R.drawable.ff23,
        R.drawable.ff24,
        R.drawable.ff25,
        R.drawable.ff26,
        R.drawable.ff27,
        R.drawable.ff28,
        R.drawable.ff29,
        R.drawable.ff30,
        R.drawable.ff31,
        R.drawable.ff32,
        R.drawable.ff33,
        R.drawable.ff34,
        R.drawable.ff35,
        R.drawable.ff36,
        R.drawable.ff37,
        R.drawable.ff38,
        R.drawable.ff39,
        R.drawable.ff40,
        R.drawable.ff41,
        R.drawable.ff42,
        R.drawable.ff43,
        R.drawable.ff44,
        R.drawable.ff45,
        R.drawable.ff46,
        R.drawable.ff47,
        R.drawable.ff48,
        R.drawable.ff49,
        R.drawable.ff50,
        R.drawable.ff51,
        R.drawable.ff52,
        R.drawable.ff53,
        R.drawable.ff54,
        R.drawable.ff55,
        R.drawable.ff56,
        R.drawable.ff57,
        R.drawable.ff58,
        R.drawable.ff59,
        R.drawable.ff60,
        R.drawable.ff61,
        R.drawable.ff62,
        R.drawable.ff63,
        R.drawable.ff64
    )

    fun getGameBookCoverAddress(i: Int): Int {
        return gameBookCovers[i]
    }

    fun getCreationActivity(
        context: Context, position: Int
    ): Class<out AdventureCreation>? {
        var intentClass: Class<out AdventureCreation>? = null
        try {
            val gamebook = FightingFantasyGamebook.gamebookFromNumber(position + 1)
                ?: throw IllegalStateException("No gamebook with number ${position + 1}")
            intentClass = Class
                .forName(
                    "pt.joaomneto.titancompanion.adventurecreation.impl."
                        + gamebook.initials.toUpperCase()
                        + "AdventureCreation"
                ) as Class<out AdventureCreation>
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            return null
        }

        return intentClass
    }

    fun getRunActivity(
        context: Context,
        gamebook: FightingFantasyGamebook
    ): Class<out Adventure>? {
        var intentClass: Class<out Adventure>? = null
        try {
            intentClass = Class
                .forName(
                    "pt.joaomneto.titancompanion.adventure.impl."
                        + gamebook.initials.toUpperCase()
                        + "Adventure"
                ) as Class<out Adventure>
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return intentClass
    }
}
