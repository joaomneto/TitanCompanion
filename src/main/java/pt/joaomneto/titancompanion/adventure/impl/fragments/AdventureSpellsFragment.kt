//package pt.joaomneto.titancompanion.adventure.impl.fragments
//
//import android.app.AlertDialog
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView.OnItemClickListener
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.ListView
//import android.widget.Spinner
//import pt.joaomneto.titancompanion.R
//import pt.joaomneto.titancompanion.adventure.Adventure
//import pt.joaomneto.titancompanion.adventure.AdventureFragment
//import pt.joaomneto.titancompanion.adventure.SpellAdventure
//import pt.joaomneto.titancompanion.adventure.impl.util.Spell
//import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.TranslatableEnumAdapter
//
//open class AdventureSpellsFragment : AdventureFragment() {
//
//    internal var spellList: ListView? = null
//    private var chooseSpellSpinner: Spinner? = null
//    private var addSpellButton: Button? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        val adv = activity as SpellAdventure<Spell>
//        super.onCreate(savedInstanceState)
//        val rootView = inflater.inflate(
//            R.layout.fragment_adventure_spells_chooser, container, false
//        )
//
//        spellList = rootView.findViewById<ListView>(R.id.spellList)
//        chooseSpellSpinner = rootView
//            .findViewById<Spinner>(R.id.chooseSpellSpinner)
//        addSpellButton = rootView.findViewById<Button>(R.id.addSpellButton)
//
//        val adapter = TranslatableEnumAdapter(
//            adv, android.R.layout.simple_list_item_1,
//            adv.chosenSpells
//        )
//        spellList!!.adapter = adapter
//
//        spellList!!.onItemClickListener = OnItemClickListener { _, _, arg2, _ ->
//            val position = arg2
//            val builder = AlertDialog.Builder(adv)
//            builder.setTitle(R.string.useSpellQuestion)
//                .setCancelable(false)
//                .setNegativeButton(
//                    R.string.close
//                ) { dialog, _ -> dialog.cancel() }
//            builder.setPositiveButton(
//                R.string.ok
//            ) { _, _ ->
//                val spell = adv.chosenSpells[position]
//                specificSpellActivation(adv, spell)
//                if (adv.isSpellSingleUse) {
//                    adv.chosenSpells = adv.chosenSpells.minus(adv.chosenSpells[position])
//                    (spellList!!
//                        .adapter as ArrayAdapter<*>)
//                        .notifyDataSetChanged()
//                }
//            }
//
//            val alert = builder.create()
//            alert.show()
//        }
//
//        addSpellButton!!.setOnClickListener {
//            val spell = chooseSpellSpinner!!.selectedItem as Spell
//            if (adv.isSpellSingleUse || !adv.chosenSpells.contains(spell)) {
//                adv.chosenSpells = adv.chosenSpells.plus(spell)
//            } else {
//                Adventure.showAlert(R.string.spellAlreadyChosen, adv)
//            }
//            refreshScreen()
//        }
//
//        chooseSpellSpinner!!.adapter = spellAdapter
//
//        refreshScreen()
//
//        return rootView
//    }
//
//    protected fun specificSpellActivation(adv: Adventure, spell: Spell) {
//        spell.getAction().invoke(adv)
//    }
//
//    override fun refreshScreen() {
//        (spellList!!.adapter as ArrayAdapter<*>).notifyDataSetChanged()
//    }
//
//    private val spellAdapter: TranslatableEnumAdapter
//        get() {
//
//            val dataAdapter = TranslatableEnumAdapter(
//                activity,
//                android.R.layout.simple_list_item_1,
//                (activity as SpellAdventure<*>).spellList
//            )
//
//            return dataAdapter
//        }
//}
