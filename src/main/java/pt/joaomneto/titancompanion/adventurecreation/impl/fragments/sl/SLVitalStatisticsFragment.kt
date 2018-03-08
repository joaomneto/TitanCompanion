package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_33sl_adventurecreation_vital_statistics.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment

class SLVitalStatisticsFragment : VitalStatisticsFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_33sl_adventurecreation_vital_statistics, container, false)
    }

    fun getRatingValue()
        : TextView? {
        return ratingValue
    }
}
