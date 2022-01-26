package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.service.event.AnalyticsEvent

interface AnalyticsReporter {
    fun send(event: AnalyticsEvent)
}

internal interface AnalyticsService <in T : AnalyticsEvent> {
    fun send(event: T)
}

