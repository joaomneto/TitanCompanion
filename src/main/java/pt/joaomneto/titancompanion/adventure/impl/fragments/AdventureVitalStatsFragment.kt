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

open class AdventureVitalStatsFragment: AdventureFragment<Adventure<*, *, *>>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(
                R.layout.fragment_adventure_vitalstats, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plusStaminaButton.setOnClickListener {
            adventure.performIncreaseStamina()
        }

        plusSkillButton.setOnClickListener {
            adventure.performIncreaseSkill()
        }

        plusLuckButton.setOnClickListener {
            adventure.performIncreaseLuck()
        }

        minusStaminaButton.setOnClickListener {
            adventure.performDecreaseStamina()
        }

        minusSkillButton.setOnClickListener {
            adventure.performDecreaseSkill()
        }

        minusLuckButton.setOnClickListener {
            adventure.performDecreaseLuck()
        }

        plusProvisionsButton.setOnClickListener {
            adventure.performIncreaseProvisions()
        }

        minusProvisionsButton.setOnClickListener {
            adventure.performDecreaseProvisions()
        }


        statsStaminaValue.setOnClickListener{
            adventure.performSetInitialStamina(this)
        }

        statsSkillValue.setOnClickListener{
            adventure.performSetInitialSkill(this)
        }

        statsLuckValue.setOnClickListener{
            adventure.performSetInitialLuck(this)
        }

        usePotionButton.setOnClickListener {
            adventure.performUsePotion()
        }

        buttonSavePoint.setOnClickListener {
            adventure.performSavegame()
        }

        buttonTestLuck.setOnClickListener {
            adventure.performTestLuck()
        }

        buttonTestSkill.setOnClickListener {
           adventure.performTestSkill()
        }

        buttonConsumeProvisions.setOnClickListener {
            adventure.performConsumeProvisions()
        }



        refreshScreen()
    }

    override fun refreshScreen() {
        statsSkillValue.text = adventure.state.currentSkill.toString()
        statsStaminaValue.text = adventure.state.currentStamina.toString()
        statsLuckValue.text = adventure.state.currentLuck.toString()
        provisionsValue.text = adventure.state.provisions.toString()

        if (usePotionButton != null) {
            if (adventure.state.standardPotion < 0) {
                usePotionButton.visibility = View.GONE
            } else {
                usePotionButton.visibility = View.VISIBLE
                usePotionButton.isEnabled = adventure.state.standardPotionValue > 0
            }
        }

        val stringArray = resources.getStringArray(R.array.standard_potion_list)

        if (adventure.state.standardPotion == -1) {
            usePotionButton.visibility = View.GONE
        } else {
            usePotionButton.text = getString(R.string.usePotion, stringArray[adventure.state.standardPotion])
        }

        buttonConsumeProvisions.setText(adventure.consumeProvisionText)
        provisionsText.setText(adventure.provisionsText)

        buttonConsumeProvisions.isEnabled = adventure.state.provisions > 0

        if (adventure.state.provisionsValue < 0) {
            plusProvisionsButton.visibility = View.INVISIBLE
            minusProvisionsButton.visibility = View.INVISIBLE
            provisionsValue.visibility = View.INVISIBLE
            provisionsText.visibility = View.INVISIBLE
            buttonConsumeProvisions.visibility = View.INVISIBLE
        }
    }
}
