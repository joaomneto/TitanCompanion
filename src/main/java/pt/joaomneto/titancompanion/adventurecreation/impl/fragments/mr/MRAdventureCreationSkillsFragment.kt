//package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.mr
//
//import android.app.AlertDialog
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.View.GONE
//import android.view.ViewGroup
//import android.widget.AdapterView.OnItemClickListener
//import android.widget.ListView
//import android.widget.TextView
//import pt.joaomneto.titancompanion.R
//import pt.joaomneto.titancompanion.adventure.AdventureFragment
//import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRSkill
//import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation
//import pt.joaomneto.titancompanion.adventurecreation.impl.MRAdventureCreation
//import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.TranslatableEnumAdapter
//import java.util.Arrays
//
//class MRAdventureCreationSkillsFragment : AdventureFragment() {
//
//    private var spellScoreValue: TextView? = null
//    private var spellPointsText: TextView? = null
//    private var skillList: Array<String>? = null
//    private var selectedSkillsAdapter: TranslatableEnumAdapter? = null
//    private var activity: MRAdventureCreation? = null
//
//    val skills: Array<MRSkill>
//        get() = MRSkill.values()
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        super.onCreate(savedInstanceState)
//        val rootView = inflater.inflate(R.layout.fragment_29mr_adventurecreation_skills, container, false)
//
//        spellScoreValue = rootView.findViewById(R.id.spellScoreValue)
//        spellPointsText = rootView.findViewById(R.id.spellPointsText)
//
//        spellScoreValue!!.visibility = GONE
//        spellPointsText!!.visibility = GONE
//
//        skillList = resources.getStringArray(R.array.tcoc_spells)
//
//        val listview = rootView.findViewById<ListView>(R.id.spellListView)
//
//        activity = getActivity() as MRAdventureCreation?
//
//        val adapter = TranslatableEnumAdapter(activity, android.R.layout.simple_list_item_1, Arrays.asList(*skills))
//
//        listview.adapter = adapter
//
//        val selectedSpellsListView = rootView.findViewById<ListView>(R.id.selectedSpellListView)
//
//
//        selectedSkillsAdapter = TranslatableEnumAdapter(
//            activity,
//            android.R.layout.simple_list_item_1,
//            activity!!.skills
//        )
//
//        selectedSpellsListView.adapter = selectedSkillsAdapter
//
//        listview.onItemClickListener = OnItemClickListener { parent, view, position, id ->
//            if (activity!!.spellValue <= activity!!.skills.size)
//                return@OnItemClickListener
//            activity!!.addSkill(skills[position])
//            selectedSkillsAdapter!!.notifyDataSetChanged()
//        }
//
//        selectedSpellsListView.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
//            val builder = AlertDialog.Builder(activity)
//            builder.setTitle(R.string.deleteSkillQuestion)
//                .setCancelable(false)
//                .setNegativeButton(
//                    R.string.close
//                ) { dialog, id -> dialog.cancel() }
//            builder.setPositiveButton(R.string.ok) { dialog, which ->
//                activity!!.removeSkill(arg2)
//                selectedSkillsAdapter!!.notifyDataSetChanged()
//            }
//
//            val alert = builder.create()
//            alert.show()
//        }
//
//        return rootView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view.findViewById<View>(R.id.buttonSaveAdventure).setOnClickListener { v: View -> (this.getActivity() as AdventureCreation).saveAdventure() }
//    }
//
//    override fun refreshScreen() {
//
//        selectedSkillsAdapter!!.notifyDataSetChanged()
//    }
//}
