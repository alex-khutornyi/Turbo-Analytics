package org.berendeev.turboanalytics.framework.service.report

import com.kochava.base.Tracker
import org.berendeev.turboanalytics.framework.service.ForterTrackType
import org.berendeev.turboanalytics.framework.service.RemoteMessage
import java.time.LocalDateTime

/**
 * to send a General Report with a set of properties.
 *
 * [Name] class annotation should be used for Report Name.
 *
 * [Property] field annotation should be used for Report Properties
 *
 */
public interface GeneralReport {
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    public annotation class Name(val name: String)

    @Target(AnnotationTarget.PROPERTY)
    @Retention(AnnotationRetention.RUNTIME)
    public annotation class Property(val key: String)

    public var createdAt: LocalDateTime
}

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
         * to send a Kochava [GeneralReport] with a set of properties
         *
         * Inherit from [General] to create a specific [GeneralReport].
         *
         */
        public abstract class General(
            override var createdAt: LocalDateTime = LocalDateTime.now(),
        ) : Kochava(), GeneralReport
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
         * to send a Iterable [GeneralReport] with a set of properties
         *
         * Inherit from [General] to create a specific [GeneralReport].
         *
         */
        public abstract class General(
            override var createdAt: LocalDateTime = LocalDateTime.now(),
        ) : Iterable(), GeneralReport
    }

    /**
     * Use
     */
    public sealed class Forter : AnalyticsReport() {

        public class Simple(val type: ForterTrackType) : Forter()

        public class TypedData(val type: ForterTrackType, val data: String) : Forter()

        public class Location(val location: android.location.Location) : Forter()

        /**
         * to send a Forter [GeneralReport] with a set of properties
         *
         * Inherit from [General] to create a specific [GeneralReport].
         *
         */
        public abstract class General(
            override var createdAt: LocalDateTime = LocalDateTime.now(),
        ) : Forter(), GeneralReport

    }
}

//The class below is for demo purpose
data class GeneralReportStructure(
    val eventName: String,
    val data: Map<String, Any>
)
