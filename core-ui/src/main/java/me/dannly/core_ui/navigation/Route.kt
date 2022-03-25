package me.dannly.core_ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Route(
    private val name: String,
    val arguments: List<RouteArgument> = emptyList()
) {

    val deepLink
        get() = appendDestinationAndArguments("tenkai://$name")

    private suspend fun insertArguments(base: String, arguments: Array<out String>): String =
        withContext(Dispatchers.Default) {
            val argumentsIterator = arguments.iterator()
            var optionalAdded = false
            return@withContext buildString {
                append(base)
                this@Route.arguments.forEach { argument ->
                    if (!argumentsIterator.hasNext())
                        return@forEach
                    val next = argumentsIterator.next()
                    if (next.isBlank())
                        return@forEach
                    val optionalArgument = "${argument.name}=$next"
                    append(if (argument.optional) (if (optionalAdded) "&$optionalArgument" else "?$optionalArgument") else "/$next")
                    if(argument.optional)
                        optionalAdded = true
                }
            }
        }

    suspend fun withArguments(vararg arguments: String) =
        insertArguments(name, arguments)

    suspend fun withDeepLinkArguments(vararg arguments: String) =
        insertArguments("tenkai://$name", arguments)

    @Composable
    fun withArgumentsState(vararg arguments: String) = produceState<String?>(initialValue = null) {
        value = withArguments(*arguments)
    }.value

    private fun appendDestinationAndArguments(destination: String) = buildString {
        val mandatoryArguments =
            arguments.filter { !it.optional }.map { it.name }.toTypedArray()
        val optionalArguments = arguments.filter { it.optional }.map { it.name }.toTypedArray()
        append(destination)
        if (mandatoryArguments.isNotEmpty())
            append("/" + mandatoryArguments.joinToString("/") {
                "{$it}"
            })
        if (optionalArguments.isNotEmpty())
            append("?" + optionalArguments.joinToString("&") {
                "$it={$it}"
            })
    }

    override fun toString() = appendDestinationAndArguments(name)
}
