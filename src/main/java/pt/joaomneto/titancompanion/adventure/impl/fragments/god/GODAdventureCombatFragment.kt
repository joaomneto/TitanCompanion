package pt.joaomneto.titancompanion.adventure.impl.fragments.god

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.GODAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

class GODAdventureCombatFragment : AdventureCombatFragment() {

    var demon: Boolean = false
    var stoneCrystal: Boolean = false
    var poisonActivated: Boolean = false

    override val damage: () -> Int
        get() = {((activity as GODAdventure).weapon.damage(attackDiceRoll, demon, stoneCrystal))}


    override fun endOfTurnAction(): String{
        val adv = activity as GODAdventure
        if(adv.weapon == GODWeapon.ASSASSINS_STILETTO && adv.poison>0 && currentEnemy.staminaLoss>0){
            poisonActivated = true
            adv.poison = adv.poison - 1
        }

        if(poisonActivated){
            currentEnemy.currentStamina = currentEnemy.currentStamina -1
            return " "+getString(R.string.poisonBlade)
        }

        return ""
    }

    override fun addCombatButtonOnClick() {
        addCombatButtonOnClick(R.layout.component_64god_add_combatant)
    }

    override fun confirmCombatAction(mgr: InputMethodManager, addCombatantView: View) {
        super.confirmCombatAction(mgr, addCombatantView)
        demon = addCombatantView.findViewById<CheckBox>(R.id.demonValue).isChecked
        stoneCrystal = addCombatantView.findViewById<CheckBox>(R.id.stoneCrystal).isChecked
    }

    override fun resetCombat(clearResult: Boolean) {
        super.resetCombat(clearResult)
        demon = false
        stoneCrystal = false
        poisonActivated = false
    }
}
