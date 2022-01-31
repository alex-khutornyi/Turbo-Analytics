package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.service.event.AnalyticsReport

public interface AnalyticsReporter {
    fun report(report: AnalyticsReport)
}

internal interface AnalyticsService <in T : AnalyticsReport> {
    fun send(event: T)
}

