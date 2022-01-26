package org.berendeev.turboanalytics.sdk

import org.berendeev.turboanalytics.sdk.event.AnalyticsEvent
import org.berendeev.turboanalytics.SdkSender

class ForterSender : SdkSender<AnalyticsEvent.Forter> {

    override fun send(event: AnalyticsEvent.Forter) {
        when (event) {
            is AnalyticsEvent.Forter.SimpleEvent -> {
//                ForterSDK.getInstance().trackAction(event.type)
            }

            is AnalyticsEvent.Forter.TypedDataEvent -> {
//            ForterSDK.getInstance()
//                .trackAction(event.type, event.data)
            }

            is AnalyticsEvent.Forter.LocationEvent -> {
//                ForterSDK.getInstance().onLocationChanged(event.location)
            }
            is AnalyticsEvent.Forter.CustomEvent -> {
//            1. convert with CustomEventConverter
//            2. ForterSDK.getInstance()
//                .trackAction(event.type, event.data)
            }
        }
    }
}

/**
 * It's supposed to be com.forter.mobile.fortersdk.models.TrackType
 */
class ForterTrackType(val id: String)