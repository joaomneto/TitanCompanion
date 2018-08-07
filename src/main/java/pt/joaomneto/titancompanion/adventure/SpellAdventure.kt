//package pt.joaomneto.titancompanion.adventure
//
//import pt.joaomneto.titancompanion.adventure.impl.util.Spell
//import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
//import java.util.Arrays
//
//abstract class SpellAdventure<S : Spell>(override val fragmentConfiguration: Array<AdventureFragmentRunner>) : Adventure(
//    fragmentConfiguration
//) {
//
//    var chosenSpells: List<S> = emptyList()
//    abstract val spellList: List<S>
//    abstract val isSpellSingleUse: Boolean
//
//    protected fun arrayToStringSpells(spells: List<S>): String {
//        var _string = ""
//
//        if (!spells.isEmpty()) {
//            for (spell in spells) {
//                _string += spell.toString() + "#"
//            }
//            _string = _string.substring(0, _string.length - 1)
//        }
//        return _string
//    }
//
//    protected fun stringToArraySpells(spells: String?, type: Class<S>): MutableList<S> {
//        val elements = ArrayList<S>()
//
//        if (spells != null) {
//            val list = Arrays.asList(*spells.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
//            for (string in list) {
//                if (!string.isEmpty()) {
//                    elements.add(getSpellForString(string, type))
//                }
//            }
//        }
//
//        return elements
//    }
//
//    private fun <S> getSpellForString(string: String, type: Class<S>): S {
//        try {
//            val method = type.getMethod("valueOf", String::class.java)
//            return method.invoke(null, string) as S
//        } catch (e: Exception) {
//            throw IllegalArgumentException("Unable to instantiate Spell enum for class" + this.javaClass, e)
//        }
//    }
//}
