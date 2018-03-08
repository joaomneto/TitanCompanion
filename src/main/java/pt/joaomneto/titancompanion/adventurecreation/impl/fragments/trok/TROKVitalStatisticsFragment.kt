package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.trok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class TROKVitalStatisticsFragment : VitalStatisticsFragment() {

    lateinit var shieldsValue: TextView
    lateinit var weaponsValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_15trok_adventurecreation_vital_statistics, container, false)
        shieldsValue = rootView.findViewById(R.id.shieldsValue)
        weaponsValue = rootView.findViewById(R.id.weaponsValue)

        return rootView
    }
}
