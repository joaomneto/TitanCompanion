package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.component_basic_buttons_bar.*
import kotlinx.android.synthetic.main.component_basic_provisions_stats.*
import kotlinx.android.synthetic.main.component_basic_vital_stats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment

open class AdventureVitalStatsFragment : AdventureFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(
                R.layout.fragment_adventure_vitalstats, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adv = activity as Adventure<*>

        plusStaminaButton.setOnClickListener {
            adv.performIncreaseStamina(this)
        }

        plusSkillButton.setOnClickListener {
            adv.performIncreaseSkill(this)
        }

        plusLuckButton.setOnClickListener {
            adv.performIncreaseLuck(this)
        }

        minusStaminaButton.setOnClickListener {
            adv.performDecreaseStamina(this)
        }

        minusSkillButton.setOnClickListener {
            adv.performDecreaseSkill(this)
        }

        minusLuckButton.setOnClickListener {
            adv.performDecreaseLuck(this)
        }

        plusProvisionsButton.setOnClickListener {
            adv.performIncreaseProvisions(this)
        }

        minusProvisionsButton.setOnClickListener {
            adv.performDecreaseProvisions(this)
        }


        statsStaminaValue.setOnClickListener{
            adv.performSetInitialStamina(this)
        }

        statsSkillValue.setOnClickListener{
            adv.performSetInitialSkill(this)
        }

        statsLuckValue.setOnClickListener{
            adv.performSetInitialLuck(this)
        }

        usePotionButton.setOnClickListener({
            adv.performUsePotion(this)
        })

        buttonSavePoint.setOnClickListener {
            adv.performSavegame(this)
        }

        buttonTestLuck.setOnClickListener {
            adv.performTestLuck(this)
        }

        buttonTestSkill.setOnClickListener {
           adv.performTestSkill(this)
        }

        buttonConsumeProvisions.setOnClickListener {
            adv.performConsumeProvisions(this)
        }



        refreshScreen()
    }

    override fun refreshScreen() {
        val adv = activity as Adventure<*>

        statsSkillValue.text = adv.state.currentSkill.toString()
        statsStaminaValue.text = adv.state.currentStamina.toString()
        statsLuckValue.text = adv.state.currentLuck.toString()
        provisionsValue.text = adv.state.provisions.toString()

        if (usePotionButton != null) {
            if (adv.state.standardPotion < 0) {
                usePotionButton.visibility = View.GONE
            } else {
                usePotionButton.visibility = View.VISIBLE
                usePotionButton.isEnabled = adv.state.standardPotionValue > 0
            }
        }

        val stringArray = resources.getStringArray(R.array.standard_potion_list)

        if (adv.state.standardPotion == -1) {
            usePotionButton.visibility = View.GONE
        } else {
            usePotionButton.text = getString(R.string.usePotion, stringArray[adv.state.standardPotion])
        }

        buttonConsumeProvisions.setText(adv.consumeProvisionText)
        provisionsText.setText(adv.provisionsText)

        buttonConsumeProvisions.isEnabled = adv.state.provisions > 0

        if (adv.state.provisionsValue < 0) {
            plusProvisionsButton.visibility = View.INVISIBLE
            minusProvisionsButton.visibility = View.INVISIBLE
            provisionsValue.visibility = View.INVISIBLE
            provisionsText.visibility = View.INVISIBLE
            buttonConsumeProvisions.visibility = View.INVISIBLE
        }
    }
}
