package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.s

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class SVitalStatisticsFragment : VitalStatisticsFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_53s_adventurecreation_vital_statistics, container, false)
    }
}
