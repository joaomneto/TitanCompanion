package pt.joaomneto.titancompanion.adventure.impl.fragments.com

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_30com_adventure_kuddam.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.COMAdventure

class COMAdventureKuddamFragment : AdventureFragment() {

    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(
            R.layout.fragment_30com_adventure_kuddam, container, false
        )

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        com_kuddam_geshrak.setOnClickListener { addOrRemoveToKuddamList(Kuddam.GESHRAK) }
        com_kuddam_barkek.setOnClickListener { addOrRemoveToKuddamList(Kuddam.BARKEK) }
        com_kuddam_churka.setOnClickListener { addOrRemoveToKuddamList(Kuddam.CHURKA) }
        com_kuddam_friankara.setOnClickListener { addOrRemoveToKuddamList(Kuddam.FRIANKARA) }
        com_kuddam_griffkek.setOnClickListener { addOrRemoveToKuddamList(Kuddam.GRIFFKEK) }
        com_kuddam_gurskut.setOnClickListener { addOrRemoveToKuddamList(Kuddam.GURSKUT) }
        com_kuddam_kahhrac.setOnClickListener { addOrRemoveToKuddamList(Kuddam.KAHHRAC) }

        refreshScreensFromResume()
    }

    override fun refreshScreensFromResume() {
        val adv: COMAdventure = this.context as COMAdventure

        com_kuddam_geshrak.paintFlags = 0
        com_kuddam_barkek.paintFlags = 0
        com_kuddam_churka.paintFlags = 0
        com_kuddam_friankara.paintFlags = 0
        com_kuddam_griffkek.paintFlags = 0
        com_kuddam_gurskut.paintFlags = 0
        com_kuddam_kahhrac.paintFlags = 0

        adv.kuddamKilled.forEach({ kuddam ->
            val text = adv.findViewById<TextView>(kuddam.viewId)
            text.paintFlags = text.paintFlags or STRIKE_THRU_TEXT_FLAG
        })
    }

    private fun addOrRemoveToKuddamList(kuddam: Kuddam) {
        val adv: COMAdventure = this.context as COMAdventure
        if (!adv.kuddamKilled.contains(kuddam))
            adv.kuddamKilled = adv.kuddamKilled.plus(kuddam)
        else
            adv.kuddamKilled = adv.kuddamKilled.minus(kuddam)

        refreshScreensFromResume()
    }
}
