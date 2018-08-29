package pt.joaomneto.titancompanion.util

import android.support.v4.app.Fragment
import pt.joaomneto.titancompanion.adventure.Adventure
import kotlin.reflect.KClass

class AdventureFragmentRunner(
    val titleId: Int,
    val fragment: KClass<out Fragment>,
    val adventure: KClass<out Adventure<*,*,*>>? = null
)
