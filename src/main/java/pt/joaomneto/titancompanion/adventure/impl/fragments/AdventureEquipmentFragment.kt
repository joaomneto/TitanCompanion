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

class AdventureEquipmentFragment: AdventureFragment<Adventure<*, *, *>>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_adventure_equipment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        goldLabel.setText(adventure.currencyName)

        goldValue.setOnClickListener {
            adventure.performChangeGoldWithAlert()
        }

        buttonAddEquipment.setOnClickListener {
            adventure.performAddEquipmentWithAlert()
        }

        val adapter = ArrayGeneratorAdapter(
            adventure, android.R.layout.simple_list_item_1,
            android.R.id.text1
        ) { adventure.state.equipment }
        equipmentList.adapter = adapter

        equipmentList.onItemLongClickListener = OnItemLongClickListener { _, _, arg2, _ ->
            adventure.performRemoveEquipmentWithAlert(arg2)
        }

        minusGoldButton.setOnClickListener {
            adventure.performDecreaseGold( 1)
        }

        plusGoldButton.setOnClickListener {
            adventure.performIncreaseGold( 1)
        }
    }

    private fun getAdapter() = (equipmentList.adapter as ArrayGeneratorAdapter<String>)

    override fun refreshScreen() {
        getAdapter().notifyDataSetChanged()
        goldValue.text = adventure.state.gold.toString()
    }
}
