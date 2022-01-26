package org.berendeev.turboanalytics

import com.kochava.base.Tracker
import org.berendeev.turboanalytics.AnalyticsEvent.Kochava
import timber.log.Timber
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

class KochavaSender : Sender<Kochava> {

    override fun send(event: Kochava) {
        Timber.e( "send: $event")

        when (event) {
            is Kochava.StandardEvent -> Tracker.sendEvent(event.trackerEvent)
            is Kochava.CustomEvent -> sendCustomEvent(event)
        }
    }

    private fun sendCustomEvent(event: Kochava.CustomEvent) {
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