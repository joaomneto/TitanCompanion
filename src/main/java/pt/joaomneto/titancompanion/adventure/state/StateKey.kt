package pt.joaomneto.titancompanion.adventure.state

import kotlin.reflect.KClass

/**
 * Created by joao.neto on 4/3/18.
 */
interface StateKey {
    val saveFileKey:String?
    val serializer:((Any) -> String)?
}
