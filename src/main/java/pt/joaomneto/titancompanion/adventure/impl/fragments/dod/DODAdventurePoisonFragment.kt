package pt.joaomneto.titancompanion.adventure.impl.fragments.dod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_35dod_adventure_poison.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure.Companion.getResId
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.DODAdventure

class DODAdventurePoisonFragment : AdventureFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(
                R.layout.fragment_35dod_adventure_poison,
                container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.context as DODAdventure

        plusPoisonButton.setOnClickListener({
            adv.poison = minOf(24, adv.poison + 1)
            refreshScreensFromResume()
        })
        minusPoisonButton.setOnClickListener({
            adv.poison = maxOf(0, adv.poison - 1)
            refreshScreensFromResume()
        })
    }

    override fun refreshScreensFromResume() {
        val adv = this.context as DODAdventure

        statsPoisonValue.text = adv.poison.toString()

        poisonLayout.setImageResource(
                getResId(
                        adv,
                        "img_35dod_poison_${(this.activity as DODAdventure).poison}",
                        "drawable"
                )
        )
    }


}
