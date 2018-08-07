package pt.joaomneto.titancompanion.adventurecreation.impl.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.component_basic_vital_stats.*

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation

open class VitalStatisticsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_adventurecreation_vital_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.buttonRollStats).setOnClickListener { v: View ->
            (this.activity as AdventureCreation).rollStats(
                v
            )
        }

        view.findViewById<View>(R.id.buttonSaveAdventure).setOnClickListener { v: View -> (this.activity as AdventureCreation).saveAdventure() }
    }
}
