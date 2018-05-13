package pt.joaomneto.titancompanion.adventure.impl

import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.RCAdventureRobotCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.RCAdventureRobotFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.RCAdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.Robot
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.RobotSpecialAbility
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.RobotSpeed
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import java.io.BufferedWriter
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap

class RCAdventure : TFODAdventure(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            RCAdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.robots,
            RCAdventureRobotFragment::class
        ),
        AdventureFragmentRunner(
            R.string.robotFights,
            RCAdventureRobotCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.fights,
            AdventureCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.goldEquipment,
            AdventureEquipmentFragment::class
        ),
        AdventureFragmentRunner(
            R.string.notes,
            AdventureNotesFragment::class
        )
    )
) {

    var currentRobot: Robot? = null
    var robots: List<Robot> = mutableListOf()

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
        super.storeAdventureSpecificValuesInFile(bw)

        var robotsS = ""

        for (r in robots) {
            val name = r.name
            val location = if (r.location != null) r.location else ""
            val speed = r.speed
            val armor = r.armor
            val ability = if (r.robotSpecialAbility != null) r.robotSpecialAbility.reference!!.toString() + "" else ""
            val alternateName = if (r.alternateForm != null) r.alternateForm.name else ""
            robotsS += (name + "§" + location + "§" + speed.name + "§" + armor + "§" + ability + "§" + r.bonus + "§" + alternateName + "§" + r.isActive
                + "#")
        }
        if (robots.isNotEmpty()) {
            robotsS = robotsS.substring(0, robotsS.length - 1)
        }

        bw.write("robots=" + robotsS + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        val robotsS = String(savedGame.getProperty("robots").toByteArray(java.nio.charset.Charset.forName("UTF-8")))

        val robotCache = HashMap<String, Robot>()
        val robotAltCache = HashMap<Robot, String>()

        if (!robotsS.isEmpty()) {
            robots = ArrayList()
            val list = Arrays.asList(*robotsS.split("#".toRegex()).toTypedArray())
            for (string in list) {
                if (!string.isEmpty()) {
                    val split = string.split("§".toRegex()).toTypedArray()
                    val specialAbility = split[4]
                    val alternateFormName = split[6]
                    val active = java.lang.Boolean.parseBoolean(split[7])
                    val robot = Robot(
                        split[0], Integer.parseInt(split[3]), RobotSpeed.valueOf(split[2]), Integer.parseInt(split[5]),
                        RobotSpecialAbility.getAbiliyByReference(
                            if (specialAbility.isEmpty()) null else Integer.parseInt(
                                specialAbility
                            )
                        )
                    )
                    robot.location = split[1]
                    robot.isActive = active
                    currentRobot = robot
                    robotCache.put(robot.name, robot)
                    if (!alternateFormName.isEmpty())
                        robotAltCache.put(robot, alternateFormName)
                    robots = robots.plus(robot)
                }
            }
        }

        robots
            .filter { robotAltCache.containsKey(it) }
            .forEach { it.alternateForm = robotCache[robotAltCache[it]] }
    }

    fun destroyCurrentRobot() {
        robots = robots.filterNot { it == currentRobot || it == currentRobot?.alternateForm }
        currentRobot = null
        fullRefresh()
    }
}
