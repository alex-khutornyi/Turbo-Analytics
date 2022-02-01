package org.berendeev.turboanalytics.framework

import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport


public interface AnalyticsReporter {
    fun send(report: AnalyticsReport)
}


