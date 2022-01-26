package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.sdk.event.AnalyticsEvent

interface AnalyticsReporter {
    fun send(event: AnalyticsEvent)
}

interface SdkSender <in T : AnalyticsEvent> {
    fun send(event: T)
}

