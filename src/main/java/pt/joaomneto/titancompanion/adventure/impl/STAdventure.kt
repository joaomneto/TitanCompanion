package pt.joaomneto.titancompanion.adventure.impl

import android.os.Bundle
import android.view.Menu
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.st.STCombatFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.st.STCrewStatsFragment
import pt.joaomneto.titancompanion.adventure.impl.fragments.st.STStarshipCombatFragment
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedWriter
import java.io.IOException
import java.util.HashMap
import java.util.HashSet

class STAdventure : Adventure(
    arrayOf(
        AdventureFragmentRunner(
            R.string.vitalStats,
            AdventureVitalStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.shipCrewStats,
            STCrewStatsFragment::class
        ),
        AdventureFragmentRunner(
            R.string.combat,
            STCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.starshipCombat,
            STStarshipCombatFragment::class
        ),
        AdventureFragmentRunner(
            R.string.notes,
            AdventureNotesFragment::class
        )
    )
) {

    var initialScienceOfficerSkill = -1
    var initialMedicalOfficerSkill = -1
    var initialEngineeringOfficerSkill = -1
    var initialSecurityOfficerSkill = -1
    var initialSecurityGuard1Skill = -1
    var initialSecurityGuard2Skill = -1
    var initialShipWeapons = -1
    var initialScienceOfficerStamina = -1
    var initialMedicalOfficerStamina = -1
    var initialEngineeringOfficerStamina = -1
    var initialSecurityOfficerStamina = -1
    var initialSecurityGuard1Stamina = -1
    var initialSecurityGuard2Stamina = -1
    var initialShipShields = -1
    var currentScienceOfficerSkill = -1
    var currentMedicalOfficerSkill = -1
    var currentEngineeringOfficerSkill = -1
    var currentSecurityOfficerSkill = -1
    var currentSecurityGuard1Skill = -1
    var currentSecurityGuard2Skill = -1
    var currentShipWeapons = -1
    var currentScienceOfficerStamina = -1
    var currentMedicalOfficerStamina = -1
    var currentEngineeringOfficerStamina = -1
    var currentSecurityOfficerStamina = -1
    var currentSecurityGuard1Stamina = -1
    var currentSecurityGuard2Stamina = -1
    var currentShipShields = -1
    var isLandingPartyScienceOfficer = false
    var isLandingPartyMedicalOfficer = false
    var isLandingPartyEngineeringOfficer = false
    var isLandingPartySecurityOfficer = false
    var isLandingPartySecurityGuard1 = false
    var isLandingPartySecurityGuard2 = false
    var isDeadScienceOfficer = false
    var isDeadMedicalOfficer = false
    var isDeadEngineeringOfficer = false
    var isDeadSecurityOfficer = false
    var isDeadSecurityGuard1 = false
    var isDeadSecurityGuard2 = false

    private var stringToCrewmanMap: MutableMap<String, STCrewman> = mutableMapOf()
    private var crewmanToStringMap: MutableMap<STCrewman, String> = mutableMapOf()

    val stCrewStatsFragment: STCrewStatsFragment?
        get() = getFragment(STCrewStatsFragment::class)

    val landingParty: Set<STCrewman>
        get() {
            val landingParty = HashSet<STAdventure.STCrewman>()
            if (isLandingPartyEngineeringOfficer)
                landingParty.add(STCrewman.ENGINEERING_OFFICER)
            if (isLandingPartyScienceOfficer)
                landingParty.add(STCrewman.SCIENCE_OFFICER)
            if (isLandingPartyMedicalOfficer)
                landingParty.add(STCrewman.MEDICAL_OFFICER)
            if (isLandingPartySecurityGuard1)
                landingParty.add(STCrewman.SECURITY_GUARD1)
            if (isLandingPartySecurityGuard2)
                landingParty.add(STCrewman.SECURITY_GUARD2)
            if (isLandingPartySecurityOfficer)
                landingParty.add(STCrewman.SECURITY_OFFICER)
            landingParty.add(STCrewman.CAPTAIN)
            return landingParty
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)

            stringToCrewmanMap = HashMap()
            stringToCrewmanMap.put(resources.getString(R.string.captain), STCrewman.CAPTAIN)
            stringToCrewmanMap.put(resources.getString(R.string.medicalOfficer), STCrewman.MEDICAL_OFFICER)
            stringToCrewmanMap.put(resources.getString(R.string.scienceOfficer), STCrewman.SCIENCE_OFFICER)
            stringToCrewmanMap.put(resources.getString(R.string.engineeringOfficer), STCrewman.ENGINEERING_OFFICER)
            stringToCrewmanMap.put(resources.getString(R.string.securityOfficer), STCrewman.SECURITY_OFFICER)
            stringToCrewmanMap.put(resources.getString(R.string.securityGuard1), STCrewman.SECURITY_GUARD1)
            stringToCrewmanMap.put(resources.getString(R.string.securityGuard2), STCrewman.SECURITY_GUARD2)

            crewmanToStringMap = HashMap()
            crewmanToStringMap.put(STCrewman.CAPTAIN, resources.getString(R.string.captain))
            crewmanToStringMap.put(STCrewman.MEDICAL_OFFICER, resources.getString(R.string.medicalOfficer))
            crewmanToStringMap.put(STCrewman.SCIENCE_OFFICER, resources.getString(R.string.scienceOfficer))
            crewmanToStringMap.put(STCrewman.ENGINEERING_OFFICER, resources.getString(R.string.engineeringOfficer))
            crewmanToStringMap.put(STCrewman.SECURITY_OFFICER, resources.getString(R.string.securityOfficer))
            crewmanToStringMap.put(STCrewman.SECURITY_GUARD1, resources.getString(R.string.securityGuard1))
            crewmanToStringMap.put(STCrewman.SECURITY_GUARD2, resources.getString(R.string.securityGuard2))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    @Throws(IOException::class)
    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {

        bw.write("scienceOfficerSkill=" + currentScienceOfficerSkill + "\n")
        bw.write("scienceOfficerStamina=" + currentScienceOfficerStamina + "\n")
        bw.write("medicalOfficerSkill=" + currentMedicalOfficerSkill + "\n")
        bw.write("medicalOfficerStamina=" + currentMedicalOfficerStamina + "\n")
        bw.write("engineeringOfficerSkill=" + currentEngineeringOfficerSkill + "\n")
        bw.write("engineeringOfficerStamina=" + currentEngineeringOfficerStamina + "\n")
        bw.write("securityOfficerSkill=" + currentSecurityOfficerSkill + "\n")
        bw.write("securityOfficerStamina=" + currentSecurityOfficerStamina + "\n")
        bw.write("securityGuard1Skill=" + currentSecurityGuard1Skill + "\n")
        bw.write("securityGuard1Stamina=" + currentSecurityGuard1Stamina + "\n")
        bw.write("securityGuard2Skill=" + currentSecurityGuard2Skill + "\n")
        bw.write("securityGuard2Stamina=" + currentSecurityGuard2Stamina + "\n")
        bw.write("shipWeapons=" + currentShipWeapons + "\n")
        bw.write("shipShields=" + currentShipShields + "\n")

        bw.write("scienceOfficerInitialSkill=" + initialScienceOfficerSkill + "\n")
        bw.write("scienceOfficerInitialStamina=" + initialScienceOfficerStamina + "\n")
        bw.write("medicalOfficerInitialSkill=" + initialMedicalOfficerSkill + "\n")
        bw.write("medicalOfficerInitialStamina=" + initialMedicalOfficerStamina + "\n")
        bw.write("engineeringOfficerInitialSkill=" + initialEngineeringOfficerSkill + "\n")
        bw.write("engineeringOfficerInitialStamina=" + initialEngineeringOfficerStamina + "\n")
        bw.write("securityOfficerInitialSkill=" + initialSecurityOfficerSkill + "\n")
        bw.write("securityOfficerInitialStamina=" + initialSecurityOfficerStamina + "\n")
        bw.write("securityGuard1InitialSkill=" + initialSecurityGuard1Skill + "\n")
        bw.write("securityGuard1InitialStamina=" + initialSecurityGuard1Stamina + "\n")
        bw.write("securityGuard2InitialSkill=" + initialSecurityGuard2Skill + "\n")
        bw.write("securityGuard2InitialStamina=" + initialSecurityGuard2Stamina + "\n")
        bw.write("shipInitialWeapons=" + initialShipWeapons + "\n")
        bw.write("shipInitialShields=" + initialShipShields + "\n")

        bw.write("landingPartyScienceOfficer=" + isLandingPartyScienceOfficer + "\n")
        bw.write("landingPartyMedicalOfficer=" + isLandingPartyMedicalOfficer + "\n")
        bw.write("landingPartyEngineeringOfficer=" + isLandingPartyEngineeringOfficer + "\n")
        bw.write("landingPartySecurityOfficer=" + isLandingPartySecurityOfficer + "\n")
        bw.write("landingPartySecurityGuard1=" + isLandingPartySecurityGuard1 + "\n")
        bw.write("landingPartySecurityGuard2=" + isLandingPartySecurityGuard2 + "\n")

        bw.write("deadScienceOfficer=" + isDeadScienceOfficer + "\n")
        bw.write("deadMedicalOfficer=" + isDeadMedicalOfficer + "\n")
        bw.write("deadEngineeringOfficer=" + isDeadEngineeringOfficer + "\n")
        bw.write("deadSecurityOfficer=" + isDeadSecurityOfficer + "\n")
        bw.write("deadSecurityGuard1=" + isDeadSecurityGuard1 + "\n")
        bw.write("deadSecurityGuard2=" + isDeadSecurityGuard2 + "\n")
    }

    override fun loadAdventureSpecificValuesFromFile() {
        currentScienceOfficerSkill = Integer.valueOf(savedGame.getProperty("scienceOfficerSkill"))
        currentScienceOfficerStamina = Integer.valueOf(savedGame.getProperty("scienceOfficerStamina"))
        currentMedicalOfficerSkill = Integer.valueOf(savedGame.getProperty("medicalOfficerSkill"))
        currentMedicalOfficerStamina = Integer.valueOf(savedGame.getProperty("medicalOfficerStamina"))
        currentEngineeringOfficerSkill = Integer.valueOf(savedGame.getProperty("engineeringOfficerSkill"))
        currentEngineeringOfficerStamina = Integer.valueOf(savedGame.getProperty("engineeringOfficerStamina"))
        currentSecurityOfficerSkill = Integer.valueOf(savedGame.getProperty("securityOfficerSkill"))
        currentSecurityOfficerStamina = Integer.valueOf(savedGame.getProperty("securityOfficerStamina"))
        currentSecurityGuard1Skill = Integer.valueOf(savedGame.getProperty("securityGuard1Skill"))
        currentSecurityGuard1Stamina = Integer.valueOf(savedGame.getProperty("securityGuard1Stamina"))
        currentSecurityGuard2Skill = Integer.valueOf(savedGame.getProperty("securityGuard2Skill"))
        currentSecurityGuard2Stamina = Integer.valueOf(savedGame.getProperty("securityGuard2Stamina"))
        currentShipWeapons = Integer.valueOf(savedGame.getProperty("shipWeapons"))
        currentShipShields = Integer.valueOf(savedGame.getProperty("shipShields"))

        initialScienceOfficerSkill = Integer.valueOf(savedGame.getProperty("scienceOfficerInitialSkill"))
        initialScienceOfficerStamina = Integer.valueOf(savedGame.getProperty("scienceOfficerInitialStamina"))
        initialMedicalOfficerSkill = Integer.valueOf(savedGame.getProperty("medicalOfficerInitialSkill"))
        initialMedicalOfficerStamina = Integer.valueOf(savedGame.getProperty("medicalOfficerInitialStamina"))
        initialEngineeringOfficerSkill = Integer.valueOf(savedGame.getProperty("engineeringOfficerInitialSkill"))
        initialEngineeringOfficerStamina = Integer.valueOf(
            savedGame.getProperty(
                "engineeringOfficerInitialStamina"
            )
        )
        initialSecurityOfficerSkill = Integer.valueOf(savedGame.getProperty("securityOfficerInitialSkill"))
        initialSecurityOfficerStamina = Integer.valueOf(savedGame.getProperty("securityOfficerInitialStamina"))
        initialSecurityGuard1Skill = Integer.valueOf(savedGame.getProperty("securityGuard1InitialSkill"))
        initialSecurityGuard1Stamina = Integer.valueOf(savedGame.getProperty("securityGuard1InitialStamina"))
        initialSecurityGuard2Skill = Integer.valueOf(savedGame.getProperty("securityGuard2InitialSkill"))
        initialSecurityGuard2Stamina = Integer.valueOf(savedGame.getProperty("securityGuard2InitialStamina"))
        initialShipWeapons = Integer.valueOf(savedGame.getProperty("shipInitialWeapons"))
        initialShipShields = Integer.valueOf(savedGame.getProperty("shipInitialShields"))

        isLandingPartyScienceOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("landingPartyScienceOfficer"))
        isLandingPartyMedicalOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("landingPartyMedicalOfficer"))
        isLandingPartyEngineeringOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("landingPartyEngineeringOfficer"))
        isLandingPartySecurityOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("landingPartySecurityOfficer"))
        isLandingPartySecurityGuard1 = java.lang.Boolean.valueOf(savedGame.getProperty("landingPartySecurityGuard1"))
        isLandingPartySecurityGuard2 = java.lang.Boolean.valueOf(savedGame.getProperty("landingPartySecurityGuard2"))

        isDeadScienceOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("deadScienceOfficer"))
        isDeadMedicalOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("deadMedicalOfficer"))
        isDeadEngineeringOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("deadEngineeringOfficer"))
        isDeadSecurityOfficer = java.lang.Boolean.valueOf(savedGame.getProperty("deadSecurityOfficer"))
        isDeadSecurityGuard1 = java.lang.Boolean.valueOf(savedGame.getProperty("deadSecurityGuard1"))
        isDeadSecurityGuard2 = java.lang.Boolean.valueOf(savedGame.getProperty("deadSecurityGuard2"))
    }

    fun getCrewmanStamina(crewman: STCrewman): Int {

        when (crewman) {
            STAdventure.STCrewman.CAPTAIN -> return getCurrentStamina()

            STAdventure.STCrewman.SCIENCE_OFFICER -> return currentScienceOfficerStamina

            STAdventure.STCrewman.MEDICAL_OFFICER -> return currentMedicalOfficerStamina

            STAdventure.STCrewman.ENGINEERING_OFFICER -> return currentEngineeringOfficerStamina

            STAdventure.STCrewman.SECURITY_OFFICER -> return currentSecurityOfficerStamina

            STAdventure.STCrewman.SECURITY_GUARD1 -> return currentSecurityGuard1Stamina

            STAdventure.STCrewman.SECURITY_GUARD2 -> return currentSecurityGuard2Stamina

            else -> return 0
        }
    }

    fun getCrewmanSkill(crewman: STCrewman): Int {

        when (crewman) {
            STAdventure.STCrewman.CAPTAIN -> return getCurrentSkill()

            STAdventure.STCrewman.SCIENCE_OFFICER -> return currentScienceOfficerSkill

            STAdventure.STCrewman.MEDICAL_OFFICER -> return currentMedicalOfficerSkill

            STAdventure.STCrewman.ENGINEERING_OFFICER -> return currentEngineeringOfficerSkill

            STAdventure.STCrewman.SECURITY_OFFICER -> return currentSecurityOfficerSkill

            STAdventure.STCrewman.SECURITY_GUARD1 -> return currentSecurityGuard1Skill

            STAdventure.STCrewman.SECURITY_GUARD2 -> return currentSecurityGuard2Skill

            else -> return 0
        }
    }

    fun setCrewmanStamina(crewman: STCrewman, value: Int) {

        when (crewman) {
            STAdventure.STCrewman.CAPTAIN -> setCurrentStamina(value)
            STAdventure.STCrewman.SCIENCE_OFFICER -> currentScienceOfficerStamina = value
            STAdventure.STCrewman.MEDICAL_OFFICER -> currentMedicalOfficerStamina = value
            STAdventure.STCrewman.ENGINEERING_OFFICER -> currentEngineeringOfficerStamina = value
            STAdventure.STCrewman.SECURITY_OFFICER -> currentSecurityOfficerStamina = value
            STAdventure.STCrewman.SECURITY_GUARD1 -> currentSecurityGuard1Stamina = value
            STAdventure.STCrewman.SECURITY_GUARD2 -> currentSecurityGuard2Stamina = value
        }

        stCrewStatsFragment?.refreshScreensFromResume()
    }

    fun setCrewmanDead(crewman: STCrewman) {

        when (crewman) {
            STAdventure.STCrewman.SCIENCE_OFFICER -> {
                if(isDeadScienceOfficer) return
                isDeadScienceOfficer = true
                currentScienceOfficerSkill = initialScienceOfficerSkill - 2
                currentScienceOfficerStamina = DiceRoller.roll2D6().sum!! + 12
                isLandingPartyScienceOfficer = false
                stCrewStatsFragment?.disableCrewmanLandingPartyOption(STCrewman.SCIENCE_OFFICER)
            }
            STAdventure.STCrewman.MEDICAL_OFFICER -> {
                if(isDeadMedicalOfficer) return
                isDeadMedicalOfficer = true
                currentMedicalOfficerSkill = initialMedicalOfficerSkill - 2
                currentMedicalOfficerStamina = DiceRoller.roll2D6().sum!! + 12
                isLandingPartyMedicalOfficer = false
                stCrewStatsFragment?.disableCrewmanLandingPartyOption(STCrewman.MEDICAL_OFFICER)
            }
            STAdventure.STCrewman.ENGINEERING_OFFICER -> {
                if(isDeadEngineeringOfficer) return
                isDeadEngineeringOfficer = true
                currentEngineeringOfficerSkill = initialEngineeringOfficerSkill - 2
                currentEngineeringOfficerStamina = DiceRoller.roll2D6().sum!! + 12
                isLandingPartyEngineeringOfficer = false
                stCrewStatsFragment?.disableCrewmanLandingPartyOption(STCrewman.ENGINEERING_OFFICER)
            }
            STAdventure.STCrewman.SECURITY_OFFICER -> {
                if(isDeadScienceOfficer) return
                isDeadSecurityOfficer = true
                currentSecurityOfficerSkill = initialSecurityOfficerSkill - 2
                currentSecurityOfficerStamina = DiceRoller.roll2D6().sum!! + 12
                isLandingPartySecurityOfficer = false
                stCrewStatsFragment?.disableCrewmanLandingPartyOption(STCrewman.SECURITY_OFFICER)
            }
            STAdventure.STCrewman.SECURITY_GUARD1 -> {
                if(isDeadSecurityGuard1) return
                isDeadSecurityGuard1 = true
                currentSecurityGuard1Skill = initialSecurityGuard1Skill - 2
                currentSecurityGuard1Stamina = DiceRoller.roll2D6().sum!! + 12
                isLandingPartySecurityGuard1 = false
                stCrewStatsFragment?.disableCrewmanLandingPartyOption(STCrewman.SECURITY_GUARD1)
            }
            STAdventure.STCrewman.SECURITY_GUARD2 -> {
                if(isDeadSecurityGuard2) return
                isDeadSecurityGuard2 = true
                currentSecurityGuard2Skill = initialSecurityGuard2Skill - 2
                currentSecurityGuard2Stamina = DiceRoller.roll2D6().sum!! + 12
                isLandingPartySecurityGuard2 = false
                stCrewStatsFragment?.disableCrewmanLandingPartyOption(STCrewman.SECURITY_GUARD2)
            }
            else -> {
            }
        }

        stCrewStatsFragment?.refreshScreensFromResume()
    }

    fun getCrewmanForString(value: String): STCrewman {
        return stringToCrewmanMap[value] ?: throw IllegalArgumentException("Invalid crewman")
    }

    fun getStringForCrewman(value: STCrewman): String {
        return crewmanToStringMap[value] ?: throw IllegalArgumentException("Invalid crewman")
    }

    enum class STCrewman {
        CAPTAIN, SCIENCE_OFFICER, MEDICAL_OFFICER, ENGINEERING_OFFICER, SECURITY_OFFICER, SECURITY_GUARD1, SECURITY_GUARD2
    }

    companion object {

        protected val FRAGMENT_CREW_STATS = 1
        protected val FRAGMENT_PHASER_COMBAT = 2
        protected val FRAGMENT_SHIP_COMBAT = 3
        protected val FRAGMENT_NOTES = 4
    }
}
