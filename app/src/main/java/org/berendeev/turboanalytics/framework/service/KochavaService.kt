package org.berendeev.turboanalytics.framework.service

import com.kochava.base.Tracker
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport.Kochava
import org.berendeev.turboanalytics.framework.service.report.ReportName
import org.berendeev.turboanalytics.framework.service.report.ReportProperty
import timber.log.Timber
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

class KochavaService : AnalyticsService<Kochava> {

    override fun send(event: Kochava) {
        Timber.e( "send: $event")

        when (event) {
            is Kochava.Standard -> Tracker.sendEvent(event.trackerEvent)
            is Kochava.General -> sendCustomEvent(event)
        }
    }

    private fun sendCustomEvent(event: Kochava.General) {
        val hasEventName = event::class.hasAnnotation<ReportName>()
        val eventName = event::class.findAnnotation<ReportName>()?.name
        Timber.e( "send: hasEventName=$hasEventName, eventName=$eventName")

        if (hasEventName.not()) {
            Timber.e( "send: EXCEPTION class must have `${ReportName::name}` annotation")
        }

        for (property in event::class.memberProperties) {
            val hasEventPropertyAnnotation = property.findAnnotation<ReportProperty>() != null
            val propertyName = property.name
            val propertyType = property.returnType
            val propertyValue = property.getter.call(event)
            Timber.e( "send: hasEventPropertyAnnotation=$hasEventPropertyAnnotation, type: $propertyType: $propertyName=$propertyValue")
        }
    }
}