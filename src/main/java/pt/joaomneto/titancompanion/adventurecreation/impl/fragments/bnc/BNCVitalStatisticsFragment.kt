package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.bnc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class BNCVitalStatisticsFragment : VitalStatisticsFragment() {

    lateinit var willpowerValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_25bnc_adventurecreation_vital_statistics, container, false)
        willpowerValue = rootView.findViewById(R.id.willpowerValue)

        return rootView
    }
}
