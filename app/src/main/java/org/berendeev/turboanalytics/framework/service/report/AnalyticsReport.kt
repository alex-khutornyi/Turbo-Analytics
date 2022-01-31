package org.berendeev.turboanalytics.framework.service.report

import com.kochava.base.Tracker
import org.berendeev.turboanalytics.framework.service.ForterTrackType
import org.berendeev.turboanalytics.framework.service.RemoteMessage

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ReportName(val name: String)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ReportProperty(val key: String)

public sealed class AnalyticsReport {
    /**
     * Use [Kochava] to send Analytics to [Kochava SDK](https://support.kochava.com/sdk-integration/android-sdk-integration/android-using-the-sdk/)
     */
    public sealed class Kochava : AnalyticsReport() {
        /**
         * to send [Standard Kochava events](https://support.kochava.com/sdk-integration/android-sdk-integration/android-using-the-sdk/?scrollto=marker_4#collapseTrackingEvents)
         * such as [Deeplinking event](https://support.kochava.com/sdk-integration/android-sdk-integration/android-using-the-sdk/?scrollto=marker_4#collapseDeeplinking)
         *
         * Inherit from [Standard] to create reusable Standard Events
         */
        public open class Standard(val trackerEvent: Tracker.Event): Kochava()

        /**
         * to send custom Kochava events with custom fields.
         *
         * Inherit from [General] to create a specific Custom Event.
         *
         * [ReportName] class annotation should be used for Event Name.
         *
         * [ReportProperty] field annotation should be used for Event Properties
         *
         */
        public open class General: Kochava()
    }

    /**
     * Use [Iterable] to send Analytics to [Iterable SDK](https://github.com/Iterable/iterable-android-sdk)
     */
    public sealed class Iterable : AnalyticsReport() {

        /**
         * to send [In-App click](https://support.iterable.com/hc/en-us/articles/360038939972-Events-for-In-App-Messages-and-Mobile-Inbox-#in-app-click)
         */
        public open class InAppClick(val messageId: String, val clickedUrl: String) : Iterable()

        /**
         * com.google.firebase.messaging.RemoteMessage should be used
         */
        public open class FirebaseMessage(val remoteMessage: RemoteMessage) : Iterable()


        /**
         * to send custom Kochava events with custom fields.
         *
         * Inherit from [General] to create a specific Custom Event.
         *
         * [ReportName] class annotation should be used for Event Name.
         *
         * [ReportProperty] field annotation should be used for Event Properties
         *
         */
        public open class General : Iterable()
    }

    /**
     * Use
     */
    public sealed class Forter : AnalyticsReport() {

        public class Simple(val type: ForterTrackType) : Forter()

        public class TypedData(val type: ForterTrackType, val data: String) : Forter()

        public class Location(val location: android.location.Location) : Forter()

        /**
         * to send custom Kochava events with custom fields.
         *
         * Inherit from [General] to create a specific Custom Event.
         *
         * [ReportName] class annotation should be used for Event Name.
         *
         * [ReportProperty] field annotation should be used for Event Properties
         *
         */
        public open class General : Forter()

    }
}

data class GeneralReportStructure(
    val eventName: String,
    val data: Map<String, Any>
)
