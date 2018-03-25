package pt.joaomneto.titancompanion.adventure.impl.fragments.sota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_32sota_adventure_time.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.Adventure.Companion.getResId
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.SOTAAdventure

class SOTAAdventureTimeFragment : AdventureFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(
                R.layout.fragment_32sota_adventure_time,
                container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.context as SOTAAdventure

        plusTimeButton.setOnClickListener({
            adv.time = minOf(20, adv.time + 1)
            when (adv.time) {
                18 -> Adventure.Companion.showAlert(adv.getString(R.string.goToParagraph, 57), adv)
                20 -> Adventure.Companion.showAlert(adv.getString(R.string.goToParagraph, 326), adv)
            }
            refreshScreensFromResume()
        })
        minusTimeButton.setOnClickListener({
            adv.time = maxOf(0, adv.time - 1)
            refreshScreensFromResume()
        })
    }

    override fun refreshScreensFromResume() {
        val adv = this.context as SOTAAdventure

        statsTimeValue.text = adv.time.toString()

        if (adv.time > 0)
            hourglassLayout.setImageResource(getResId(adv, "img_32sota_hourglass_${(this.activity as SOTAAdventure).time}", "drawable"))
        else
            hourglassLayout.setImageResource(R.drawable.img_32sota_hourglass)
    }
}
