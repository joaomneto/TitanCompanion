package pt.joaomneto.titancompanion.adventure.impl.fragments.rc.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.RCAdventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.Robot

class RobotListAdapter(private val adv: RCAdventure, private val values: List<Robot>) : ArrayAdapter<Robot>(
    adv,
    -1,
    values
), View.OnCreateContextMenuListener {

    override fun onCreateContextMenu(p0: ContextMenu?, p1: View?, p2: ContextMenu.ContextMenuInfo?) {
        TODO("not implemented")
    }

    var currentRobot: Robot?
        get() = adv.currentRobot
        set(currentRobot) {
            adv.currentRobot = currentRobot
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val robotView = inflater.inflate(R.layout.component_22rc_robot, parent, false)

        val robotTextName = robotView.rootView.findViewById<TextView>(R.id.robotTextNameValue)
        val robotTextArmor = robotView.rootView.findViewById<TextView>(R.id.robotTextArmorValue)
        val robotTextSpeed = robotView.rootView.findViewById<TextView>(R.id.robotTextSpeedValue)
        val robotTextBonus = robotView.rootView.findViewById<TextView>(R.id.robotTextBonusValue)
        val robotTextLocation = robotView.rootView.findViewById<TextView>(R.id.robotTextLocationValue)
        val robotTextSpecialAbility = robotView.rootView.findViewById<TextView>(R.id.robotTextSpecialAbilityValue)
        // final Button removeRobotButton = (Button)
        // robotView.getRootView().findViewById(R.id.removeRobotButton);

        val robotPosition = values[position]

        robotTextName.text = robotPosition.name
        robotTextArmor.text = "" + robotPosition.armor!!
        robotTextSpeed.text = "" + robotPosition.speed
        robotTextBonus.text = "" + robotPosition.bonus!!
        robotTextLocation.text = robotPosition.location
        if (robotPosition.robotSpecialAbility != null) {
            robotTextSpecialAbility.setText(robotPosition.robotSpecialAbility.getName()!!)
        }

        val minusCombatArmor = robotView.findViewById<Button>(R.id.minusRobotArmorButton)
        val plusCombatArmor = robotView.findViewById<Button>(R.id.plusRobotArmorButton)

        val radio = robotView.rootView.findViewById<RadioButton>(R.id.robotSelected)
        radio.isChecked = robotPosition.isActive

        if (robotPosition.isActive) {
            currentRobot = robotPosition
        }

        val adapter = this


        radio.setOnCheckedChangeListener { buttonView, isChecked ->
            for (r in values) {
                r.isActive = false
            }
            robotPosition.isActive = true
            adapter.notifyDataSetChanged()
            currentRobot = robotPosition

            adv.fullRefresh()
        }

        minusCombatArmor.setOnClickListener {
            robotPosition.armor = Math.max(0, robotPosition.armor!! - 1)
            robotTextArmor.text = "" + robotPosition.armor!!
        }

        plusCombatArmor.setOnClickListener {
            robotPosition.armor = robotPosition.armor!! + 1
            robotTextArmor.text = "" + robotPosition.armor!!
        }

        robotView.setOnLongClickListener { false }

        return robotView
    }

}
