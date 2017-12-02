package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_33sl_adventurecreation_vital_statistics.*
import pt.joaomneto.titancompanion.R

class SLVitalStatisticsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_33sl_adventurecreation_vital_statistics, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun getRatingValue()
        : TextView? {
        return ratingValue
    }

}
