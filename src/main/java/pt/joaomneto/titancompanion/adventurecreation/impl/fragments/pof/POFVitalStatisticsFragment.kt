package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.pof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class POFVitalStatisticsFragment : VitalStatisticsFragment() {

    lateinit var powerValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_28pof_adventurecreation_vital_statistics, container, false)
        powerValue = rootView.findViewById(R.id.powerValue)

        return rootView
    }
}
