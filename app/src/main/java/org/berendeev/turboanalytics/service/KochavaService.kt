package org.berendeev.turboanalytics.service

import com.kochava.base.Tracker
import org.berendeev.turboanalytics.service.event.AnalyticsReport.Kochava
import org.berendeev.turboanalytics.service.event.EventName
import org.berendeev.turboanalytics.service.event.EventProperty
import org.berendeev.turboanalytics.AnalyticsService
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
        val hasEventName = event::class.hasAnnotation<EventName>()
        val eventName = event::class.findAnnotation<EventName>()?.name
        Timber.e( "send: hasEventName=$hasEventName, eventName=$eventName")

        if (hasEventName.not()) {
            Timber.e( "send: EXCEPTION class must have `${EventName::name}` annotation")
        }

        for (property in event::class.memberProperties) {
            val hasEventPropertyAnnotation = property.findAnnotation<EventProperty>() != null
            val propertyName = property.name
            val propertyType = property.returnType
            val propertyValue = property.getter.call(event)
            Timber.e( "send: hasEventPropertyAnnotation=$hasEventPropertyAnnotation, type: $propertyType: $propertyName=$propertyValue")
        }
    }
}