package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.hoh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class HOHVitalStatisticsFragment : VitalStatisticsFragment() {

    lateinit var fearValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_10hoh_adventurecreation_vital_statistics, container, false)
        fearValue = rootView.findViewById(R.id.fearValue)

        return rootView
    }
}
