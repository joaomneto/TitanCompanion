package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.awf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.AWFAdventureCreation
import java.util.ArrayList
import java.util.HashMap

class AWFAdventureCreationSuperpowerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_17awf_adventure_creation_superpowers, container, false)

        val skillList = ArrayList<Map<String, String>>()

        val stringArray = resources.getStringArray(R.array.superpower_list)

        for (string in stringArray) {
            val skill = HashMap<String, String>()
            skill["skill"] = string
            skillList.add(skill)
        }

        val mAdapter = SimpleAdapter(
            activity,
            skillList,
            R.layout.potions_item,
            arrayOf("skill"),
            intArrayOf(R.id.potion_name)
        )

        val lView = rootView.findViewById<ListView>(R.id.superpowerList)
        lView.adapter = mAdapter
        lView.setSelector(R.drawable.row_selector)

        lView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> (activity as AWFAdventureCreation).superPower = stringArray[position] }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.buttonSaveAdventure).setOnClickListener { v: View -> (this.activity as AdventureCreation).saveAdventure() }
    }
}
