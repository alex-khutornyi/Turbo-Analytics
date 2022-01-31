package org.berendeev.turboanalytics.framework.service

import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import javax.inject.Inject

class ForterService @Inject constructor() : AnalyticsService<AnalyticsReport.Forter> {

    override fun send(report: AnalyticsReport.Forter) {
        when (report) {
            is AnalyticsReport.Forter.Simple -> {
//                ForterSDK.getInstance().trackAction(event.type)
            }

            is AnalyticsReport.Forter.TypedData -> {
//            ForterSDK.getInstance()
//                .trackAction(event.type, event.data)
            }

            is AnalyticsReport.Forter.Location -> {
//                ForterSDK.getInstance().onLocationChanged(event.location)
            }
            is AnalyticsReport.Forter.General -> {
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