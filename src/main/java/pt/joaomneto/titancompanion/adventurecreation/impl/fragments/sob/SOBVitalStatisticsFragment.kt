package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class SOBVitalStatisticsFragment : VitalStatisticsFragment() {

    lateinit var crewStrengthValue: TextView
    lateinit var crewStrikeValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_16sob_adventurecreation_vital_statistics, container, false)
        crewStrengthValue = rootView.findViewById(R.id.crewStrengthValue)
        crewStrikeValue = rootView.findViewById(R.id.crewStrikeValue)

        return rootView
    }
}
