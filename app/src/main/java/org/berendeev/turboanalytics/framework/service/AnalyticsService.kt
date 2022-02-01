package org.berendeev.turboanalytics.framework.service

import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport

internal interface AnalyticsService <in T : AnalyticsReport> {
    fun send(report: T)
}
