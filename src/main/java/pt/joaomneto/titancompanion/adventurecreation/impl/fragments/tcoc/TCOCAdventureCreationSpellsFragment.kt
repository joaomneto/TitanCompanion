package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
import pt.joaomneto.titancompanion.adventurecreation.impl.TCOCAdventureCreation

class TCOCAdventureCreationSpellsFragment : Fragment() {

    var spellScoreValue: TextView? = null
        private set
    private var spellList: Array<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_02tcoc_adventurecreation_spells, container, false)

        spellScoreValue = rootView.findViewById(R.id.spellScoreValue)
        spellList = resources.getStringArray(R.array.tcoc_spells)

        val listview = rootView.findViewById<ListView>(R.id.spellListView)

        val adv = activity as TCOCAdventureCreation

        val adapter = ArrayAdapter(
            adv,
            android.R.layout.simple_list_item_1, android.R.id.text1, spellList!!
        )

        listview.adapter = adapter

        val selectedSpellsListView = rootView.findViewById<ListView>(R.id.selectedSpellListView)

        val selectedSpellsAdapter = SpellListAdapter(adv, adv.spellList)

        selectedSpellsListView.adapter = selectedSpellsAdapter

        listview.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            if (adv.spellValue <= adv.spellListSize)
                return@OnItemClickListener
            adv.addSpell(spellList!![position])
            selectedSpellsAdapter.notifyDataSetChanged()
            spellScoreValue!!.text = (adv.spellValue - adv.spellListSize).toString() + ""
        }

        selectedSpellsListView.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            val builder = AlertDialog.Builder(adv)
            builder.setTitle(R.string.deleteSpellQuestion)
                .setCancelable(false)
                .setNegativeButton(
                    R.string.close
                ) { dialog, _ -> dialog.cancel() }
            builder.setPositiveButton(R.string.ok) { dialog, which ->
                adv.removeSpell(arg2)
                selectedSpellsAdapter.notifyDataSetChanged()
                spellScoreValue!!.text = (adv.spellValue - adv.spellListSize).toString() + ""
            }

            val alert = builder.create()
            alert.show()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.buttonSaveAdventure).setOnClickListener { v: View -> (this.getActivity() as AdventureCreation).saveAdventure() }
    }

    companion object {

        val SKILL_POTION = 0
        val STRENGTH_POTION = 1
        val FORTUNE_POTION = 2
    }
}
