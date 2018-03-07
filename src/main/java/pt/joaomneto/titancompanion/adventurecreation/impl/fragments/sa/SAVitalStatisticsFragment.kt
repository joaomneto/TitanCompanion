package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class SAVitalStatisticsFragment : VitalStatisticsFragment() {

    lateinit var armorValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_12sa_adventurecreation_vital_statistics, container, false)
        armorValue = rootView.findViewById(R.id.armorValue)

        return rootView
    }
}
