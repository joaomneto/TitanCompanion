package pt.joaomneto.titancompanion.adventure.impl.fragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment

class CombatantListAdapter(context: Context, private val generator: () -> List<AdventureCombatFragment.Combatant>) : ArrayGeneratorAdapter<AdventureCombatFragment.Combatant>(context, -1, -1, generator) {

    private val adventure = context as Adventure<*,*,*>

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val combatantView = inflater.inflate(R.layout.component_combatant, parent, false)

        val combatTextStamina = combatantView.rootView.findViewById<TextView>(R.id.combatTextStaminaValue)

        val combatTextSkill = combatantView.rootView.findViewById<TextView>(R.id.combatTextSkillValue)

        val combatPosition = {generator()[position]}

        val radio = combatantView.rootView.findViewById<RadioButton>(R.id.combatSelected)
        radio.isChecked = combatPosition().isActive


        combatTextSkill.text = "" + combatPosition().currentSkill
        combatTextStamina.text = "" + combatPosition().currentStamina

        val minusCombatStamina = combatantView.findViewById<Button>(R.id.minusCombatantStaminaButton)
        val plusCombatStamina = combatantView.findViewById<Button>(R.id.plusCombatantStaminaButton)
        val minusCombatSkill = combatantView.findViewById<Button>(R.id.minusCombatantSkillButton)
        val plusCombatSkill = combatantView.findViewById<Button>(R.id.plusCombatantSkillButton)

        minusCombatStamina.setOnClickListener {
            adventure.performDecreaseCombatantStamina(combatPosition())
            combatTextStamina.text = "" + generator()[position].currentStamina
        }
        plusCombatStamina.setOnClickListener {
            adventure.performIncreaseCombatantStamina(combatPosition())
            combatTextStamina.text = "" + generator()[position].currentStamina
        }
        plusCombatSkill.setOnClickListener {
            adventure.performIncreaseCombatantSkill(combatPosition())
            combatTextSkill.text = "" + generator()[position].currentSkill
        }
        minusCombatSkill.setOnClickListener {
            adventure.performDecreaseCombatantSkill(combatPosition())
            combatTextSkill.text = "" + generator()[position].currentSkill
        }


        return combatantView
    }
}
