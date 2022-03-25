package me.dannly.core_ui.navigation

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class RouteTest {

    private lateinit var route: Route

    @Before
    fun setUp() {
        route = Route(
            "test",
            listOf(
                RouteArgument(name = "name", false),
                RouteArgument(name = "age", false),
                RouteArgument(name = "address", true),
                RouteArgument(name = "cpf", true)
            )
        )
    }

    @Test
    fun withArguments() = runBlocking {
        assertThat(route.withArguments(
            "Dan",
            "19",
            "Wall_Street",
            "12345678"
        )).isEqualTo("test/Dan/19?address=Wall_Street&cpf=12345678")

        assertThat(route.withArguments(
            "Dan",
            "19",
            "",
            "12345678"
        )).isEqualTo("test/Dan/19?cpf=12345678")
    }

    @Test
    fun withDeepLinkArguments() = runBlocking {
        assertThat(route.withDeepLinkArguments(
            "Dan",
            "19",
            "Wall_Street",
            "12345678"
        )).isEqualTo("tenkai://test/Dan/19?address=Wall_Street&cpf=12345678")
    }
}