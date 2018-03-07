package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.strider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class STRIDERVitalStatisticsFragment : VitalStatisticsFragment() {

    lateinit var fearValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(
            R.layout.fragment_27strider_adventurecreation_vital_statistics,
            container,
            false
        )
        fearValue = rootView.findViewById(R.id.fearValue)

        return rootView
    }
}
