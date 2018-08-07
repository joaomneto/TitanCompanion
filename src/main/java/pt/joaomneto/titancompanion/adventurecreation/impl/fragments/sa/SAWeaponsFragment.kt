//package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa
//
//import android.app.AlertDialog
//import android.content.Context
//import android.content.DialogInterface
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.View.OnClickListener
//import android.view.ViewGroup
//import android.view.inputmethod.InputMethodManager
//import android.widget.AdapterView.OnItemLongClickListener
//import android.widget.Button
//import android.widget.ListView
//import android.widget.Spinner
//import android.widget.TextView
//import pt.joaomneto.titancompanion.R
//import pt.joaomneto.titancompanion.adventure.Adventure
//import pt.joaomneto.titancompanion.adventurecreation.impl.SAAdventureCreation
//import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.TranslatableEnumAdapter
//
//class SAWeaponsFragment : Fragment() {
//
//    lateinit var weaponsValue: TextView
//    lateinit var weaponList: ListView
//    lateinit var buttonAddWeapon: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        super.onCreate(savedInstanceState)
//        val adv = activity as SAAdventureCreation
//
//        var rootView = inflater.inflate(
//            R.layout.fragment_12sa_adventurecreation_weapons, container,
//            false
//        )
//        weaponsValue = rootView.findViewById(R.id.weaponsValue)
//        weaponList = rootView.findViewById(R.id.weaponList)
//        buttonAddWeapon = rootView.findViewById(R.id.buttonAddweapon)
//
//        weaponList.onItemLongClickListener = OnItemLongClickListener { _, _, position, _ ->
//            val builder = AlertDialog.Builder(adv)
//            builder.setTitle(R.string.saDeleteWeapon)
//                .setCancelable(false)
//                .setNegativeButton(
//                    R.string.close
//                ) { dialog, id -> dialog.cancel() }
//            builder.setPositiveButton(
//                R.string.ok
//            ) { _, _ ->
//                adv.weapons.removeAt(position)
//                (weaponList.adapter as TranslatableEnumAdapter)
//                    .notifyDataSetChanged()
//            }
//
//            val alert = builder.create()
//            alert.show()
//            true
//        }
//
//        buttonAddWeapon!!.setOnClickListener(OnClickListener {
//            val alert = AlertDialog.Builder(adv)
//
//            alert.setTitle(R.string.saWeapon)
//
//            // Set an EditText view to get user input
//            val input = Spinner(adv)
//            val adapter = TranslatableEnumAdapter(
//                adv, android.R.layout.simple_list_item_1,
//                if (adv!!.weapons.isEmpty()) SAWeapon.INITIALWEAPONS else SAWeapon.values()
//            )
//            input.adapter = adapter
//            val imm = adv
//                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
//            input.requestFocus()
//            alert.setView(input)
//
//            alert.setPositiveButton(R.string.ok,
//                DialogInterface.OnClickListener { dialog, whichButton ->
//                    val selectedWeapon = SAWeapon.values()[input.selectedItemPosition]
//                    if (getCurrentWeaponsCount(adv) + selectedWeapon.weaponPoints > adv.currentWeapons) {
//                        Adventure.showAlert(
//                            getString(
//                                R.string.saNoWeaponPoints,
//                                getString(selectedWeapon.getLabelId())
//                            ), adv
//                        )
//                        return@OnClickListener
//                    }
//                    adv.weapons.add(selectedWeapon)
//                    (weaponList
//                        .adapter as TranslatableEnumAdapter).notifyDataSetChanged()
//                })
//
//            alert.setNegativeButton(
//                R.string.cancel
//            ) { dialog, whichButton ->
//                // Canceled.
//            }
//
//            alert.show()
//        })
//
//        val adapter = TranslatableEnumAdapter(
//            adv, android.R.layout.simple_list_item_1,
//            adv!!.weapons
//        )
//
//        weaponList!!.adapter = adapter
//        adapter.notifyDataSetChanged()
//
//        return rootView
//    }
//
//    private fun getCurrentWeaponsCount(adv: SAAdventureCreation): Float {
//        var count = 0f
//        for (weapon in adv.weapons) {
//            count += weapon.weaponPoints.toFloat()
//        }
//        return count
//    }
//}
