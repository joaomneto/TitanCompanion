package pt.joaomneto.titancompanion.adventure.impl.fragments.dod

import android.app.AlertDialog
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.DODAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

class DODAdventureCombatFragment : AdventureCombatFragment() {

    override fun onPlayerDeath(adv: Adventure) {
        if ((adv as DODAdventure).hasMedallionPower()) {

            val builder = AlertDialog.Builder(context)
            builder
                    .setTitle(R.string.result)
                    .setMessage(R.string.youreDead)
                    .setCancelable(false)
                    .setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
                    .setPositiveButton(R.string.useMedallion) { dialog, _ ->
                        dialog.cancel()
                        useMedallion(adv)
                        adv.refreshScreens()
                    }
            val alert = builder.create()
            alert.show()
        } else {
            super.onPlayerDeath(adv)
        }
    }

    private fun useMedallion(adv: Adventure) {
        val dodadv = adv as DODAdventure
        val medallion = dodadv.getMedallionLowestPower()

        medallion?.let {
            dodadv.currentStamina = dodadv.currentStamina + 4
            it.power = it.power - 1
            if (medallion?.power == 2) {
                dodadv.currentLuck = Math.max(0, dodadv.currentLuck)
                dodadv.currentSkill = Math.max(0, dodadv.currentSkill)
                dodadv.poison = Math.min(24, dodadv.poison + 3)
                Adventure.showAlert(getString(R.string.medallionFirstTime, medallion.medallion.name), adv)
            }else{
                Adventure.showAlert(getString(R.string.medallionUsage, medallion.medallion.name), adv)
            }
        }
    }
}


