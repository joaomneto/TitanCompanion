package pt.joaomneto.titancompanion.adventure.state.bean

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import java.util.Properties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

data class MainState(
    val gamebook: FightingFantasyGamebook,
    val name: String,
    val initialSkill: Int,
    val initialLuck: Int,
    val initialStamina: Int,
    val currentSkill: Int,
    val currentLuck: Int,
    val currentStamina: Int,
    val equipment: List<String>,
    val notes: List<String>,
    val gold: Int,
    val provisions: Int,
    val provisionsValue: Int,
    val currentReference: Int,
    val standardPotion: Int,
    val standardPotionValue: Int
) : State {
    fun checkSkill(): Boolean = pt.joaomneto.titancompanion.util.DiceRoller.roll2D6().sum <= currentSkill

    fun checkLuck(): Boolean = pt.joaomneto.titancompanion.util.DiceRoller.roll2D6().sum <= currentLuck

    fun isMaxStamina(): Boolean = currentStamina == initialStamina

    fun notesAsString() = stringListToText(notes)

    fun equipmentAsString() = stringListToText(
        equipment
    )

    override fun toSavegameProperties(): Properties {
        val properties = Properties()
        val map = MainState::class.memberProperties
            .map {
                "${it.name}" to
                    when (it.name) {
                        "gamebook" -> this.gamebook.name
                        "notes" -> notesAsString()
                        "equipment" -> equipmentAsString()
                        else -> "${it.get(this)}"
                    }
            }
        properties.putAll(map)
        return properties
    }

    open fun storeAdventureSpecificValuesInFile(): String = ""

    companion object {
        fun arrayToString(elements: Collection<Any>): String {
            var _string = ""

            if (!elements.isEmpty()) {
                for (value in elements) {
                    _string += value.toString() + "#"
                }
                _string = _string.substring(0, _string.length - 1)
            }
            return _string
        }

        fun <Y : Enum<Y>> enumListToText(list: List<Y>): String {
            var text = ""

            if (!list.isEmpty()) {
                for (note in list) {
                    text += note.name + "#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun <Y : Enum<Y>> enumMapToText(map: Map<Y, Int>): String {
            var text = ""

            if (!map.isEmpty()) {
                for (enum in map.keys) {
                    text += enum.name + "$" + map[enum] + "#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun stringListToText(list: List<String>): String {
            var text = ""

            if (!list.isEmpty()) {
                for (note in list) {
                    text += note + "#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun stringToStringList(text: String) = text.split("#".toRegex()).filter { it.isNotEmpty() }

        inline fun <reified Y : kotlin.Enum<Y>> stringToEnumList(equipmentS: String): List<Y> {
            return stringToStringList(equipmentS).map {
                safeValueOf<Y>(
                    it
                )!!
            }
        }

        inline fun <reified Y : kotlin.Enum<Y>> stringToEnumMap(equipmentS: String): Map<Y, Int> {
            return stringToStringList(equipmentS).map {
                val tokens = it.split("§")
                safeValueOf<Y>(tokens[0])!! to tokens[1].toInt()
            }.toMap()
        }

        inline fun <reified T : kotlin.Enum<T>> safeValueOf(type: String?): T? {
            return java.lang.Enum.valueOf(T::class.java, type)
        }

        fun fromProperties(properties: Properties): MainState {
            val primaryConstructor = MainState::class.primaryConstructor!!
            val values = primaryConstructor.parameters
                .map {
                    val value = properties[it.name] as String?
                    when (it.name) {
                        "name" -> it to value
                        "equipment", "notes" -> it to stringToStringList(
                            value!!
                        )
                        "gamebook" -> it to FightingFantasyGamebook.valueOf(value as String)
                        else -> it to (value?.toInt() ?: -1)
                    }

                }.toMap()
            return primaryConstructor.callBy(
                values
            )
        }
    }
}



