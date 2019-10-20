package pt.joaomneto.titancompanion.adventure.impl.fragments.strider

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_adventure_equipment.*
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment

/**
 * Created by Joao Neto on 30-05-2017.
 */

class STRIDERAdventureEquipmentFragment : AdventureEquipmentFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goldLabel.visibility = View.GONE
        minusGoldButton?.visibility = View.GONE
        plusGoldButton?.visibility = View.GONE
        goldValue?.visibility = View.GONE
    }
}
