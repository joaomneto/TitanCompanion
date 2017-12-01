package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment


open class AdventureVitalStatsFragment : AdventureFragment() {


    private var statsStaminaValue: TextView? = null
    private var statsSkillValue: TextView? = null
    private var statsLuckValue: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater?.inflate(
                R.layout.fragment_adventure_vitalstats, container, false)

        statsStaminaValue = rootView?.findViewById(R.id.statsStaminaValue)
        statsSkillValue = rootView?.findViewById(R.id.statsSkillValue)
        statsLuckValue = rootView?.findViewById(R.id.statsLuckValue)

        return rootView
    }

    override fun onViewCreated(rootView: View?, savedInstanceState: Bundle?) {
        val adv = activity as Adventure

        val stringArray = resources.getStringArray(R.array.standard_potion_list)

        if (adv.standardPotion == -1) {
            usePotionButton?.visibility = View.GONE
        } else {
            usePotionButton?.text = getString(R.string.usePotion, stringArray[adv.standardPotion])
        }
        plusStaminaButton?.setOnClickListener {
            if (adv.currentStamina < adv.initialStamina)
                adv.currentStamina = adv.currentStamina + 1
            refreshScreensFromResume()
        }

        plusSkillButton?.setOnClickListener {
            if (adv.currentSkill < adv.initialSkill)
                adv.currentSkill = adv.currentSkill + 1
            refreshScreensFromResume()
        }

        statsStaminaValue = rootView?.findViewById(R.id.statsStaminaValue)
        statsSkillValue = rootView?.findViewById(R.id.statsSkillValue)
        statsLuckValue = rootView?.findViewById(R.id.statsLuckValue)

        statsStaminaValue?.setOnClickListener(setInitialStamina(adv))
        statsSkillValue?.setOnClickListener(setIntialSkill(adv))
        statsLuckValue?.setOnClickListener(setInitialLuck(adv))

        plusLuckButton?.setOnClickListener {
            if (adv.currentLuck < adv.initialLuck)
                adv.currentLuck = adv.currentLuck + 1
            refreshScreensFromResume()
        }

        plusProvisionsButton?.setOnClickListener {
            adv.provisions = adv.provisions + 1
            refreshScreensFromResume()
        }

        minusStaminaButton?.setOnClickListener {
            if (adv.currentStamina > 0)
                adv.currentStamina = adv.currentStamina - 1
            refreshScreensFromResume()
        }

        minusSkillButton?.setOnClickListener {
            if (adv.currentSkill > 0)
                adv.currentSkill = adv.currentSkill - 1
            refreshScreensFromResume()
        }

        minusLuckButton?.setOnClickListener {
            if (adv.currentLuck > 0)
                adv.currentLuck = adv.currentLuck - 1
            refreshScreensFromResume()
        }

        minusProvisionsButton?.setOnClickListener {
            if (adv.provisions > 0)
                adv.provisions = adv.provisions - 1
            refreshScreensFromResume()
        }

        buttonConsumeProvisions.text = adv.consumeProvisionText
        provisionsText.text = adv.provisionsText


        if (adv.provisionsValue == null || adv.provisionsValue < 0) {
            plusProvisionsButton?.visibility = View.INVISIBLE
            minusProvisionsButton?.visibility = View.INVISIBLE
            provisionsValue?.visibility = View.INVISIBLE
            provisionsText?.visibility = View.INVISIBLE
            buttonConsumeProvisions.visibility = View.INVISIBLE
        }

        refreshScreensFromResume()
    }

    private fun setInitialLuck(adv: Adventure): (View) -> Unit {
        return { view ->

            val alert = createAlertForInitialStatModification(R.string.setInitialLuck, { dialog, _ ->

                val value = getValueFromAlertTextField(view, dialog as AlertDialog)

                adv.initialLuck = value
            })


            alert.show()
        }
    }

    private fun setIntialSkill(adv: Adventure): (View) -> Unit {
        return { view ->

            val alert = createAlertForInitialStatModification(R.string.setInitialSkill, { dialog, _ ->

                val value = getValueFromAlertTextField(view, dialog as AlertDialog)
                adv.initialSkill = value
            })


            alert.show()
        }
    }

    private fun setInitialStamina(adv: Adventure): (View) -> Unit {
        return { view ->

            val alert = createAlertForInitialStatModification(R.string.setInitialStamina, { dialog, _ ->

                val value = getValueFromAlertTextField(view, dialog as AlertDialog)

                adv.initialStamina = value
            })


            alert.show()
            view.clearFocus()
        }
    }

    private fun getValueFromAlertTextField(view: View, dialog: AlertDialog): Int {
        val input = dialog.findViewById<EditText>(R.id.alert_editText_field)

        val value = Integer.parseInt(input.text.toString())

        (activity as Adventure).closeKeyboard(view)

        return value
    }


    fun setProvisionsValue(value: Int?) {
        this.provisionsValue?.text = value?.toString()
    }

    protected fun createAlertForInitialStatModification(dialogTitle: Int, positiveButtonListener: (DialogInterface, Int) -> Unit): AlertDialog.Builder {
        val alert = AlertDialog.Builder(context)

        alert.setTitle(dialogTitle)

        // Set an EditText view to get user input
        val input = EditText(context)
        input.id = R.id.alert_editText_field

        val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY)
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.keyListener = DigitsKeyListener.getInstance("0123456789")
        input.requestFocus()
        alert.setView(input)

        alert.setNegativeButton(R.string.cancel
        ) { _, _ -> imm.hideSoftInputFromWindow(input.windowToken, 0) }

        alert.setPositiveButton(R.string.ok, positiveButtonListener)

        alert.setOnDismissListener {
            val view = activity.currentFocus
            if (view != null) {
                val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        return alert
    }


    override fun refreshScreensFromResume() {
        val adv = activity as Adventure

        statsSkillValue?.let {

            statsSkillValue?.text = adv.currentSkill.toString()
            statsStaminaValue?.text = adv.currentStamina.toString()
            statsLuckValue?.text = adv.currentLuck.toString()
            provisionsValue?.text = adv.provisions?.toString()

            if (usePotionButton != null) {
                if (adv.standardPotion < 0) {
                    usePotionButton?.visibility = View.GONE
                } else {
                    usePotionButton?.visibility = View.VISIBLE
                    usePotionButton?.isEnabled = adv.standardPotionValue > 0
                }
            }
        }
    }


}
