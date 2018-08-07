//package pt.joaomneto.titancompanion.adventurecreation.impl
//
//import pt.joaomneto.titancompanion.R
//import pt.joaomneto.titancompanion.adventure.impl.fragments.mr.MRSkill
//import pt.joaomneto.titancompanion.adventure.impl.util.Spell
//import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.PotionsFragment
//import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment
//import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.mr.MRAdventureCreationSkillsFragment
//import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
//import java.io.BufferedWriter
//import java.io.IOException
//import java.util.ArrayList
//
//class MRAdventureCreation : TWOFMAdventureCreation(
//    arrayOf(
//        AdventureFragmentRunner(
//            R.string.title_adventure_creation_vitalstats,
//            VitalStatisticsFragment::class
//        ),
//        AdventureFragmentRunner(
//            R.string.title_adventure_creation_potions,
//            PotionsFragment::class
//        ),
//        AdventureFragmentRunner(
//            R.string.specialSkills,
//            MRAdventureCreationSkillsFragment::class
//        )
//    )
//) {
//
//    val skills: MutableList<Spell> = ArrayList()
//
//    val spellValue: Int = 3
//
//    @Throws(IOException::class)
//    override fun storeAdventureSpecificValuesInFile(bw: BufferedWriter) {
//
//        var chosenSkillsS = ""
//
//        if (!skills.isEmpty()) {
//            for (spell in skills) {
//                chosenSkillsS += spell.toString() + "#"
//            }
//            chosenSkillsS = chosenSkillsS.substring(0, chosenSkillsS.length - 1)
//        }
//
//        bw.write("skills=" + chosenSkillsS + "\n")
//        bw.write("provisionsValue=4\n")
//        bw.write("provisions=10\n")
//        bw.write("gold=0\n")
//    }
//
//    override fun validateCreationSpecificParameters(): String? {
//        val sb = StringBuilder()
//        if (this.skills.isEmpty()) {
//            sb.append(getString(R.string.chosenSkills))
//        }
//
//        return sb.toString()
//    }
//
//    fun addSkill(skill: MRSkill) {
//        if (!skills.contains(skill)) {
//            skills.add(skill)
//        }
//    }
//
//    fun removeSkill(skill: Int) {
//        skills.removeAt(skill)
//    }
//}
