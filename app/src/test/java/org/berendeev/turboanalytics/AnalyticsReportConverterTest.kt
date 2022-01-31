package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import org.berendeev.turboanalytics.framework.service.report.AnalyticsEventConverter
import org.berendeev.turboanalytics.framework.service.report.ReportProperty
import org.junit.Assert.*
import org.junit.Test

class AnalyticsReportConverterTest {
    @Test
    fun map_object() {
        class User(
            @ReportProperty("name")
            val name: String,

            @ReportProperty("soname")
            val soname: String,
        )

        class TestReport(
            @ReportProperty(key = KEY)
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
            @ReportProperty(key = "key1")
            val valInConstructor: String,

            @ReportProperty(key = "key2")
            var varInConstructor: String
        ) : AnalyticsReport("test_event_name") {
            @ReportProperty(key = "key3")
            var valInBody: String = "val3"

            @ReportProperty(key = "key4")
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
            @ReportProperty(key = KEY)
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
            @ReportProperty(key = KEY)
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
