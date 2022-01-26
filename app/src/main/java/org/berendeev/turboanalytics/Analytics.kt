package org.berendeev.turboanalytics

import com.kochava.base.Tracker
import org.berendeev.turboanalytics.AnalyticsEvent.Kochava.CustomEvent

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventProperty(val key: String)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventName(val name: String)

sealed class AnalyticsEvent {
    /**
     * Use [Kochava] to send Analytics to [Kochava SDK](https://support.kochava.com/sdk-integration/android-sdk-integration/android-using-the-sdk/)
     */
    sealed class Kochava : AnalyticsEvent() {
        /**
         * to send [Standard Kochava events](https://support.kochava.com/sdk-integration/android-sdk-integration/android-using-the-sdk/?scrollto=marker_4#collapseTrackingEvents)
         * such as [Deeplinking event](https://support.kochava.com/sdk-integration/android-sdk-integration/android-using-the-sdk/?scrollto=marker_4#collapseDeeplinking)
         *
         * Inherit from [StandardEvent] to create reusable Standard Events
         */
        open class StandardEvent(val trackerEvent: Tracker.Event): Kochava()

        /**
         * to send custom Kochava events with custom fields.
         *
         * Inherit from [CustomEvent] to create a specific Custom Event.
         *
         * [EventName] class annotation should be used for Event Name.
         *
         * [EventProperty] field annotation should be used for Event Properties
         *
         */
        open class CustomEvent: Kochava()
    }

    /**
     * Use [Iterable] to send Analytics to [Iterable SDK](https://github.com/Iterable/iterable-android-sdk)
     */
    sealed class Iterable : AnalyticsEvent() {

        /**
         * to send [In-App click](https://support.iterable.com/hc/en-us/articles/360038939972-Events-for-In-App-Messages-and-Mobile-Inbox-#in-app-click)
         */
        open class InAppClick(val messageId: String, val clickedUrl: String) : Iterable()

        /**
         * com.google.firebase.messaging.RemoteMessage should be used
         */
        open class FirebaseMessage(val remoteMessage: RemoteMessage) : Iterable()


        /**
         * to send custom Kochava events with custom fields.
         *
         * Inherit from [CustomEvent] to create a specific Custom Event.
         *
         * [EventName] class annotation should be used for Event Name.
         *
         * [EventProperty] field annotation should be used for Event Properties
         *
         */
        open class CustomEvent : Iterable()
    }
}

interface Sender <in T : AnalyticsEvent> {
    fun send(event: T)
}

interface AnalyticsReporter {
    fun send(event: AnalyticsEvent)
}
