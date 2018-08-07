package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import kotlinx.android.synthetic.main.fragment_adventure_equipment.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.adapter.ArrayGeneratorAdapter

class AdventureEquipmentFragment : AdventureFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_adventure_equipment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = context as Adventure<*>
        goldLabel.setText(adv.currencyName)

        goldValue.setOnClickListener {
            adv.performChangeGoldWithAlert(this)
        }

        buttonAddEquipment.setOnClickListener {
            adv.performAddEquipmentWithAlert(this)
        }

        val adapter = ArrayGeneratorAdapter(adv, android.R.layout.simple_list_item_1,
            android.R.id.text1, { adv.state.equipment })
        equipmentList.adapter = adapter

        equipmentList.onItemLongClickListener = OnItemLongClickListener { _, _, arg2, _ ->
            adv.performRemoveEquipmentWithAlert(this, arg2)
        }

        minusGoldButton.setOnClickListener {
            adv.performDecreaseGold(this, 1)
        }

        plusGoldButton.setOnClickListener {
            adv.performIncreaseGold(this, 1)
        }
    }

    private fun getAdapter() = (equipmentList.adapter as ArrayGeneratorAdapter<String>)

    override fun refreshScreen() {
        getAdapter().notifyDataSetChanged()
        val adv = activity as Adventure<*>?
        goldValue.text = adv?.state?.gold.toString()
    }
}
