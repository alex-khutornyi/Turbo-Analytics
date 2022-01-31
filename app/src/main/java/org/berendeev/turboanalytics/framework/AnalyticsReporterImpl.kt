package org.berendeev.turboanalytics.framework

import org.berendeev.turboanalytics.framework.service.AnalyticsService
import org.berendeev.turboanalytics.framework.service.ForterService
import org.berendeev.turboanalytics.framework.service.IterableService
import org.berendeev.turboanalytics.framework.service.KochavaService
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import javax.inject.Inject

internal class AnalyticsReporterImpl @Inject constructor(
    private val kochavaService: KochavaService,
    private val iterableService: IterableService,
    private val forterService: ForterService,
) : AnalyticsReporter {

    override fun report(report: AnalyticsReport) {
        when (report) {
            is AnalyticsReport.Kochava -> kochavaService.send(report)
            is AnalyticsReport.Iterable -> iterableService.send(report)
            is AnalyticsReport.Forter -> forterService.send(report)
            else -> throw IllegalArgumentException("Sender `${AnalyticsService::class.qualifiedName}` implementation NOT found for `${report::class.qualifiedName}`")
        }
    }
}
