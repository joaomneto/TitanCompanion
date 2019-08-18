package pt.joaomneto.titancompanion.adventurecreation.impl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Spinner
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.TWOFMAdventureCreation
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.LocaleHelper
import java.util.ArrayList
import java.util.HashMap

class PotionsFragment : Fragment() {

    private val defaultPotionDosage: Int
        get() = if ("fr" == LocaleHelper.getLanguage(this.activity) || (this.activity as AdventureCreation).gamebook == FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN) 2 else 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_adventurecreation_potions, container, false)

        val potionList = ArrayList<Map<String, String>>()

        val stringArray = resources.getStringArray(R.array.standard_potion_list)

        for (string in stringArray) {
            val potion = HashMap<String, String>()
            potion["potion"] = string
            potionList.add(potion)
        }

        val mAdapter = SimpleAdapter(
            activity,
            potionList,
            R.layout.potions_item,
            arrayOf("potion"),
            intArrayOf(R.id.potion_name)
        )

        val lView = rootView.findViewById<ListView>(R.id.potionList)
        lView.adapter = mAdapter
        lView.setSelector(R.drawable.row_selector)

        lView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ -> (activity as TWOFMAdventureCreation).potion = position }

        val spinner = rootView.findViewById<Spinner>(R.id.potionDosesSpinner)

        val adapter = ArrayAdapter.createFromResource(
            this.activity!!.actionBar!!.themedContext,
            R.array.potion_doses,
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                (activity as TWOFMAdventureCreation).potionDoses = i + 1
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                (activity as TWOFMAdventureCreation).potionDoses = defaultPotionDosage
            }
        }

        //        1 dose == position 0 of the spinner
        spinner.setSelection(defaultPotionDosage - 1)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.buttonSaveAdventure).setOnClickListener { v: View -> (this.activity as AdventureCreation).saveAdventure() }
    }

    companion object {

        val SKILL_POTION = 0
        val STRENGTH_POTION = 1
        val FORTUNE_POTION = 2
    }
}
