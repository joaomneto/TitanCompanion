package pt.joaomneto.titancompanion.adventure.impl.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.SpellAdventure
import pt.joaomneto.titancompanion.adventure.impl.util.Spell
import pt.joaomneto.titancompanion.adventurecreation.impl.TranslatableEnumAdapter

open class AdventureSpellsFragment : AdventureFragment() {

    internal var spellList: ListView? = null
    internal var chooseSpellSpinner: Spinner? = null
    internal var addSpellButton: Button? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val adv = activity as SpellAdventure<Spell>
        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(
                R.layout.fragment_adventure_spells_chooser, container, false)

        spellList = rootView.findViewById<ListView>(R.id.spellList)
        chooseSpellSpinner = rootView
                .findViewById<Spinner>(R.id.chooseSpellSpinner)
        addSpellButton = rootView.findViewById<Button>(R.id.addSpellButton)


        val adapter = TranslatableEnumAdapter(adv,
                adv.chosenSpells)
        spellList!!.adapter = adapter

        spellList!!.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            val position = arg2
            val builder = AlertDialog.Builder(adv)
            builder.setTitle(R.string.useSpellQuestion)
                    .setCancelable(false)
                    .setNegativeButton(R.string.close
                    ) { dialog, id -> dialog.cancel() }
            builder.setPositiveButton(R.string.ok
            ) { dialog, which ->
                val spell = adv.chosenSpells[position]
                specificSpellActivation(adv, spell)
                if (adv.isSpellSingleUse) {
                    adv.chosenSpells.removeAt(position)
                    (spellList!!
                            .adapter as ArrayAdapter<String>)
                            .notifyDataSetChanged()
                }
            }

            val alert = builder.create()
            alert.show()
        }

        addSpellButton!!.setOnClickListener {
            val spell = chooseSpellSpinner!!.selectedItem as Spell
            if (adv.isSpellSingleUse || !adv.chosenSpells.contains(spell)) {
                adv.chosenSpells.add(
                        spell)
            } else {
                Adventure.showAlert(R.string.spellAlreadyChosen, adv)
            }
            refreshScreensFromResume()
        }

        chooseSpellSpinner!!.adapter = spellAdapter

        refreshScreensFromResume()

        return rootView
    }


    protected fun specificSpellActivation(adv: Adventure, spell: Spell) {
        spell.getAction().invoke(adv)
    }


    override fun refreshScreensFromResume() {
        (spellList!!.adapter as ArrayAdapter<String>).notifyDataSetChanged()

    }

    private val spellAdapter: TranslatableEnumAdapter
        get() {

            val dataAdapter = TranslatableEnumAdapter(activity, (activity as SpellAdventure<*>).spellList)

            return dataAdapter
        }


}
