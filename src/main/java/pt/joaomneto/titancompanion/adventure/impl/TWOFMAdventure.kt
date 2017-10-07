package pt.joaomneto.titancompanion.adventure.impl

import java.io.BufferedWriter
import java.io.IOException

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import android.os.Bundle
import android.view.Menu

open class TWOFMAdventure : Adventure() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        bw.write("standardPotion=" + standardPotion + "\n")
        bw.write("standardPotionValue=" + standardPotionValue
                + "\n")
        bw.write("gold=" + gold + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        standardPotion = Integer.valueOf(savedGame
                .getProperty("standardPotion"))
        standardPotionValue = Integer.valueOf(savedGame
                .getProperty("standardPotionValue"))
        gold = Integer.valueOf(savedGame.getProperty("gold"))

    }


}
