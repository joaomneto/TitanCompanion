package pt.joaomneto.titancompanion.adventure.impl.fragments.com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.STRIDERAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class COMAdventureKuddamFragment : AdventureVitalStatsFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(
                R.layout.fragment_30com_adventure_kuddam, container, false)

        initialize(rootView)

        refreshScreensFromResume()

        return rootView
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()

    }

}
