package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.service.ForterSender
import org.berendeev.turboanalytics.service.IterableSender
import org.berendeev.turboanalytics.service.KochavaService
import org.berendeev.turboanalytics.service.event.AnalyticsEvent

class AnalyticsReporterImpl(
    private val kochavaService: KochavaService,
    private val iterableSender: IterableSender,
    private val forterSender: ForterSender,
) : AnalyticsReporter {

    override fun send(event: AnalyticsEvent) {
        when (event) {
            is AnalyticsEvent.Kochava -> kochavaService.send(event)
            is AnalyticsEvent.Iterable -> iterableSender.send(event)
            is AnalyticsEvent.Forter -> forterSender.send(event)
            else -> throw IllegalArgumentException("Sender `${AnalyticsService::class.qualifiedName}` implementation NOT found for `${event::class.qualifiedName}`")
        }
    }
}
