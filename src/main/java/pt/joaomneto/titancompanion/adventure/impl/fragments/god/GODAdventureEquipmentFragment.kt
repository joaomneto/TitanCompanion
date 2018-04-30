package pt.joaomneto.titancompanion.adventure.impl.fragments.god

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_63god_adventure_equipment.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.GODAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.DropdownStringAdapter

/**
 * Created by Joao Neto on 30-05-2017.
 */

class GODAdventureEquipmentFragment : AdventureEquipmentFragment() {

    companion object {
        val weapons = GODWeapon.values()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_63god_adventure_equipment, container, false)
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        val adapter = DropdownStringAdapter(activity, android.R.layout.simple_list_item_1, weapons.map { getString(it.displayName) } )

        weaponsSpinner.adapter = adapter

        weaponsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
                (activity as GODAdventure).weapon = weapons[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                (activity as GODAdventure).weapon = weapons[0]
            }
        }

    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        weaponsSpinner.setSelection(GODWeapon.values().indexOf((activity as GODAdventure).weapon))
    }
}
