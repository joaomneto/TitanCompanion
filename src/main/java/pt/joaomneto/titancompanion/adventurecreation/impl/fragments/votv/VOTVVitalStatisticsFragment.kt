package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.bnc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class VOTVVitalStatisticsFragment : VitalStatisticsFragment() {

    var faithValue: TextView = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_38votv_adventurecreation_vital_statistics, container, false)
        faithValue = rootView.findViewById(R.id.faithValue)

        return rootView
    }

    override fun onDestroyView() {
        faithValue = null
        super.onDestroyView()
    }
}
