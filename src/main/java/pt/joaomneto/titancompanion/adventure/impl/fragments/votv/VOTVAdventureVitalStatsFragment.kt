package pt.joaomneto.titancompanion.adventure.impl.fragments.votv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_38votv_adventure_vitalstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.VOTVAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment

class VOTVAdventureVitalStatsFragment : AdventureVitalStatsFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_38votv_adventure_vitalstats, container, false
        )
    }

    val adventure
        get() = this.activity as VOTVAdventure

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        minusFaithButton.setOnClickListener(adventure.decreaseFaithListener)
        plusFaithButton.setOnClickListener(adventure.increaseFaithListener)
    }

    override fun refreshScreensFromResume() {
        super.refreshScreensFromResume()
        statsFaithValue?.text = "${adventure.currentFaith}"
    }
}
