package org.berendeev.turboanalytics.framework.service

import com.kochava.base.Tracker
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport.Kochava
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Name
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Property
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

class KochavaService @Inject constructor() : AnalyticsService<Kochava> {

    override fun send(report: Kochava) {
        Timber.e( "send: $report")

        when (report) {
            is Kochava.Standard -> Tracker.sendEvent(report.trackerEvent)
            is Kochava.General -> sendCustomEvent(report)
        }
    }

    private fun sendCustomEvent(event: Kochava.General) {
        val hasEventName = event::class.hasAnnotation<Name>()
        val eventName = event::class.findAnnotation<Name>()?.name
        Timber.e( "send: hasEventName=$hasEventName, eventName=$eventName")

        if (hasEventName.not()) {
            Timber.e( "send: EXCEPTION class must have `${Name::name}` annotation")
        }

        for (property in event::class.memberProperties) {
            val hasEventPropertyAnnotation = property.findAnnotation<Property>() != null
            val propertyName = property.name
            val propertyType = property.returnType
            val propertyValue = property.getter.call(event)
            Timber.e( "send: hasEventPropertyAnnotation=$hasEventPropertyAnnotation, type: $propertyType: $propertyName=$propertyValue")
        }
    }
}