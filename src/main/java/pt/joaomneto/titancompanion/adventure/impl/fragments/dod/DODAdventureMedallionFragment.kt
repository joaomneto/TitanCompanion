package pt.joaomneto.titancompanion.adventure.impl.fragments.dod

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.BackgroundColorSpan
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_35dod_adventure_medallions.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.DODAdventure
import pt.joaomneto.titancompanion.adventure.impl.MedallionStatus
import java.lang.Math.max
import java.lang.Math.min

class DODAdventureMedallionFragment : AdventureFragment() {

    var rootView: View? = null
    private lateinit var mAdapter: MedallionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(
            R.layout.fragment_35dod_adventure_medallions, container, false
        )

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = MedallionListAdapter(
            (this.activity as DODAdventure)
        )

        mAdapter.notifyDataSetChanged()

        medallionList.adapter = mAdapter

        refreshScreensFromResume()
    }

    override fun refreshScreensFromResume() {
        mAdapter.notifyDataSetChanged()
    }
}

class MedallionListAdapter(private val adv: DODAdventure) : ArrayAdapter<MedallionStatus>(
    adv,
    -1,
    adv.medallions
), View.OnCreateContextMenuListener {

    override fun onCreateContextMenu(p0: ContextMenu?, p1: View?, p2: ContextMenu.ContextMenuInfo?) {
        TODO("not implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val medallionView = inflater.inflate(R.layout.component_35dod_medallion, parent, false)

        val medallionName = medallionView.rootView.findViewById<TextView>(R.id.medallionName)
        val statsMedallionValue = medallionView.rootView.findViewById<TextView>(R.id.statsMedallionValue)
        val plusMedallionButton = medallionView.rootView.findViewById<TextView>(R.id.plusMedallionButton)
        val minusMedallionButton = medallionView.rootView.findViewById<TextView>(R.id.minusMedallionButton)

        val medallion = adv.medallions[position]

        medallionName.setText(medallion.medallion.textId)

        plusMedallionButton.setOnClickListener {
            medallion.power = min(medallion.power+1, 3)
            this.notifyDataSetInvalidated()
        }

        minusMedallionButton.setOnClickListener {
            medallion.power = max(medallion.power-1, 0)
            this.notifyDataSetInvalidated()
        }

        medallionName.setOnClickListener {
            if (medallion.achieved) {
                medallion.achieved = false
                medallion.power = 3
            } else {
                medallion.achieved = true
                medallion.power = 3
            }
            this.notifyDataSetInvalidated()
        }

        statsMedallionValue.text = medallion.power.toString()
        statsMedallionValue.visibility = if (medallion.achieved) VISIBLE else INVISIBLE
        plusMedallionButton.visibility = if (medallion.achieved) VISIBLE else INVISIBLE
        minusMedallionButton.visibility = if (medallion.achieved) VISIBLE else INVISIBLE

        if (medallion.achieved) {
            val spannableString = Spannable.Factory.getInstance().newSpannable(medallionName.text)
            spannableString.setSpan(
                    BackgroundColorSpan(0xFF33B5E5.toInt()),
                    0,
                    medallionName.text.length,
                    SPAN_EXCLUSIVE_EXCLUSIVE
            )
            medallionName.text = spannableString
        }else{
            medallionName.setText(medallion.medallion.textId)
        }

        return medallionView
    }
}

