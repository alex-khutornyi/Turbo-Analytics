package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.service.event.AnalyticsReport
import org.berendeev.turboanalytics.service.event.AnalyticsEventConverter
import org.berendeev.turboanalytics.service.event.EventProperty
import org.junit.Assert.*
import org.junit.Test

class AnalyticsReportConverterTest {
    @Test
    fun map_object() {
        class User(
            @EventProperty("name")
            val name: String,

            @EventProperty("soname")
            val soname: String,
        )

        class TestReport(
            @EventProperty(key = KEY)
            var user: User
        ) : AnalyticsReport("test_event_name")

        val user = User("111", "222")
        val event = TestReport(user)
        val map = AnalyticsEventConverter()
            .convertToMap(event)

        assertTrue(KEY in map.keys)
        val userMap = map[KEY] as Map<String, String>
        assertNotNull(userMap)
        assertEquals(user.name, userMap["name"])
        assertEquals(user.soname, userMap["soname"])
    }

    @Test
    fun when_several_properties() {
        class TestReport(
            @EventProperty(key = "key1")
            val valInConstructor: String,

            @EventProperty(key = "key2")
            var varInConstructor: String
        ) : AnalyticsReport("test_event_name") {
            @EventProperty(key = "key3")
            var valInBody: String = "val3"

            @EventProperty(key = "key4")
            var varInBody: String = "val4"
        }

        val event = TestReport("val1", "val2")
        val map = AnalyticsEventConverter()
            .convertToMap(event)
        assertEquals(4, map.keys.size)
    }

    @Test
    fun map_string() {
        class TestReport(
            @EventProperty(key = KEY)
            val userName: String,
        ) : AnalyticsReport("test_event_name")

        val event = TestReport("value")
        val map = AnalyticsEventConverter()
            .convertToMap(event)

        assertEquals(event.userName, map[KEY])
    }

    @Test
    fun map_int() {
        class TestReport(
            @EventProperty(key = KEY)
            val userName: String,
        ) : AnalyticsReport("test_event_name")

        val event = TestReport("value")
        val map = AnalyticsEventConverter()
            .convertToMap(event)

        assertEquals(event.userName, map[KEY])
    }

    companion object {
        private const val KEY = "KEY"
    }
}
