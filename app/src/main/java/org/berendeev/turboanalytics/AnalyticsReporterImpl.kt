package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.service.ForterService
import org.berendeev.turboanalytics.service.IterableService
import org.berendeev.turboanalytics.service.KochavaService
import org.berendeev.turboanalytics.service.event.AnalyticsReport

class AnalyticsReporterImpl(
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
